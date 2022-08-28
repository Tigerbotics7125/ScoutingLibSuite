package io.github.tigerbotics7125.discordbot.commands;

import io.github.tigerbotics7125.discordbot.Application;
import io.github.tigerbotics7125.discordbot.utilities.Constants;
import java.util.List;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.callback.InteractionMessageBuilder;

public class InfoCmd extends SlashCommandExecutor {

  @Override
  public String getName() {
    return "info";
  }

  @Override
  public String getDescription() {
    return "Displays information about the bot.";
  }

  @Override
  public List<SlashCommandOption> getOptions() {
    return null;
  }

  public InfoCmd() {
    super();
  }

  @Override
  public void execute(SlashCommandInteraction interaction) {
    interaction.respondLater();

    String versions = String.format("Java: `%s`\n", System.getProperty("java.version"));

    StringBuilder tbaApiInfo = new StringBuilder();
    Application.getTBAApi()
        .ifPresent(
            tba ->
                tbaApiInfo
                    .append(
                        String.format(
                            "Current Season: `%s`\n",
                            tba.getStatus().join().orElseThrow().currentSeason))
                    .append(
                        String.format(
                            "TBA datafeed down?: `%s`\n",
                            tba.getStatus().join().orElseThrow().isDatafeedDown)));

    EmbedBuilder eb =
        new EmbedBuilder()
            .setTitle("Scout")
            .setDescription(versions + tbaApiInfo)
            .setColor(Constants.kNeutral)
            .setTimestampToNow();

    long start = System.currentTimeMillis();
    var msg = interaction.createFollowupMessageBuilder().addEmbed(eb).send().join();
    long end = System.currentTimeMillis();

    eb.addField("Current Ping", String.format("%dms", (end - start)));

    if (Application.getTBAApi().isPresent()) {
      start = System.currentTimeMillis();
      Application.getTBAApi().get().getStatus().join();
      end = System.currentTimeMillis();

      eb.addField("TBA API Ping", String.format("%dms", (end - start)));
    }
    new InteractionMessageBuilder()
        .addEmbed(eb)
        .editFollowupMessage(interaction, msg.getId())
        .join();
  }
}
