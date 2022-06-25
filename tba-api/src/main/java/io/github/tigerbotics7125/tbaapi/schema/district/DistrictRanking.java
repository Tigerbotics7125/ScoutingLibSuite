package io.github.tigerbotics7125.tbaapi.schema.district;

import com.google.gson.annotations.SerializedName;

public class DistrictRanking {

  @SerializedName("team_key")
  public String teamKey;

  @SerializedName("rank")
  public int rank;

  @SerializedName("rookie_bonus")
  public int rookieBonus;

  @SerializedName("point_total")
  public int pointTotal;

  @SerializedName("event_points")
  public Event[] eventPoints;

  public class Event {

    @SerializedName("district_cmp")
    public boolean districtCmp;

    @SerializedName("total")
    public int total;

    @SerializedName("alliance_points")
    public int alliancePoints;

    @SerializedName("elim_points")
    public int elimPoints;

    @SerializedName("award_points")
    public int awardPoints;

    @SerializedName("event_key")
    public String eventKey;

    @SerializedName("qal_points")
    public int qalPoints;
  }
}
