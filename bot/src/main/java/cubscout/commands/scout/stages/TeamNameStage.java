package cubscout.commands.scout.stages;

import cubscout.commands.scout.ScoutSession;
import org.javacord.api.event.message.MessageCreateEvent;

public class TeamNameStage extends Stage {

  public TeamNameStage(ScoutSession session) {
    super(session);

    session
        .getMsgBuilder()
        .addEmbed(
            session
                .getEmbed()
                .setDescription("Awaiting **Team Name**\nExample:\n```\nTigerbotics\n```"))
        .editFollowupMessage(session.getInteraction(), session.getMessage().getId())
        .join();
  }

  @Override
  public void onMessageCreate(MessageCreateEvent event) {
    // assert that the message was sent by the scouter
    if (getSession().getScoutId() == event.getMessageAuthor().getId()) {
      // assume all team names (also assuming scouters will use common slang) are under 25
      // characters
      if (event.getMessageContent().length() < 25) {
        getSession().getTeam().setName(event.getMessageContent());

        // delete the message to confirm that we received it.
        event.getMessage().delete().join();

        // move onto the next stage
        getSession().incrementStage();
        getSession().activateStage();
      }
    }
  }
}
