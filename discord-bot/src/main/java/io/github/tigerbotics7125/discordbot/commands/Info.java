package io.github.tigerbotics7125.discordbot.commands;

import io.github.tigerbotics7125.databaselib.DatabaseLib;
import io.github.tigerbotics7125.discordbot.Application;
import io.github.tigerbotics7125.discordbot.DiscordBot;
import io.github.tigerbotics7125.discordbot.utilities.Constants;
import io.github.tigerbotics7125.tbaapi.TBAReadApi3;
import java.io.IOException;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionMessageBuilder;

public class Info extends SCmdListener {
  public static final String kName = "info";
  public static final String kDescription = "Displays information about the bot.";

  public Info(DiscordApi api, long slashCommandId) {
    super(api, slashCommandId);
  }

  @Override
  public void execute(SlashCommandInteraction interaction) {
    interaction.respondLater();

    StringBuilder versions = new StringBuilder();
    versions.append(String.format("Java: `%s`\n", System.getProperty("java.version")));
    versions.append(String.format("Bot: `%s`\n", DiscordBot.getBuildVersion()));
    versions.append(String.format("DBLib: `%s`\n", DatabaseLib.getBuildVersion()));
    versions.append(String.format("TBAApi: `%s`\n", TBAReadApi3.getBuildVersion()));

    StringBuilder tbaApiInfo = new StringBuilder();
    try {
      tbaApiInfo.append(
          String.format("Current Season: `%s`\n", Application.tbaApi.getStatus().currentSeason));
      tbaApiInfo.append(
          String.format(
              "TBA datafeed down?: `%s`\n", Application.tbaApi.getStatus().isDatafeedDown));
    } catch (IOException e) {
      // apis suck.
      e.printStackTrace();
    }

    EmbedBuilder eb =
        new EmbedBuilder()
            .setTitle("Scout")
            .setDescription(versions.toString() + tbaApiInfo.toString())
            .setColor(Constants.kNeutral)
            .setTimestampToNow();
    long start = System.currentTimeMillis();
    var msg = interaction.createFollowupMessageBuilder().addEmbed(eb).send().join();
    long end = System.currentTimeMillis();

    eb.addField("Current Ping", String.format("%dms", (end - start)));

    new InteractionMessageBuilder()
        .addEmbed(eb)
        .editFollowupMessage(interaction, msg.getId())
        .join();
  }
}
