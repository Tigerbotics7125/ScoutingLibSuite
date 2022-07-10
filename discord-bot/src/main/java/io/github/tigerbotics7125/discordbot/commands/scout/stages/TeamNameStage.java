package io.github.tigerbotics7125.discordbot.commands.scout.stages;

import io.github.tigerbotics7125.discordbot.commands.scout.ScoutSession;
import org.javacord.api.event.message.MessageCreateEvent;

public class TeamNameStage extends Stage {
  public TeamNameStage(ScoutSession session) {
    super(session);

    getSession()
        .getMsgBuilder()
        .addEmbed(
            getSession()
                .getEmbed()
                .setDescription(
                    "Awaiting **Name**\n" + "```\nTeam Name\n```Example:\n```\nTigerbotics\n```"))
        .editFollowupMessage(getSession().getInteraction(), getSession().getMessage().getId());
  }

  @Override
  public void onMessageCreate(MessageCreateEvent event) {
    if (getSession().getScoutId() == event.getMessageAuthor().getId()) {
      String name = event.getMessageContent();

      getSession().getTeam().setName(name);

      getSession().incrementStage();
      getSession().activateStage();

      event.getMessage().delete().join();
    }
  }
}
