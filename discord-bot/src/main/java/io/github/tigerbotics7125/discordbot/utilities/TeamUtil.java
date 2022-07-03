package io.github.tigerbotics7125.discordbot.utilities;

import io.github.tigerbotics7125.databaselib.pojos.Team;
import io.github.tigerbotics7125.databaselib.pojos.Team.Comment;
import io.github.tigerbotics7125.discordbot.Application;
import io.github.tigerbotics7125.tbaapi.schema.event.Event;
import java.io.IOException;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;

/** A class which helps easily build a team object, and also provides utility functions. */
public class TeamUtil {

  public static boolean isComplete(Team team) {
    return team.getNumber() != 0
        & !team.getName().isBlank()
        & team.getComments().size() != 0
        & team.getScouterId() != 0L;
  }

  public static boolean isTeamNumberIllegal(int teamNumber) {
    return teamNumber < 1 || teamNumber > 9999;
  }

  public static EmbedBuilder toEmbed(Team team, DiscordApi api) {
    // embed to produce
    var eb = new EmbedBuilder();

    String title =
        String.format(
            "%s || %s",
            team.getNumber() == 0 ? "No Number" : String.valueOf(team.getNumber()),
            team.getName().isBlank() ? "No Name" : team.getName());
    if (team.getNumber() != 0) {
      String url = "https://www.thebluealliance.com/team/" + team.getNumber();
      eb.setAuthor("view on TBA", url, Util.getResource("TBALogo.png"));
    }
    var color = isComplete(team) ? Constants.kPositive : Constants.kNegative;

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

  /**
   * @param team must have number specified.
   * @return Fill a team object with data available from TBA.
   * @throws IOException
   */
  public static Team fillFromTBA(Team team) throws IOException {
    if (team.getNumber() == 0) {
      throw new IllegalArgumentException("Team number must be set.");
    }

    String teamKey = "frc" + team.getNumber();

    team.setName(Application.tbaApi.getTeam(teamKey).nickname);

    Event[] events = Application.tbaApi.getTeamEventsInYear(teamKey, team.getSeason());
    StringBuilder oprDprCcwm = new StringBuilder();
    for (Event event : events) {
      // offensive power rating
      double opr = Application.tbaApi.getEventOPRs(event.key).oprs.get(teamKey);
      // defensive power rating
      double dpr = Application.tbaApi.getEventOPRs(event.key).dprs.get(teamKey);
      // calculated contribution to winning margin
      double ccwm = Application.tbaApi.getEventOPRs(event.key).ccwms.get(teamKey);

      // round values to 3 places
      opr = Math.round(opr * 100.0) / 100.0;
      dpr = Math.round(dpr * 100.0) / 100.0;
      ccwm = Math.round(ccwm * 100.0) / 100.0;

      oprDprCcwm.append(
          String.format(
              "Week %d : %s: OPR: %f, DPR: %f, CCWM: %f\n",
              event.week, event.shortName, opr, dpr, ccwm));
    }

    team.addComment(new Comment("OPR/DPR/CCWM", oprDprCcwm.toString()));

    return team;
  }
}
