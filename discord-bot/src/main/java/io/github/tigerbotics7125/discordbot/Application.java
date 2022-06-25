package io.github.tigerbotics7125.discordbot;

import io.github.tigerbotics7125.discordbot.threads.CommandBuilder;
import io.github.tigerbotics7125.discordbot.threads.StatusUpdater;
import io.github.tigerbotics7125.tbaapi.TBAReadApi3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Application {
  private static final Logger logger = LogManager.getLogger(Application.class);

  public static DiscordApi api;
  public static TBAReadApi3 tbaApi;

  public static void main(String[] args) {

    String mDiscordToken = System.getenv("SCOUT_DISCORD_TOKEN");
    String mTBAAuthToken = System.getenv("TBA_AUTH_TOKEN");

    if (mDiscordToken == null) {
      logger.error("Environment variable `SCOUT_DISCORD_TOKEN` does not exist.");
      System.exit(1);
    }
    if (mTBAAuthToken == null) {
      logger.error("Environment variable `TBA_AUTH_TOKEN` does not exist.");
      System.exit(1);
    }

    logger.info("Starting Cub Scout...");

    api =
        new DiscordApiBuilder().setToken(mDiscordToken).setAllNonPrivilegedIntents().login().join();
    tbaApi = new TBAReadApi3(mTBAAuthToken);

    // set up threads
    new CommandBuilder().start();
    new StatusUpdater().start();

    logger.info("Cub Scout started!");
  }
}
