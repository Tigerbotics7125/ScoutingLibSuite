package cubscout.commands.scout;

import cubscout.commands.SCmdListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommandInteraction;

public class ScoutCmd extends SCmdListener {

  public static final String kName = "scout";
  public static final String kDescription = "Start an assisted scouting session.";

  public ScoutCmd(DiscordApi api, long slashCommandId) {
    super(api, slashCommandId);
  }

  @Override
  public void execute(SlashCommandInteraction interaction) {
    new ScoutSession(interaction);
  }
}
