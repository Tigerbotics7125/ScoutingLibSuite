package io.github.tigerbotics7125.discordbot.commands;

import org.javacord.api.DiscordApi;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public abstract class SCmdListener implements SlashCommandCreateListener {

  long mId;

  public SCmdListener(DiscordApi api, long slashCommandId) {
    mId = slashCommandId;
    api.addSlashCommandCreateListener(this);
  }

  @Override
  public void onSlashCommandCreate(SlashCommandCreateEvent event) {
    SlashCommandInteraction interaction = event.getSlashCommandInteraction();
    if (interaction.getCommandId() == mId) {
      new Thread(() -> execute(interaction)).start();
    }
  }

  public abstract void execute(SlashCommandInteraction interaction);
}
