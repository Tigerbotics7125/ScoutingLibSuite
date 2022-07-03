package io.github.tigerbotics7125.discordbot;

import io.github.tigerbotics7125.discordbot.commands.GetCmd;
import io.github.tigerbotics7125.discordbot.commands.InfoCmd;
import io.github.tigerbotics7125.discordbot.commands.SlashCommandExecutor;
import io.github.tigerbotics7125.discordbot.commands.scout.ScoutCmd;
import io.github.tigerbotics7125.discordbot.threads.StatusUpdater;
import io.github.tigerbotics7125.tbaapi.TBAReadApi3;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Application {
  private static final Logger logger = LogManager.getLogger();
  private static final Map<String, SlashCommandExecutor> activeCommands = new HashMap<>();

  public static DiscordApi api;
  public static TBAReadApi3 tbaApi;
  public static boolean canUseTba = true;

  public static void main(String[] args) {

    logger.info("Starting Discord Bot");

    // Log into Discord Api
    String mDiscordToken = System.getenv("SCOUT_DISCORD_TOKEN");
    if (mDiscordToken == null) {
      logger.error("Environment variable `SCOUT_DISCORD_TOKEN` does not exist.");
      logger.error(
          "Please set the environment variable `SCOUT_DISCORD_TOKEN` to your Discord token.");
      System.exit(1);
    } else {
      api =
          new DiscordApiBuilder()
              .setToken(mDiscordToken)
              .setAllNonPrivilegedIntents()
              .login()
              .join();
    }

    // Log into TBA Api
    String mTBAAuthToken = System.getenv("TBA_AUTH_TOKEN");
    if (mTBAAuthToken == null) {
      logger.warn("Environment variable `TBA_AUTH_TOKEN` does not exist.");
      logger.warn("TBA API functionality will not be available.");
      canUseTba = false;
    } else {
      tbaApi = new TBAReadApi3(mTBAAuthToken);
    }

    // Start threads
    new StatusUpdater().start();

    // Create slash commands
    activateCommands();

    logger.info("Started Discord Bot");
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
