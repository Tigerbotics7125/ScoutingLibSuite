package cubscout.commands.scout.listeners;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import cubscout.commands.EventUtil;
import cubscout.commands.scout.ScoutSession;

public class ImageListener implements MessageCreateListener {

  ScoutSession mSession;

  public ImageListener(ScoutSession session) {
    mSession = session;
  }

  @Override
  public void onMessageCreate(MessageCreateEvent event) {
    
    if (!EventUtil.pertainsToEvent(event, mSession.getScoutMessage().getChannel(), mSession.getScouter())) {
      return;
    }

    // for every image attachment in the message download it, then add it.
    if (!event.getMessageAttachments().isEmpty()) {
      for (MessageAttachment att : event.getMessageAttachments()) {
        if (att.isImage()) {
          att.downloadAsImage().thenAcceptAsync(image -> {
            mSession.getTeamBuilder().withImage(image);
            mSession.refreshTeamMessage();
          });
        }
        
      }
    }
    event.deleteMessage().join();
  }
}
