package cubscout;

import cubscout.threads.CommandBuilder;
import cubscout.threads.StatusUpdater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Application {
  private static final Logger logger = LogManager.getLogger(Application.class);

  public static DiscordApi api;

  public static void main(String[] args) {

    String mDiscordToken = System.getenv("SCOUT_DISCORD_TOKEN");

    if (mDiscordToken == null) {
      logger.error("Environment variable `SCOUT_DISCORD_TOKEN` does not exist.");
      System.exit(1);
    }

    logger.info("Starting Cub Scout...");

    api =
        new DiscordApiBuilder().setToken(mDiscordToken).setAllNonPrivilegedIntents().login().join();

    // set up threads
    new CommandBuilder().start();
    new StatusUpdater().start();

    logger.info("Cub Scout started!");
  }
}
