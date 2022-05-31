package cubscout.commands.api;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import cubscout.backend.pojos.Team;
import cubscout.commands.SCmdListener;
import cubscout.utilities.Constants;
import cubscout.utilities.HighLevelDatabaseUtil;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

public class Purge extends SCmdListener {

  public Purge(DiscordApi api, long slashCommandId) {
    super(api, slashCommandId);
  }

  public static final String kName = "purge";
  public static final String kDescription = "Purge the database.";

  @Override
  public void execute(SlashCommandInteraction interaction) {
    var response = new EmbedBuilder().setColor(Constants.kNeutral);
    if (interaction.getUser().getId() == Constants.kAdminUserId) {
      MongoCollection<Team> collection = HighLevelDatabaseUtil.getYearlyCollection();
      collection.deleteMany(Filters.empty());
      response.setTitle("Purged DB!");
    }
    interaction.createImmediateResponder().addEmbed(response).respond();
  }
}
