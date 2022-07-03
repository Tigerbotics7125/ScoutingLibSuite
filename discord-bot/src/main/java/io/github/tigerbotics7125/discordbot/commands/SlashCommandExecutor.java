package io.github.tigerbotics7125.discordbot.commands;

import io.github.tigerbotics7125.discordbot.Application;
import java.util.List;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public abstract class SlashCommandExecutor implements SlashCommandCreateListener {

  public static String mName;
  public static String mDescription;
  public static List<SlashCommandOption> mOptions;

  long mId;

  public SlashCommandExecutor() {
    if (mOptions != null) {
      mId =
          SlashCommand.with(mName, mDescription, mOptions)
              .createGlobal(Application.api)
              .join()
              .getId();
    } else {
      mId = SlashCommand.with(mName, mDescription).createGlobal(Application.api).join().getId();
    }
    Application.api.addSlashCommandCreateListener(this);
  }

  @Override
  public void onSlashCommandCreate(SlashCommandCreateEvent event) {
    SlashCommandInteraction interaction = event.getSlashCommandInteraction();
    // run the execution in a new thread so we don't block the listener thread
    if (interaction.getCommandId() == mId) {
      new Thread(() -> execute(interaction)).start();
    }
  }

  /** Sets the name to be used when registering */
  public static void setName(String name) {
    mName = name;
  }

  /** Sets the description to be used when registering */
  public static void setDescription(String description) {
    mDescription = description;
  }

  /** Sets the options to be used when registering */
  public static void setOptions(List<SlashCommandOption> options) {
    mOptions = options;
  }

  /** Performs the commands actions */
  public abstract void execute(SlashCommandInteraction interaction);
}
