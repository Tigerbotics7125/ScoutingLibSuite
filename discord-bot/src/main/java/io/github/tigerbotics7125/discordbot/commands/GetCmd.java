package io.github.tigerbotics7125.discordbot.commands;

import static com.mongodb.client.model.Filters.eq;
import static io.github.tigerbotics7125.discordbot.utilities.Constants.kDatabaseName;
import static io.github.tigerbotics7125.discordbot.utilities.Constants.kTeamCollectionName;

import com.mongodb.client.model.Sorts;
import io.github.tigerbotics7125.databaselib.DatabaseAccessor;
import io.github.tigerbotics7125.databaselib.pojos.Team;
import io.github.tigerbotics7125.discordbot.utilities.Constants;
import io.github.tigerbotics7125.discordbot.utilities.TeamUtil;
import io.github.tigerbotics7125.discordbot.utilities.Util;
import java.util.ArrayList;
import java.util.List;
import org.bson.conversions.Bson;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

public class GetCmd extends SCmdListener {

  public static final String kName = "get";
  public static final String kDescription = "Query the database for a team number.";
  public static final List<SlashCommandOption> kOptions =
      List.of(
          SlashCommandOption.create(
              SlashCommandOptionType.DECIMAL, "team_number", "The team to search for.", true));

  public GetCmd(DiscordApi api, long slashCommandId) {
    super(api, slashCommandId);
  }

  @Override
  public void execute(SlashCommandInteraction interaction) {
    // tell discord we will get to them later.
    interaction.respondLater();
    var msg = interaction.createFollowupMessageBuilder();

    // validate the team number
    int teamNumber = interaction.getOptionDecimalValueByIndex(0).get().intValue();
    if (teamNumber < 1 || teamNumber > 9999) {
      msg.addEmbed(new EmbedBuilder().setTitle("Invalid team number").setColor(Constants.kNegative))
          .send()
          .join();
      return; // exit early
    }

    // get matching team entries
    var collection =
        DatabaseAccessor.getInstance()
            .getCollection(kDatabaseName, kTeamCollectionName, Team.class);
    Bson filter = eq("number", teamNumber);

    List<Team> teams = new ArrayList<>();
    // sort by submission time, the newest first.
    collection.find(filter).sort(Sorts.descending("submissionTime")).into(teams);

    // add the count embed
    msg.addEmbed(
        new EmbedBuilder()
            .setTitle(Util.getIterableSize(teams) + " entries for team " + teamNumber + ".")
            .setColor(Constants.kNeutral)
            .setFooter(interaction.getUser().getName(), interaction.getUser().getAvatar())
            .setTimestampToNow());

    // add the team embeds
    int counter = 1;
    for (Team t : teams) {
      if (counter < 10) {
        msg.addEmbed(TeamUtil.toEmbed(t, interaction.getApi()));
      }
      counter++;
    }

    // send the message
    msg.send().join();
  }
}
