package io.github.tigerbotics7125.discordbot.commands.scout.stages;

import io.github.tigerbotics7125.databaselib.pojos.Team;
import io.github.tigerbotics7125.discordbot.commands.scout.ScoutSession;
import org.javacord.api.event.message.MessageCreateEvent;

public class TeamCommentStage extends Stage {

  public TeamCommentStage(ScoutSession session) {
    super(session);

    getSession()
        .getMsgBuilder()
        .addEmbed(
            getSession()
                .getEmbed()
                .setDescription(
                    "Awaiting **Comments**\n"
                        + "```\n"
                        + "Title\n"
                        + "Body (Optional)\n"
                        + "```Example:\n"
                        + "```\n"
                        + "Drivetrain\n"
                        + "Mecanum\n"
                        + "```"))
        .editFollowupMessage(getSession().getInteraction(), getSession().getMessage().getId())
        .join();
  }

  @Override
  public void onMessageCreate(MessageCreateEvent event) {
    // assert that the message was sent by the scouter
    if (getSession().getScoutId() == event.getMessageAuthor().getId()) {
      // Field titles are limited to 256 characters
      // First will be considered the title, the rest will be considered the description

      String[] input = event.getMessageContent().split("\n");

      String title = input[0];

      // check title not out of bounds
      if (title.length() > 256) {
        throw new IllegalArgumentException("Title is too long.");
      }

      StringBuilder description = new StringBuilder();

      for (int i = 1; i < input.length; i++) {
        description.append(input[i]);
        // re-add new lines
        if (i != input.length - 1) {
          description.append("\n");
        }
      }

      // add the team comment
      // check that the description is not empty, if it is, add a fake description which can't be
      // seen.
      getSession()
          .getTeam()
          .addComment(
              new Team.Comment(
                  title, description.toString().isEmpty() ? "** **" : description.toString()));

      // re-activate the stage, but don't move on (effectively just edits the team embed)
      getSession().activateStage();

      // delete the message to confirm that we received it.
      event.getMessage().delete().join();
    }
  }
}
