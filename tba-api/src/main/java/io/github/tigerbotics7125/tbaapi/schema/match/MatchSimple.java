package io.github.tigerbotics7125.tbaapi.schema.match;

import com.google.gson.annotations.SerializedName;
import io.github.tigerbotics7125.tbaapi.schema.match.Match.Alliances;

public class MatchSimple {

  @SerializedName("key")
  public String key;

  @SerializedName("comp_level")
  public String compLevel;

  @SerializedName("set_number")
  public int setNumber;

  @SerializedName("match_number")
  public int matchNumber;

  @SerializedName("alliances")
  public Alliances alliances;

  @SerializedName("winning_alliance")
  public String winningAlliance;

  @SerializedName("event_key")
  public String eventKey;

  @SerializedName("time")
  public long time;

  @SerializedName("actual_time")
  public long actualTime;

  @SerializedName("predicted_time")
  public long predictedTime;

  public MatchSimple(Match match) {
    this.key = match.key;
    this.compLevel = match.compLevel;
    this.setNumber = match.setNumber;
    this.matchNumber = match.matchNumber;
    this.alliances = match.alliances;
    this.winningAlliance = match.winningAlliance;
    this.eventKey = match.eventKey;
    this.time = match.time;
    this.actualTime = match.actualTime;
    this.predictedTime = match.predictedTime;
  }
}
