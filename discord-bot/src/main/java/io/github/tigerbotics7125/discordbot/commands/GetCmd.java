package io.github.tigerbotics7125.discordbot.commands;

import static com.mongodb.client.model.Filters.eq;
import static io.github.tigerbotics7125.discordbot.utilities.Constants.kDatabaseName;
import static io.github.tigerbotics7125.discordbot.utilities.Constants.kTeamCollectionName;

import com.mongodb.client.model.Sorts;
import io.github.tigerbotics7125.databaselib.DatabaseAccessor;
import io.github.tigerbotics7125.databaselib.pojos.Team;
import io.github.tigerbotics7125.discordbot.Application;
import io.github.tigerbotics7125.discordbot.utilities.Constants;
import io.github.tigerbotics7125.discordbot.utilities.TeamUtil;
import io.github.tigerbotics7125.discordbot.utilities.Util;
import io.github.tigerbotics7125.tbaapi.schema.event.Event;
import io.github.tigerbotics7125.tbaapi.schema.event.EventOPRs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.bson.conversions.Bson;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

public class GetCmd extends SlashCommandExecutor {

  static {
    setName("get");
    setDescription("Query the database with a team number.");
    setOptions(
        List.of(
            SlashCommandOption.create(
                SlashCommandOptionType.DECIMAL, "team_number", "The team to search for.", true)));
  }

  public GetCmd() {
    super();
  }

  @Override
  public void execute(SlashCommandInteraction interaction) {
    // tell discord we will get to them later.
    interaction.respondLater();
    var msg = interaction.createFollowupMessageBuilder();

    // validate the team number
    // should be fine, as it is a required input.
    int teamNumber = interaction.getOptionDecimalValueByIndex(0).orElseThrow().intValue();
    if (TeamUtil.isTeamNumberIllegal(teamNumber)) {
      msg.addEmbed(new EmbedBuilder().setTitle("Invalid team number").setColor(Constants.kNegative))
          .send()
          .join();
      return;
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

    // get and add TBA result embeds
    String teamKey = "frc" + teamNumber;
    try {
      Event[] teamEvents =
          Application.tbaApi
              .getTeamEventsInYear(
                  teamKey, Application.tbaApi.getStatus().join().orElseThrow().currentSeason)
              .join()
              .orElseThrow();
      Arrays.sort(teamEvents); // sort by week number as defined by compareTo()
      StringBuilder oprs = new StringBuilder();
      if (teamEvents.length == 0) {
        // team has no events this season.
        oprs.append("Team has not competed in events this season.");
      } else {
        for (Event event : teamEvents) {
          try {
            EventOPRs eventOprs = Application.tbaApi.getEventOPRs(event.key).join().orElseThrow();
            double opr = eventOprs.oprs.get(teamKey);
            double dpr = eventOprs.dprs.get(teamKey);
            double ccwm = eventOprs.ccwms.get(teamKey);
            oprs.append(
                String.format(
                    "wk %d: %s\nOPR: `%.2f`, DPR: `%.2f`, CCWM: `%.2f`\n",
                    event.week + 1, event.shortName, opr, dpr, ccwm));
          } catch (NoSuchElementException nsee) {
            // getEventOPRs failed.
            oprs.append(
                String.format("wk %d: %s No OPR/DPR/CCWM data\n", event.week + 1, event.shortName));
          }
        }
      }
      msg.addEmbed(
          new EmbedBuilder()
              .setTitle("TBA Results")
              .setAuthor(
                  "View on TBA",
                  "https://www.thebluealliance.com/team/" + teamNumber,
                  Util.getResource(Util.Resource.TBALogoPng))
              .setColor(Constants.kNeutral)
              .addField("OPR/DPR/CCWM", oprs.toString(), false)
              .setFooter(teamKey));
    } catch (NoSuchElementException nsee) {
      // assume that APIStatus does not fail, leaving the Team to not exist.
      msg.addEmbed(
          new EmbedBuilder()
              .setTitle("TBA Results")
              .setColor(Constants.kNegative)
              .setDescription("Team does not exist!")
              .setFooter(teamKey));
    }

    // add the team embeds
    int counter = 1;
    for (Team t : teams) {
      if (counter < 5) {
        msg.addEmbed(TeamUtil.toEmbed(t, interaction.getApi()));
      }
      counter++;
    }

    // send the message
    msg.send().join();
  }
}