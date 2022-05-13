package cubscout.commands.api;

import com.mongodb.client.MongoCollection;

import cubscout.backend.pojos.Team;
import cubscout.commands.SCmdListener;
import cubscout.utilities.HighLevelDatabaseUtil;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

public class PostTeam extends SCmdListener {

  public PostTeam(DiscordApi api, long slashCommandId) {
    super(api, slashCommandId);
  }

  public static final String kName = "post";
  public static final String kDescription = "Post a data entry to the database.";
  public static final List<SlashCommandOption> kOptions =
      Arrays.asList(
          SlashCommandOption.create(
              SlashCommandOptionType.INTEGER, "team_number", "Ex: 7125", true),
          SlashCommandOption.create(
              SlashCommandOptionType.STRING, "team_name", "Ex: Tigerbotics", true));

  @Override
  public void execute(SlashCommandInteraction interaction) {
    int teamNumber = interaction.getFirstOptionIntValue().get();
    String teamName = interaction.getSecondOptionStringValue().get();

    var response = new EmbedBuilder().setAuthor(interaction.getUser()).setTimestampToNow();
    var responder = interaction.createImmediateResponder();

    MongoCollection<Team> collection = HighLevelDatabaseUtil.getYearlyCollection();

    Team team =
        new Team()
            .withNumber(teamNumber)
            .withName(teamName)
            .withScouterId(interaction.getUser().getIdAsString());

    // check if team is already in database
    if (collection.find(TeamUtil.matchTeam(teamNumber)).first() != null) {
      // there is already a team with this number
      // run update command instead
      response.setTitle("Team already exists!");
      response.setColor(Color.RED);
      responder.addEmbed(response).respond();
      return;
    }
    // team is safe to add, add it
    collection.insertOne(team);

    // return if successful
    if (collection.find(TeamUtil.matchTeam(teamNumber)).first() != null) {
      // the team item was successfully inserted, and retrieved
      response.setColor(Color.GREEN);
      response.setTitle("Success!");
      response.setDescription(
          String.format("Team `%s`-`%s` successfully added.", teamNumber, teamName));
    } else {
      response.setColor(Color.RED);
      response.setTitle("Error!");
      response.setDescription(
          String.format("Team `%s`-`%s` could not be added at this time.", teamNumber, teamName));
    }

    // respond with results
    responder.addEmbed(response).respond();
  }
}
