package cubscout.utilities;

import cubscout.backend.pojos.Team;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

/** A class which helps easily build a team object, and also provides utility functions. */
public class TeamUtil {

  public static boolean isComplete(Team team) {
    return team.getNumber() != 0
        & !team.getName().isBlank()
        & team.getComments().size() != 0
        & team.getScouterId() != 0L;
  }

  public static EmbedBuilder toEmbed(Team team, DiscordApi api) {
    // embed to produce
    var eb = new EmbedBuilder();

    String title =
        String.format(
            "%s || %s",
            team.getNumber() == 0 ? "No Number" : String.valueOf(team.getNumber()),
            team.getName().isBlank() ? "No Name" : team.getName());
    Color color = isComplete(team) ? Constants.kPositive : Constants.kNegative;

    StringBuilder footer = new StringBuilder();
    // if the team has an id from DB add it.
    if (team.getId() != null) {
      footer.append(String.format("ID: %s\n", team.getId()));
    }
    // if the team has a scouter, add them, otherwise do nothing.
    if (team.getScouterId() != 0L) {
      api.getUserById(team.getScouterId())
          // kinda weird but gets activated after join() so it works *shrug*
          .whenComplete(
              (scout, exception) -> {
                if (scout != null) {
                  footer.append(String.format("S: %s\n", scout.getName()));
                  footer.append(String.format("SID: %s", scout.getId()));
                  eb.setFooter(footer.toString(), scout.getAvatar());
                } else {
                  eb.setFooter(footer.toString());
                }
              })
          .join();
    }

    // add everything to the embed.
    eb.setTitle(title).setColor(color).setTimestamp(team.getSubmissionTime());
    team.getComments()
        .forEach(comment -> eb.addField(comment.getTitle(), comment.getContent(), false));

    return eb;
  }
}
