package io.github.tigerbotics7125.discordbot;

public class DiscordBot {
  public static String getBuildVersion() {
    return DiscordBot.class.getPackage().getImplementationVersion();
  }
}
