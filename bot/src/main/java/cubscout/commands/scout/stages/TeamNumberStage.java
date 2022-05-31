package cubscout.commands.scout.stages;

import cubscout.commands.scout.ScoutSession;
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
