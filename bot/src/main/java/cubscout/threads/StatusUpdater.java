package cubscout.threads;

import cubscout.Application;
import cubscout.backend.pojos.Team;
import cubscout.utilities.HighLevelDatabaseUtil;
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

      long numTeams = HighLevelDatabaseUtil.getTotalDocuments("cub-scout", "2022", Team.class);
      Application.api.updateActivity(ActivityType.PLAYING, "with " + numTeams + " teams.");

      logger.info("Status updated!");
      try {
        // 3 minutes
        sleep(3 * 60 * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
