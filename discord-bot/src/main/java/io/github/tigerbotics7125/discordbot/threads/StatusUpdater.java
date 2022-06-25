package io.github.tigerbotics7125.discordbot.threads;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.github.tigerbotics7125.databaselib.DatabaseAccessor;
import io.github.tigerbotics7125.databaselib.pojos.Team;
import io.github.tigerbotics7125.discordbot.Application;
import io.github.tigerbotics7125.discordbot.utilities.Constants;
import io.github.tigerbotics7125.discordbot.utilities.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.activity.ActivityType;

public class StatusUpdater extends Thread {
  private static final Logger logger = LogManager.getLogger(StatusUpdater.class);

  @Override
  public void start() {
    setName("Cub Scout - Status Updater");
    super.start();
  }

  @Override
  public void run() {
    while (true) {
      logger.info("Updating Status...");

      Application.api.updateActivity(ActivityType.PLAYING, "with " + countTeams() + " teams.");

      logger.info("Status updated!");
      try {
        // 3 minutes
        sleep(3 * 60 * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private int countTeams() {
    MongoCollection<Team> col =
        DatabaseAccessor.getInstance()
            .getCollection(Constants.kDatabaseName, Constants.kTeamCollectionName, Team.class);

    return Util.getIterableSize(col.find(Filters.empty()));
  }
}
