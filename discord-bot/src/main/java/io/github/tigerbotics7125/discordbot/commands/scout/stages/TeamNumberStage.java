package io.github.tigerbotics7125.discordbot.commands.scout.stages;

import io.github.tigerbotics7125.discordbot.commands.scout.ScoutSession;
import io.github.tigerbotics7125.discordbot.utilities.TeamUtil;
import java.io.IOException;
import org.javacord.api.event.message.MessageCreateEvent;

public class TeamNumberStage extends Stage {

  public TeamNumberStage(ScoutSession session) {
    super(session);

    // send message
    session
        .getMsgBuilder()
        .addEmbed(
            session.getEmbed().setDescription("Awaiting **Team Number**\nExample:\n```\n7125\n```"))
        .editFollowupMessage(session.getInteraction(), session.getMessage().getId())
        .join();
  }

  @Override
  public void onMessageCreate(MessageCreateEvent event) {
    // assert that the message was sent by the scouter
    if (getSession().getScoutId() == event.getMessageAuthor().getId()) {
      // attempt to parse the message as a team number.
      try {
        // will fail if the message is not a number
        int teamNumber = Integer.parseInt(event.getMessageContent());

        // check that the number is valid
        // a valid team number is a positive integer between 1 and 9999
        if (!(teamNumber < 1 || teamNumber > 9999)) {
          // valid team number
          getSession().getTeam().setNumber(teamNumber);
        } else {
          throw new NumberFormatException("Team number must be between 1 and 9999.");
        }

        // delete the message to confirm that we received it.
        event.getMessage().delete().join();

        // try filling data from TBA
        try {
          TeamUtil.fillFromTBA(getSession().getTeam());
          // if we did fill, then the name is filled, so skip it.
          getSession().incrementStage(); // adds an extra increment to pass the name stage.
        } catch (IOException e) {
          // if we can't fill no big deal.
          e.printStackTrace();
        }

        // move onto the next stage
        getSession().incrementStage();
        getSession().activateStage();

      } catch (NumberFormatException e) {
        // invalid team number
        System.err.println(e.getMessage());
        e.printStackTrace();
      }
    }
  }
}
