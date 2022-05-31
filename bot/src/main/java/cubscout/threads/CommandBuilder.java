package cubscout.threads;

import cubscout.Application;
import cubscout.commands.GetCmd;
import cubscout.commands.api.Purge;
import cubscout.commands.scout.ScoutCmd;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommand;

import java.util.HashSet;
import java.util.Set;

public class CommandBuilder extends Thread {
  private static final Logger logger = LogManager.getLogger(CommandBuilder.class);

  private static final DiscordApi mApi = Application.api;

  @Override
  public void start() {
    setName("Cub Scout - Command Builder");
    super.start();
  }

  @Override
  public void run() {
    // each command is built, then its name is added to a list of names, which we
    // compare other global commands against, if the existing global command is not
    // included in the list of names, then we delete it.
    logger.info("Building Commands...");
    Set<String> currentCommands = new HashSet<>();

    // creates the command, then passes the id and api to listener.
    logger.info(String.format("Building '%s' command...", GetCmd.kName));
    new GetCmd(
        mApi,
        SlashCommand.with(GetCmd.kName, GetCmd.kDescription, GetCmd.kOptions)
            .createGlobal(Application.api)
            .join()
            .getId());
    currentCommands.add(GetCmd.kName);

    logger.info(String.format("Building '%s' command...", Purge.kName));
    new Purge(
        mApi,
        SlashCommand.with(Purge.kName, Purge.kDescription)
            .createGlobal(Application.api)
            .join()
            .getId());
    currentCommands.add(Purge.kName);

    logger.info(String.format("Building '%s' command...", ScoutCmd.kName));
    new ScoutCmd(
        mApi,
        SlashCommand.with(ScoutCmd.kName, ScoutCmd.kDescription)
            .createGlobal(Application.api)
            .join()
            .getId());
    currentCommands.add(ScoutCmd.kName);

    logger.info("Commands built!");

    // clean out old commands
    logger.info("Cleaning out old commands...");
    for (SlashCommand slashCommand : Application.api.getGlobalSlashCommands().join()) {
      if (!currentCommands.contains(slashCommand.getName())) {
        logger.info("Deleting old command: " + slashCommand.getName());
        slashCommand.deleteGlobal().join();
      }
    }
    logger.info("Old commands cleaned!");
  }
}
