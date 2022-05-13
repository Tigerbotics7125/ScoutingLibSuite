package cubscout.commands.api;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.FindIterable;
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

public class GetTeam extends SCmdListener {

  public GetTeam(DiscordApi api, long slashCommandId) {
    super(api, slashCommandId);
  }

  public static final String kName = "get";
  public static final String kDescription = "Query the database for a team number.";
  public static final List<SlashCommandOption> kOptions =
      Arrays.asList(
          SlashCommandOption.create(
              SlashCommandOptionType.INTEGER, "team_number", "The team to search for.", true));

  @Override
  public void execute(SlashCommandInteraction interaction) {
    int query = interaction.getFirstOptionIntValue().get();
    var response =
        new EmbedBuilder()
            .setAuthor(interaction.getUser())
            .setDescription(String.format("Showing results for `%s`.", query))
            .setTimestampToNow();

    // we have a query key, so try to find it.
    // get collection
    MongoCollection<Team> collection = HighLevelDatabaseUtil.getYearlyCollection();
    FindIterable<Team> results = collection.find(eq("number", query));
    // check if results exist
    if (results.first() == null) {
      // if first result is null, then no results were found
      response.setColor(Color.ORANGE);
      response.setTitle("No results found.");
    } else {
      //response = results.first().toDataEmbed(interaction.getApi());
    }

    // respond with results
    interaction.createImmediateResponder().addEmbed(response).respond();
  }
}
