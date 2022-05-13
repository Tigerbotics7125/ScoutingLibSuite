package cubscout.commands.api;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

public class TeamUtil {

  /**
   * @param teamNumber
   * @return a filter which matches all teams with the given team number
   */
  public static Bson matchTeam(int teamNumber) {
    return Filters.eq("number", teamNumber);
  }

  /**
   * @param teamNumber
   * @return if the team number is a valid FRC team number
   */
  public static boolean validateTeamNumber(int teamNumber) {
    return teamNumber >= 1 && teamNumber <= 9999;
  }
}
