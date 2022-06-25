package io.github.tigerbotics7125.discordbot.commands.scout;

import io.github.tigerbotics7125.discordbot.commands.SCmdListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommandInteraction;

public class ScoutCmd extends SCmdListener {

  public static final String kName = "scout";
  public static final String kDescription = "Start an assisted scouting session.";

  public ScoutCmd(DiscordApi api, long slashCommandId) {
    super(api, slashCommandId);
  }

  @Override
  @SuppressWarnings("resource") // leak is handled internally by a TimerTask
  public void execute(SlashCommandInteraction interaction) {
    new ScoutSession(interaction);
  }
}
