package cubscout.commands.scout.listeners;

import cubscout.commands.scout.ScoutSession;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

public class ButtonListener implements MessageComponentCreateListener {

  private ScoutSession mScoutInstance;

  public ButtonListener(ScoutSession scoutInstance) {
    mScoutInstance = scoutInstance;
  }

  @Override
  public void onComponentCreate(MessageComponentCreateEvent event) {

    // Only function if the message is the scout message
    if (event.getMessageComponentInteraction().getMessageId()
        != mScoutInstance.getScoutMessage().getId()) {
      return;
    }

    // Only function if our scouter is the one who pressed the button.
    if (event.getMessageComponentInteraction().getUser().getId()
        != mScoutInstance.getScouter().getId()) {
      return;
    }

    String buttonId = event.getMessageComponentInteraction().getCustomId();

    switch (buttonId) {
      case "prev":
        {
          event.getMessageComponentInteraction().acknowledge().join();
          mScoutInstance.decrementStage();
          break;
        }
      case "next":
        {
          event.getMessageComponentInteraction().acknowledge().join();
          mScoutInstance.incrementStage();
          break;
        }
      case "done":
        {
          event.getMessageComponentInteraction().acknowledge().join();
          mScoutInstance.completeInteraction();
          break;
        }
      case "abort":
        {
          event.getMessageComponentInteraction().acknowledge().join();
          mScoutInstance.abortInteraction();
          break;
        }
    }
  }
}
