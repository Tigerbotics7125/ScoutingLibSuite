package cubscout.backend.pojos;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.callback.InteractionMessageBuilder;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

import cubscout.utilities.HighLevelDatabaseUtil;

/**
 * A class which helps easily build a team object, and also provides utility functions.
 */
public class TeamBuilder {
  public final MessageComponentCreateListener kImgNavigationListener = new MessageComponentCreateListener() {
    @Override
    public void onComponentCreate(MessageComponentCreateEvent event) {
      String buttonId = event.getMessageComponentInteraction().getCustomId();
      event.getMessageComponentInteraction().acknowledge();
      switch (buttonId) {
        case "ImgPrev": {
          previousImage();
          break;
        }
        case "ImgNext": {
          nextImage();
          break;
        }
      }
    }
  };

  private Team mTeam;
  private int mImageIndex = 0;

  public TeamBuilder() {
    mTeam = new Team();
  }

  public TeamBuilder(Team team) {
    mTeam = team;
  }

  public Team build() {
    return mTeam;
  }

  //
  // Building methods
  //

  public TeamBuilder withNumber(int number) {
    mTeam.setNumber(number);
    return this;
  }

  public TeamBuilder withName(String name) {
    mTeam.setName(name);
    return this;
  }

  public TeamBuilder withScouterId(String scouterId) {
    mTeam.setScouterId(scouterId);
    return this;
  }

  public TeamBuilder withScouterId(long scouterId) {
    return withScouterId(String.valueOf(scouterId));
  }

  public TeamBuilder withScouterId(User scouter) {
    return withScouterId(scouter.getId());
  }

  public TeamBuilder withComment(String title, String comment) {
    mTeam.getCommentTitles().add(title);
    mTeam.getCommentContents().add(comment);
    return this;
  }

  public TeamBuilder withImage(BufferedImage image) {
    mTeam.getImages().add(image);
    return this;
  }

  //
  // Utility methods
  //

  public void nextImage() {
    mImageIndex++;
  }

  public void previousImage() {
    mImageIndex--;
  }

  public boolean isComplete() {
    boolean isComplete = true;

    if (mTeam.getNumber() == 0) 
      isComplete = false;
    if (mTeam.getName().isEmpty())
      isComplete = false;
    if (mTeam.getScouterId().isEmpty())
      isComplete = false;
    if (mTeam.getCommentTitles().isEmpty())
      isComplete = false;
    if (mTeam.getCommentContents().isEmpty())
      isComplete = false;
    if (mTeam.getCommentTitles().size() != mTeam.getCommentContents().size())
      isComplete = false;
    if (mTeam.getImages().isEmpty())
      isComplete = false;

    return isComplete;
    
  }

  public void postToDatabase() {
    mTeam.setLastUpdated(Instant.now());
    HighLevelDatabaseUtil.insertTeam(mTeam);
  }

  public EmbedBuilder toEmbed(DiscordApi api) {
    var eb = new EmbedBuilder();
    var completeColor = new Color(51, 255, 189);
    var incompleteColor = new Color(255, 87, 51);

    if (isComplete()) {
      eb.setColor(completeColor);
    } else {
      eb.setColor(incompleteColor);
    }
    eb.setTitle(String.format("%4d %s", mTeam.getNumber(), mTeam.getName()));
    eb.setDescription(String.format("Data is %s.", isComplete() ? "complete" : "incomplete"));
    for (int i = 0; i < mTeam.getCommentTitles().size(); i++) {
      String title = mTeam.getCommentTitles().get(i);
      String comment = mTeam.getCommentContents().get(i);
      if (comment.isEmpty()) {
        comment = "** **";
      }
      eb.addField(title, comment, false);
    }
    if (mTeam.getImages().size() > 0) {
      eb.setThumbnail(mTeam.getImages().get(mImageIndex));
    }

    return eb;
  }

  public InteractionMessageBuilder toMessage(DiscordApi api) {
    var msgBuild = new InteractionMessageBuilder();

    msgBuild.addEmbed(toEmbed(api));

    List<LowLevelComponent> components = new ArrayList<>();
    int numImages = mTeam.getImages().size();
    if (numImages > 1 && mImageIndex > 0) {
      // we need left arrow
      components.add(Button.secondary("ImgPrev", "⬅️"));
    } 
    if (numImages > 1 && mImageIndex < numImages -1) {
      // we need right arrow
      components.add(Button.secondary("ImgNext", "➡️"));  
    }
    if (!components.isEmpty()) {
      msgBuild.addComponents(ActionRow.of(components));
    }
      
    return msgBuild;
  }

}
