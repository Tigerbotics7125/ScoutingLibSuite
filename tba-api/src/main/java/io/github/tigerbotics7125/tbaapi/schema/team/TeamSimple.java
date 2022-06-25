package io.github.tigerbotics7125.tbaapi.schema.team;

import com.google.gson.annotations.SerializedName;

public class TeamSimple {

  @SerializedName("key")
  public String key;

  @SerializedName("team_number")
  public int teamNumber;

  @SerializedName("nickname")
  public String nickname;

  @SerializedName("name")
  public String name;

  @SerializedName("city")
  public String city;

  @SerializedName("state_prov")
  public String stateProv;

  @SerializedName("country")
  public String country;

  public TeamSimple(Team team) {
    this.key = team.key;
    this.teamNumber = team.teamNumber;
    this.nickname = team.nickname;
    this.name = team.name;
    this.city = team.city;
    this.stateProv = team.stateProv;
    this.country = team.country;
  }
}
