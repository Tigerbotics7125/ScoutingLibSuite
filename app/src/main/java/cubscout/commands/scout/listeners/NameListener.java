package cubscout.commands.scout.listeners;

import cubscout.commands.scout.ScoutSession;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class NameListener implements MessageCreateListener {

  private ScoutSession mSession;

  public NameListener(ScoutSession session) {
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

    // check that the message is not obsurdly long.
    if (event.getMessageContent().length() > 100) {
      return;
    }

    mSession.getTeamBuilder().withName(event.getMessageContent());
    mSession.refreshTeamMessage();
    event.deleteMessage().join();
    mSession.incrementStage();
  }
}
