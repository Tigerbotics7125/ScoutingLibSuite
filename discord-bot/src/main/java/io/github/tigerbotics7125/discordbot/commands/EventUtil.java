package io.github.tigerbotics7125.discordbot.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class EventUtil {
  /**
   * Checks if the message pertains to the channel and user given.
   *
   * @param event The event to check.
   * @param channel The channel to check against.
   * @param user The user to check against.
   * @return if the event pertains to the channel and user given.
   */
  public static boolean pertainsToEvent(MessageCreateEvent event, TextChannel channel, User user) {
    boolean sameUser = event.getMessageAuthor().asUser().get().getId() == user.getId();
    boolean sameChannel = event.getChannel().getId() == channel.getId();
    return sameUser && sameChannel;
  }
}
