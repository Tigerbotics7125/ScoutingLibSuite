package io.github.tigerbotics7125.discordbot.commands;

import io.github.tigerbotics7125.discordbot.Application;
import io.github.tigerbotics7125.discordbot.utilities.Constants;
import java.util.List;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public abstract class SlashCommandExecutor implements SlashCommandCreateListener {

  long mId;

  public SlashCommandExecutor() {
    // Create command.
    if (getOptions() != null) {
      mId =
          SlashCommand.with(getName(), getDescription(), getOptions())
              .createGlobal(Application.getDiscordApi())
              .join()
              .getId();
    } else {
      mId =
          SlashCommand.with(getName(), getDescription())
              .createGlobal(Application.getDiscordApi())
              .join()
              .getId();
    }
    // Add the executor as the listener.
    Application.getDiscordApi().addSlashCommandCreateListener(this);
  }

  public abstract void execute(SlashCommandInteraction interaction) throws Exception;
  // The get methods need to be overriden by the child class.
  public abstract String getName();

  public abstract String getDescription();

  public abstract List<SlashCommandOption> getOptions();

  @Override
  public void onSlashCommandCreate(SlashCommandCreateEvent event) {
    SlashCommandInteraction interaction = event.getSlashCommandInteraction();
    // make sure that the event is for this command.
    if (interaction.getCommandId() == mId) {
      // execute any response code in a new thread to clear the listening thread
      startExecutionThread(interaction);
    }
  }

  private void startExecutionThread(SlashCommandInteraction interaction) {
    new Thread(
            () -> {
              try {
                execute(interaction);
              } catch (Exception e) {
                String errorMessage = getErrorMessage(e, interaction);
                reportError(errorMessage, interaction);
              }
            })
        .start();
  }

  private void reportError(String errorMessage, SlashCommandInteraction interaction) {
    // report to dev
    Application.getDiscordApi()
        .getServerById(Application.EnvironmentOption.REPORT_SERVER.getValue())
        .orElseThrow()
        .getTextChannelById(Application.EnvironmentOption.REPORT_CHANNEL.getValue())
        .orElseThrow()
        .sendMessage(errorMessage)
        .join();

    // report to user
    EmbedBuilder eEmbed = getExceptionEmbed();
    interaction.createFollowupMessageBuilder().addEmbed(eEmbed).send().join();
  }

  /**
   * @param e The exception that was thrown.
   * @param interaction the interaction that threw the exception
   * @return a string explaining what happened.
   */
  private String getErrorMessage(Exception e, SlashCommandInteraction interaction) {
    StringBuilder sb = new StringBuilder();
    sb.append(interaction.getUser().getDiscriminatedName() + "\n");
    sb.append("/" + interaction.getCommandName() + " ");
    interaction
        .getArguments()
        .forEach(
            (arg) -> sb.append(arg.getName() + ": `" + arg.getStringRepresentationValue() + "` "));
    sb.append("\n");
    sb.append("```\n");
    sb.append("Message: " + e.getMessage() + "\n");
    sb.append("Stack Trace:\n");
    for (StackTraceElement ste : e.getStackTrace()) {
      sb.append(ste.toString() + "\n");
    }
    sb.append("```");
    return sb.toString();
  }

  /** @return An embed which explains to the user that something went wrong. */
  private EmbedBuilder getExceptionEmbed() {
    return new EmbedBuilder()
        .setTitle("Something went wrong!")
        .setColor(Constants.kNegative)
        .setDescription("The developers have recieved a report about this issue.")
        .setFooter("Please create an issue on GitHub if the issue persists.");
  }
}
