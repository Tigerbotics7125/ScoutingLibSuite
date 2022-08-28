package io.github.tigerbotics7125.discordbot;

import io.github.tigerbotics7125.databaselib.DatabaseAccessor;
import io.github.tigerbotics7125.discordbot.commands.GetCmd;
import io.github.tigerbotics7125.discordbot.commands.InfoCmd;
import io.github.tigerbotics7125.discordbot.commands.SlashCommandExecutor;
import io.github.tigerbotics7125.discordbot.commands.scout.ScoutCmd;
import io.github.tigerbotics7125.tbaapi.TBAReadApi3;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.interaction.SlashCommand;

public class Application {

  public enum EnvironmentOption {
    DISCORD_TOKEN(
        "DISCORD_TOKEN",
        "",
        () -> {
          logger.error("DISCORD_TOKEN environment variable not set.");
          System.exit(1);
        }),
    TBA_TOKEN(
        "TBA_TOKEN",
        "",
        () -> {
          logger.warn("TBA_TOKEN envrionment variable not set.");
          logger.warn("TBA functionality will not be available.");
        }),
    MONGODB_URI(
        "MONGODB_URI",
        "mongodb://mongo:27017",
        () -> {
          logger.warn("MONGODB_URI is not set. Using default for docker compose.");
        }),
    REPORT_SERVER(
        "REPORT_SERVER",
        "979576890190884904",
        () -> {
          logger.warn("REPORT_SERVER is not set.");
        }),
    REPORT_CHANNEL(
        "REPORT_CHANNEL",
        "999349782592114699",
        () -> {
          logger.warn("REPORT_CHANNEL is not set.");
        }),
    ;

    private final String name;
    private final String defaultValue;

    EnvironmentOption(String name, String defaultValue, Runnable onDefault) {
      // logger.info(String.format("Setting up env var `%s` with default value `%s`", name,
      // defaultValue));
      this.name = name;
      this.defaultValue = defaultValue;
      String sysVal = System.getenv(name);
      if (sysVal == null || sysVal.equals(defaultValue)) {
        onDefault.run();
      }
    }

    public String getValue() {
      String sysVal = System.getenv(name);
      return sysVal == null ? defaultValue : sysVal;
    }
  }

  public enum EnvironmentFlag {
    DEVELOPMENT("DEVELOPMENT", false),
    ;

    private final String name;
    private final boolean defaultValue;

    EnvironmentFlag(String name, boolean defaultValue) {
      this.name = name;
      this.defaultValue = defaultValue;
    }

    public boolean get() {
      String sysVal = System.getenv(name);
      return sysVal == null ? defaultValue : Boolean.parseBoolean(sysVal);
    }
  }

  private static final Logger logger = LogManager.getLogger(Application.class);
  private static final List<SlashCommandExecutor> activeCommands = new ArrayList<>();
  private static DiscordApi discordApi;
  private static TBAReadApi3 tbaApi;

  public static void main(String[] args) {

    logger.info("Starting Discord Bot");

    setupDiscord();
    setupTBA();
    setupMongo();
    setupStatusUpdater();

    activateCommands();

    logger.info("Started Discord Bot");
  }

  /** Login to Discord. */
  private static void setupDiscord() {
    discordApi =
        new DiscordApiBuilder()
            .setToken(EnvironmentOption.DISCORD_TOKEN.getValue())
            .setAllNonPrivilegedIntents()
            .login()
            .join();
  }

  /**
   * Login to TBA api. If the environment variable is not set, then the api will not be available.
   */
  private static void setupTBA() {
    if (!EnvironmentOption.TBA_TOKEN.getValue().isEmpty()) {
      tbaApi = new TBAReadApi3(EnvironmentOption.TBA_TOKEN.getValue());
    }
  }

  /** Connect to MongoDB. */
  private static void setupMongo() {
    DatabaseAccessor.getInstance().setUri(EnvironmentOption.MONGODB_URI.getValue());
  }

  /** Start a {@link Timer} that updates the status of the bot. */
  private static void setupStatusUpdater() {
    new Timer().scheduleAtFixedRate(new StatusUpdater(), 0, 3 * 1000 * 60);
  }

  /** @return The {@link DiscordApi} of the bot. */
  public static DiscordApi getDiscordApi() {
    return discordApi;
  }

  /**
   * @return An {@link Optional} containing the {@link TBAReadApi3} of the bot.
   * @implNote returns null if the {@link EnvironmentOption#TBA_TOKEN} is not set.
   */
  public static Optional<TBAReadApi3> getTBAApi() {
    return Optional.ofNullable(tbaApi);
  }

  /** Start all commands, and clean old commands. */
  public static void activateCommands() {
    // start all commands and add them to list of active commands
    activeCommands.addAll(List.of(new InfoCmd(), new GetCmd(), new ScoutCmd()));
    activeCommands.forEach((cmd) -> logger.info("Activated command: " + cmd.getName()));

    // clean inactive commands
    for (SlashCommand command : discordApi.getGlobalSlashCommands().join()) {
      // using a boolean area to get around effectively final in lambda
      final boolean[] isActive = new boolean[] {false};
      activeCommands.forEach(
          (cmd) -> {
            if (cmd.getName().equals(command.getName())) {
              isActive[0] = true;
            }
          });
      if (!isActive[0]) {
        logger.info("Deleting old command: " + command.getName());
        command.deleteGlobal().join();
      }
    }
  }
}
