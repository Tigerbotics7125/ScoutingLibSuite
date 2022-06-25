package io.github.tigerbotics7125.tbaapi.schema.match;

import com.google.gson.annotations.SerializedName;

public class MatchAlliance {

  @SerializedName("score")
  public int score;

  @SerializedName("team_keys")
  public String[] teamKeys;

  @SerializedName("surrogate_team_keys")
  public String[] surrogateTeamKeys;

  @SerializedName("dq_team_keys")
  public String[] dqTeamKeys;
}
