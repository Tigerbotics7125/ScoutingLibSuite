package cubscout.commands.scout;

import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

public class NavigationListener implements MessageComponentCreateListener {

  private final ScoutSession mSession;

  public NavigationListener(ScoutSession session) {
    this.mSession = session;
  }

  @Override
  public void onComponentCreate(MessageComponentCreateEvent event) {
    var interaction = event.getMessageComponentInteraction();
    // only react to the scouter
    if (mSession.getScoutId() == interaction.getUser().getId()) {

      switch (event.getMessageComponentInteraction().getCustomId()) {
        case "PrevStage":
          {
            mSession.decrementStage();
            mSession.activateStage();
            interaction.acknowledge();
            break;
          }

        case "NextStage":
          {
            mSession.incrementStage();
            mSession.activateStage();
            interaction.acknowledge();
            break;
          }

        case "Submit":
          {
            mSession.submit(interaction);
            break;
          }

        case "Abort":
          {
            mSession.abort();
            interaction.acknowledge();
            break;
          }
      }
    }
  }
}
