package io.github.tigerbotics7125.discordbot.commands.scout;

import io.github.tigerbotics7125.discordbot.commands.SlashCommandExecutor;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

public class ScoutCmd extends SlashCommandExecutor {

  @Override
  public String getName() {
    return "scout";
  }

  @Override
  public String getDescription() {
    return "Start an assisted scouting session.";
  }

  @Override
  public List<SlashCommandOption> getOptions() {
    return List.of(
        SlashCommandOption.create(
            SlashCommandOptionType.DECIMAL, "team_number", "The team to scout.", true));
  }

  public ScoutCmd() {
    super();
  }

  @Override
  @SuppressWarnings("resource") // AutoCloseable leak is handled internally by a TimerTask
  public void execute(SlashCommandInteraction interaction) throws Exception {
    new ScoutSession(interaction);
  }
}
