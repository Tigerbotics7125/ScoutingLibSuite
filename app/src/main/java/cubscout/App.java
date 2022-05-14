package cubscout;

import cubscout.threads.CommandBuilder;
import cubscout.threads.StatusUpdater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class App {
  private static final Logger logger = LogManager.getLogger(App.class);

  private static final String mDiscordToken =
      "TOKEN";
  public static final DiscordApi kApi =
      new DiscordApiBuilder().setAllNonPrivilegedIntents().setToken(mDiscordToken).login().join();

  public static void main(String[] args) {
    logger.info("Starting Cub Scout...");

    // set up threads
    new CommandBuilder().start();
    new StatusUpdater().start();

    logger.info("Cub Scout started!");
  }
}
