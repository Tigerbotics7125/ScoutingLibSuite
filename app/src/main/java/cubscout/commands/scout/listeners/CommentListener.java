package cubscout.commands.scout.listeners;

import cubscout.commands.scout.ScoutSession;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class CommentListener implements MessageCreateListener {

  private ScoutSession mSession;

  public CommentListener(ScoutSession session) {
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

    // Split the message into title and comment
    String title, comment = "";

    if (event.getMessageContent().contains("\n")) {
      // multi line means title and comment
      String[] parts = event.getMessageContent().split("\n");
      title = parts[0];
      comment = event.getMessageContent().substring(event.getMessageContent().indexOf("\n") + new String("\n").length());
    } else {
      // single line means title only
      title = event.getMessageContent();
      comment = "** **";
    }

    mSession.getTeamBuilder().withComment(title, comment);
    mSession.refreshTeamMessage();
    event.deleteMessage().join();
  }
}
