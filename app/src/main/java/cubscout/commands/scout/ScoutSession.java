package cubscout.commands.scout;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionMessageBuilder;
import org.javacord.api.util.event.ListenerManager;

import cubscout.backend.pojos.Team;
import cubscout.backend.pojos.TeamBuilder;
import cubscout.commands.scout.listeners.ButtonListener;
import cubscout.commands.scout.listeners.CommentListener;
import cubscout.commands.scout.listeners.ImageListener;
import cubscout.commands.scout.listeners.NameListener;
import cubscout.commands.scout.listeners.NumberListener;
import cubscout.utilities.HighLevelDatabaseUtil;

public class ScoutSession {

  private static final int kNumStages = 4;

  private DiscordApi mApi;
  private SlashCommandInteraction mInteraction;
  private User mScouter;
  private TeamBuilder mTeamBuilder = new TeamBuilder();
  private Message mTeamMessage;
  private Message mScoutMessage;
  private ArrayList<ListenerManager<?>> mAllListeners = new ArrayList<>();
  private ArrayList<ListenerManager<?>> mStageListeners = new ArrayList<>();

  private int mStageIndex = 0;

  public ScoutSession(SlashCommandInteraction interaction) {
    interaction.respondLater().join();
    mApi = interaction.getApi();
    mInteraction = interaction;
    mScouter = interaction.getUser();
    mTeamBuilder.withScouterId(mScouter.getIdAsString());
    mTeamMessage = mTeamBuilder.toMessage(mApi).setFlags(MessageFlag.EPHEMERAL).sendFollowupMessage(interaction).join();
    mScoutMessage =
        interaction
            .createFollowupMessageBuilder()
            .addComponents(
                ActionRow.of(
                    Button.secondary("prev", null, "⬅️"),
                    Button.secondary("next", null, "➡️"),
                    Button.success("done", "Post"),
                    Button.danger("abort", "Abort")))
            .setFlags(MessageFlag.EPHEMERAL)
            .send()
            .join();
    mAllListeners.add(mApi.addMessageComponentCreateListener(new ButtonListener(this)));
    mAllListeners.add(mApi.addMessageComponentCreateListener(mTeamBuilder.kImgNavigationListener));
    activateStage();
  }

  public DiscordApi getApi() {
    return mApi;
  }

  public User getScouter() {
    return mScouter;
  }

  public TeamBuilder getTeamBuilder() {
    return mTeamBuilder;
  }

  public Message getTeamMessage() {
    return mTeamMessage;
  }

  public Message getScoutMessage() {
    return mScoutMessage;
  }

  public ArrayList<ListenerManager<?>> getAllListeners() {
    return mAllListeners;
  }

  public ArrayList<ListenerManager<?>> getStageListeners() {
    return mStageListeners;
  }

  public int getStageIndex() {
    return mStageIndex;
  }

  /** Updates the team message with the latest instance of the Team object. */
  public void refreshTeamMessage() {
    mTeamMessage.delete();
    mTeamMessage = mTeamBuilder.toMessage(mApi).setFlags(MessageFlag.EPHEMERAL).sendFollowupMessage(mInteraction).join();
  }

  /** Decrements the stage -1 if possible, then activates the stage. */
  public void decrementStage() {
    removeStageListeners();
    if (mStageIndex > 0) {
      mStageIndex--;
    }
    activateStage();
  }

  /** Increments the stage +1 if possible, then activates the stage. */
  public void incrementStage() {
    removeStageListeners();
    if (mStageIndex < kNumStages - 1) {
      mStageIndex++;
    }
    activateStage();
  }

  /** Removes ListenerManagers tied to a stage. */
  public void removeStageListeners() {
    for (int i = 0; i < mStageListeners.size(); i++) {
      removeListener(mStageListeners.get(i));
      i--;
    }
  }

  /** Removes all listeners, without ConcurrentModificationException... */
  public void removeAllListeners() {
    for (int i = 0; i < mAllListeners.size(); i++) {
      removeListener(mAllListeners.get(i));
      i--;
    }
  }

  /** Adds the listener to the list. */
  public void addListener(ListenerManager<?> listener) {
    mAllListeners.add(listener);
  }

  /** Adds a listener to the current stage's list. */
  public void addStageListener(ListenerManager<?> listener) {
    mStageListeners.add(listener);
    addListener(listener);
  }

  /** Removes a ListenerManger from both lists of listeners. */
  public void removeListener(ListenerManager<?> listener) {
    // remove listener from api before removing from list otherwise its just
    // permanently on and we cant get rid of it lol.
    listener.remove();

    mAllListeners.remove(listener);
    // remove only removes if present, no need to check.
    mStageListeners.remove(listener);
  }

  /** Activates a stage based on the current stage index. */
  public void activateStage() {
    removeStageListeners();
    switch (mStageIndex) {
      case 0:
        {
          activateNumberStage();
          return;
        }
      case 1:
        {
          activateNameStage();
          return;
        }
      case 2:
        {
          activateCommentStage();
          return;
        }
      case 3:
        {
          activateImageStage();
          return;
        }
    }
  }

  /** @return A unifrom EmbedBuilder to use througout. */
  public EmbedBuilder getScoutEmbed(String title, String description) {
    return new EmbedBuilder()
        .setTitle(title)
        .setDescription(description)
        .setColor(Color.MAGENTA)
        .setFooter(
            "Scouting session for " + mScouter.getDisplayName(mScoutMessage.getServer().get()),
            mScouter.getAvatar())
        .setTimestampToNow();
  }

  /** Post the built team to the DB; ending the scouting session. */
  public void completeInteraction() {
    mScoutMessage.edit(getScoutEmbed("Posting session...", "Thank you for scouting!")).join();
    // add team to DB
    HighLevelDatabaseUtil.insertTeam(mTeamBuilder.build());
    removeAllListeners();
    new InteractionMessageBuilder().addEmbed(getScoutEmbed("Team posted!", "Thank you for scouting!")).editFollowupMessage(mInteraction, mScoutMessage.getId()).join();
  }

  /** Abandons the scouting session. */
  public void abortInteraction() {
    removeAllListeners();
    mTeamMessage.delete().join();
    new InteractionMessageBuilder().copy(mScoutMessage).removeAllComponents().editFollowupMessage(mInteraction, mScoutMessage.getId()).join();
    mScoutMessage
        .edit(new EmbedBuilder().setTitle("Scouting session aborted.").setColor(Color.RED))
        .join();
  }

  /** Activates the stage which retrieves the team's number. */
  public void activateNumberStage() {
    mScoutMessage
        .edit(
            getScoutEmbed(
                "__Number__",
                "Awaiting input...\n"
                    + "Input must be an integer between 1 and 9999 inclusive.\n\n"
                    + "7125"))
        .join();
    addStageListener(
        mScoutMessage.getChannel().addMessageCreateListener(new NumberListener(this)));
  }

  /** Activates the stage which retrieves the team's name. */
  public void activateNameStage() {
    mScoutMessage
        .edit(
            getScoutEmbed("__Name__", "Awaiting input...\nInput must be a string.\n\nTigerbotics"))
        .join();
    addStageListener(
        mScoutMessage.getChannel().addMessageCreateListener(new NameListener(this)));
  }

  /** Activates the stage which retrieves comments about the team. */
  public void activateCommentStage() {
    mScoutMessage
        .edit(
            getScoutEmbed(
                "__Comments__",
                "Awaiting input...\n"
                    + "Input \"Title\" must be a string; optionally a \"Description\" can be"
                    + " specified on the next and subsequent lines.\n\n"
                    + "Drivetrain\n"
                    + "Mecanum\n"
                    + "Very agile."))
        .join();
    addStageListener(
        mScoutMessage.getChannel().addMessageCreateListener(new CommentListener(this)));
  }

  public void activateImageStage() {
    mScoutMessage
        .edit(getScoutEmbed("__Images__", "Awaiting input...\nInput must be an Imgur link.\n```https://imgur.com/a/3WFjNN0```"))
        .join();
    addStageListener(
        mScoutMessage.getChannel().addMessageCreateListener(new ImageListener(this)));
  }

}
