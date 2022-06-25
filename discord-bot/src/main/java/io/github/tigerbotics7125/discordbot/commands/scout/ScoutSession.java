package io.github.tigerbotics7125.discordbot.commands.scout;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static io.github.tigerbotics7125.discordbot.utilities.Constants.kDatabaseName;
import static io.github.tigerbotics7125.discordbot.utilities.Constants.kTeamCollectionName;

import io.github.tigerbotics7125.databaselib.DatabaseAccessor;
import io.github.tigerbotics7125.databaselib.pojos.Team;
import io.github.tigerbotics7125.discordbot.Application;
import io.github.tigerbotics7125.discordbot.commands.scout.stages.Stage;
import io.github.tigerbotics7125.discordbot.commands.scout.stages.TeamCommentStage;
import io.github.tigerbotics7125.discordbot.commands.scout.stages.TeamNameStage;
import io.github.tigerbotics7125.discordbot.commands.scout.stages.TeamNumberStage;
import io.github.tigerbotics7125.discordbot.utilities.Constants;
import io.github.tigerbotics7125.discordbot.utilities.TeamUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import org.bson.conversions.Bson;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionMessageBuilder;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.util.event.ListenerManager;

public class ScoutSession implements AutoCloseable {

  private final SlashCommandInteraction kInteraction;
  private final DiscordApi kApi;
  private final long kScoutId;
  private final Team kTeam = new Team();
  private final Timer kTimer = new Timer();
  private final TimeoutTimerTask kTimeoutTask = new TimeoutTimerTask(this, 15);
  private Message mMessage;
  private ListenerManager<MessageComponentCreateListener> mComponentListener;
  private StageFrame mDesiredStage = StageFrame.TEAM_NUMBER;
  private Stage mActiveStage = null;
  private TextChannel mChannel;

  public ScoutSession(SlashCommandInteraction interaction) {
    kInteraction = interaction;
    kApi = interaction.getApi();
    kScoutId = kInteraction.getUser().getId();
    // initialize more complex variables
    init();
    // start listening for messages
    activateStage();
  }

  private void init() {
    // let discord know we will not be responding immediately
    kInteraction.respondLater().join();
    // get the channel if present, if not, abort the session as it's required.
    kInteraction
        .getChannel()
        .ifPresentOrElse(
            (channel) -> mChannel = channel,
            () -> {
              abort("You must be in a channel to scout.");
              return; // it is necessary shut-up intellij
            });
    // send the first message so no NPE
    mMessage =
        kInteraction.createFollowupMessageBuilder().setContent("Initializing...").send().join();
    // set up the navigation button listener
    mComponentListener = mChannel.addMessageComponentCreateListener(new NavigationListener(this));
    // start the timeout timer
    kTimer.scheduleAtFixedRate(kTimeoutTask, 0, 1000);

    // add the scout's id to the team builder
    kTeam.setScouterId(kScoutId);
    try {
      kTeam.setSeason(Application.tbaApi.getStatus().currentSeason);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** @return a standardized embed for instructions on how to use the session. */
  public EmbedBuilder getEmbed() {
    return new EmbedBuilder()
        .setColor(Constants.kNeutral)
        .setFooter(
            "You have 15 minutes to complete the interaction.\nThis session ends",
            kInteraction.getUser().getAvatar())
        .setTimestamp(kTimeoutTask.getTimeoutTime());
  }

  /** Submit the team to the database iff complete. */
  public void submit(MessageComponentInteraction interaction) {

    var collection =
        DatabaseAccessor.getInstance()
            .getCollection(kDatabaseName, kTeamCollectionName, Team.class);
    // matches team with same number and scouter
    Bson filter = and(eq("number", kTeam.getNumber()), eq("scouterId", kScoutId));

    if (collection.find(filter).first() != null) {
      // team(s) already exists; overwrite (the first one)
      collection.replaceOne(filter, kTeam);
    } else {
      collection.insertOne(kTeam);
    }

    interaction.acknowledge();

    getMsgBuilder()
        .removeAllComponents()
        .addEmbed(
            getEmbed()
                .setTitle("Team Submitted!")
                .setFooter(
                    String.format("Thank you %s!", kInteraction.getUser().getName()),
                    kInteraction.getUser().getAvatar())
                .setTimestampToNow())
        .editFollowupMessage(kInteraction, mMessage.getId())
        .join();
    close();
  }

  /** Abort the current session. */
  public void abort() {
    abort(null);
  }

  /**
   * Abort the current session.
   *
   * @param reason the reason to display to the user.
   */
  public void abort(String reason) {
    getMsgBuilder()
        .removeAllEmbeds()
        .removeAllComponents()
        .addEmbed(
            new EmbedBuilder()
                .setTitle("Session Aborted")
                .setColor(Constants.kNeutral)
                .setDescription(reason == null ? "No reason given." : reason))
        .editFollowupMessage(kInteraction, mMessage.getId());
    close();
  }

  /** Stops the session, while reporting that it stopped due to a timeout. */
  public void timeout() {
    abort("Session timed out.");
  }

  /** Activates logic for the next desired stage. */
  public void activateStage() {
    // stop the previous stage
    if (mActiveStage != null) {
      try {
        mActiveStage.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    // activate the new stage
    switch (mDesiredStage) {
      case TEAM_NUMBER:
        {
          mActiveStage = new TeamNumberStage(this);
          break;
        }
      case TEAM_NAME:
        {
          mActiveStage = new TeamNameStage(this);
          break;
        }
      case TEAM_COMMENT:
        {
          mActiveStage = new TeamCommentStage(this);
          break;
        }
    }
  }

  /** Next stage. */
  public void incrementStage() {
    StageFrame nextStage = StageFrame.fromIndex(mDesiredStage.kIndex + 1);
    if (nextStage != null) {
      mDesiredStage = nextStage;
    }
  }

  /** Previous stage. */
  public void decrementStage() {
    StageFrame prevStage = StageFrame.fromIndex(mDesiredStage.kIndex - 1);
    if (prevStage != null) {
      mDesiredStage = prevStage;
    }
  }

  /** Closes the session safely. Removes all listeners, and stops timers. */
  @Override
  public void close() {
    // remove all listeners
    mComponentListener.remove();
    try {
      mActiveStage.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    // stop timer
    kTimer.cancel();
  }

  public SlashCommandInteraction getInteraction() {
    return kInteraction;
  }

  //
  // Getters and Setters
  //

  public long getScoutId() {
    return kScoutId;
  }

  public Team getTeam() {
    return kTeam;
  }

  public Message getMessage() {
    return mMessage;
  }

  public TextChannel getChannel() {
    return mChannel;
  }

  /** @return A message builder preloaded with the team embed. */
  public InteractionMessageBuilder getMsgBuilder() {
    // determine which buttons to add.
    List<LowLevelComponent> navButtons = new ArrayList<>();
    navButtons.add(Button.danger("Abort", "Abort"));
    if (mDesiredStage.kIndex > 0) {
      navButtons.add(Button.secondary("PrevStage", "Prev"));
    }
    if (mDesiredStage.kIndex < StageFrame.values().length - 1) {
      navButtons.add(Button.secondary("NextStage", "Next"));
    }
    if (TeamUtil.isComplete(kTeam)) {
      navButtons.add(Button.success("Submit", "Submit"));
    }

    return new InteractionMessageBuilder()
        .copy(mMessage)
        .removeAllEmbeds()
        .removeAllComponents()
        .setContent("** **")
        .addEmbed(TeamUtil.toEmbed(kTeam, kApi))
        .addComponents(ActionRow.of(navButtons));
  }

  private enum StageFrame {
    TEAM_NUMBER(0),
    TEAM_NAME(1),
    TEAM_COMMENT(2);

    final int kIndex;

    StageFrame(int index) {
      kIndex = index;
    }

    public static StageFrame fromIndex(int index) {
      for (StageFrame stage : StageFrame.values()) {
        if (stage.kIndex == index) {
          return stage;
        }
      }
      return null;
    }
  }
}
