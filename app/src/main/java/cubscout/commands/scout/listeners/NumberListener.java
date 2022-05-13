package cubscout.commands.scout.listeners;

import cubscout.commands.scout.ScoutSession;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class NumberListener implements MessageCreateListener {

  private ScoutSession mSession;

  public NumberListener(ScoutSession session) {
    mSession = session;
  }

  @Override
  public void onMessageCreate(MessageCreateEvent event) {
    // If the message is from a user besides our scouter, ignore it.
    if (event.getMessageAuthor().asUser().get().getId() != mSession.getScouter().getId()) {
      return;
    }

    // If the message is not in the same channel as our scout message, ignore it.
    if (mSession.getScoutMessage().getChannel().getId() != event.getChannel().getId()) {
      return;
    }

    // try and parse the message as a number, if it succeeds, set the team number, and update
    // messages
    try {
      int teamNumber = Integer.parseInt(event.getMessageContent());
      mSession.getTeamBuilder().withNumber(teamNumber);
      mSession.refreshTeamMessage();
    } catch (NumberFormatException e) {
      // if the message is not a number, ignore it.
      return;
    }

    // delete the input message.
    event.getMessage().delete().join();
    mSession.incrementStage();
  }
}
