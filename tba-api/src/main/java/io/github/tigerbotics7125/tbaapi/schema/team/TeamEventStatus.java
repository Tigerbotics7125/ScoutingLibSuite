package io.github.tigerbotics7125.tbaapi.schema.team;

import com.google.gson.annotations.SerializedName;

public class TeamEventStatus {

  @SerializedName("qual")
  public TeamEventStatusRank qual;

  @SerializedName("alliance")
  public TeamEventStatusAlliance alliance;

  @SerializedName("playoff")
  public TeamEventStatusPlayoff playoff;

  @SerializedName("aliance_status_str")
  public String allianceStatusStr;

  @SerializedName("playoff_status_str")
  public String playoffStatusStr;

  @SerializedName("overall_status_str")
  public String overallStatusStr;

  @SerializedName("next_match_key")
  public String nextMatchKey;

  @SerializedName("last_match_key")
  public String lastMatchKey;
}
