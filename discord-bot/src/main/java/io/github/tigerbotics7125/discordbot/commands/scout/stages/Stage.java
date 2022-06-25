package io.github.tigerbotics7125.discordbot.commands.scout.stages;

import io.github.tigerbotics7125.discordbot.commands.scout.ScoutSession;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

/**
 * A base class for a Stage session; children must implement the onMessageCreate method themselves.
 */
public abstract class Stage implements MessageCreateListener, AutoCloseable {

  private final ScoutSession kSession;
  private final ListenerManager<MessageCreateListener> kListener;

  public Stage(ScoutSession session) {
    kSession = session;
    kListener = kSession.getChannel().addMessageCreateListener(this);
  }

  @Override
  public void close() {
    kListener.remove();
  }

  /** @return The session that this stage belongs to. */
  protected ScoutSession getSession() {
    return kSession;
  }
}
