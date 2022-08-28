package io.github.tigerbotics7125.discordbot;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.github.tigerbotics7125.databaselib.DatabaseAccessor;
import io.github.tigerbotics7125.databaselib.pojos.Team;
import io.github.tigerbotics7125.discordbot.utilities.Constants;
import io.github.tigerbotics7125.discordbot.utilities.Util;
import java.util.TimerTask;
import org.javacord.api.entity.activity.ActivityType;

public class StatusUpdater extends TimerTask {

  @Override
  public void run() {
    Application.getDiscordApi()
        .updateActivity(ActivityType.PLAYING, "with " + countTeams() + " teams.");
  }

  /** @return The number of teams in the database. */
  private int countTeams() {
    MongoCollection<Team> col =
        DatabaseAccessor.getInstance()
            .getCollection(Constants.kDatabaseName, Constants.kTeamCollectionName, Team.class);
    return Util.getIterableSize(col.find(Filters.empty()));
  }
}
