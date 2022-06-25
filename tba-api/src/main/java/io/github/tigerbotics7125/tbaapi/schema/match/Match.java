package io.github.tigerbotics7125.tbaapi.schema.match;

import com.google.gson.annotations.SerializedName;

public class Match {

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

  @SerializedName("post_result_time")
  public long postResultTime;

  @SerializedName("videos")
  public Video[] videos;

  public MatchSimple toSimple() {
    return new MatchSimple(this);
  }

  public class Alliances {

    @SerializedName("red")
    public MatchAlliance red;

    @SerializedName("blue")
    public MatchAlliance blue;
  }

  public class Video {

    @SerializedName("type")
    public String type;

    @SerializedName("key")
    public String key;
  }
}
