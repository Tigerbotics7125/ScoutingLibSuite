package cubscout.commands.scout;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.TimerTask;

public class TimeoutTimerTask extends TimerTask {

  private final ScoutSession mSession;
  private final Instant mTimeoutTime;

  public TimeoutTimerTask(ScoutSession session, int minsToTimeout) {
    mSession = session;
    mTimeoutTime = Instant.now().plus(minsToTimeout, ChronoUnit.MINUTES);
  }

  @Override
  public void run() {
    // If we have surpassed our timeout time.
    if (mTimeoutTime.isBefore(Instant.now())) {
      mSession.timeout();
    }
  }

  public Instant getTimeoutTime() {
    return mTimeoutTime;
  }
}
