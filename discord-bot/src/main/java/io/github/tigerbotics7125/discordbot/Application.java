package io.github.tigerbotics7125.discordbot;

import io.github.tigerbotics7125.discordbot.commands.GetCmd;
import io.github.tigerbotics7125.discordbot.commands.InfoCmd;
import io.github.tigerbotics7125.discordbot.commands.SlashCommandExecutor;
import io.github.tigerbotics7125.discordbot.commands.scout.ScoutCmd;
import io.github.tigerbotics7125.tbaapi.TBAReadApi3;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Application {
  private static final String dscTokenName = "SCOUT_DISCORD_TOKEN";
  private static final String tbaTokenName = "TBA_AUTH_TOKEN";
  private static final Logger logger = LogManager.getLogger();
  private static final Map<String, SlashCommandExecutor> activeCommands = new HashMap<>();
  private static DiscordApi api;
  private static TBAReadApi3 tbaApi;

  public static void main(String[] args) {

    logger.info("Starting Discord Bot");

    // get tokens.
    String dscToken = System.getenv(dscTokenName);
    String tbaToken = System.getenv(tbaTokenName);

    // check for discord token
    if (dscToken == null) {
      logger.error(String.format("Environment variable `%s` does not exist.", dscTokenName));
      logger.error(
          String.format(
              "Please set the environment variable `%s` to your Discord token.", dscTokenName));
      // this is discord bot, no reason to run if no discord lol
      System.exit(1);
    } else {
      api = new DiscordApiBuilder().setToken(dscToken).setAllNonPrivilegedIntents().login().join();
    }

    // check for tba token
    if (tbaToken == null) {
      logger.warn(String.format("Environment variable `%s` does not exist.", tbaTokenName));
      logger.warn("TBA API functionality will not be available.");
    } else {
      tbaApi = new TBAReadApi3(tbaToken);
    }

    new Timer().scheduleAtFixedRate(new StatusUpdater(), 0, 3 * 1000 * 60);

    activateCommands();

    logger.info("Started Discord Bot");
  }

  public static DiscordApi getDiscordApi() {
    return api;
  }

  public static Optional<TBAReadApi3> getTBAApi() {
    return Optional.ofNullable(tbaApi);
  }

  public static void activateCommands() {
    activeCommands.put(GetCmd.mName, new GetCmd());
    activeCommands.put(InfoCmd.mName, new InfoCmd());
    activeCommands.put(ScoutCmd.mName, new ScoutCmd());

    // clean commands
    /*
    for(SlashCommand command : api.getGlobalSlashCommands().join()) {
      if (!activeCommands.containsKey(command.getName())) {
        logger.info("Deleting old command: " + command.getName());

        command.deleteGlobal().join();
      }
    }
    */
  }
}
