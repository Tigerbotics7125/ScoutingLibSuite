package io.github.tigerbotics7125.tbaapi.schema.team;

import com.google.gson.annotations.SerializedName;

public class TeamRobot {

  @SerializedName("year")
  public int year;

  @SerializedName("robot_name")
  public String robotName;

  @SerializedName("key")
  public String key;

  @SerializedName("team_key")
  public String teamKey;
}
