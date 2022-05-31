package cubscout.commands;

import com.mongodb.client.model.Sorts;
import cubscout.backend.pojos.Team;
import cubscout.utilities.Constants;
import cubscout.utilities.HighLevelDatabaseUtil;
import cubscout.utilities.TeamUtil;
import org.bson.conversions.Bson;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

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
    var collection = HighLevelDatabaseUtil.getYearlyCollection();
    Bson filter = eq("number", teamNumber);

    List<Team> teams = new ArrayList<>();
    // sort by submission time, the newest first.
    collection.find(filter).sort(Sorts.descending("submissionTime")).into(teams);

    // add the count embed
    msg.addEmbed(
        new EmbedBuilder()
            .setTitle(getIterableSize(teams) + " entries for team " + teamNumber + ".")
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

  private int getIterableSize(Iterable<?> data) {
    if (data instanceof Collection) {
      return ((Collection<?>) data).size();
    }
    int counter = 0;
    for (Object i : data) {
      counter++;
    }
    return counter;
  }
}
