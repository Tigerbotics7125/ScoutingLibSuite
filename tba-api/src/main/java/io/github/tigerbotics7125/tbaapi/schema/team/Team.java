package io.github.tigerbotics7125.tbaapi.schema.team;

import com.google.gson.annotations.SerializedName;

public class Team {

  @SerializedName("key")
  public String key;

  @SerializedName("team_number")
  public int teamNumber;

  @SerializedName("nickname")
  public String nickname;

  @SerializedName("name")
  public String name;

  @SerializedName("school_name")
  public String schoolName;

  @SerializedName("city")
  public String city;

  @SerializedName("state_prov")
  public String stateProv;

  @SerializedName("country")
  public String country;

  @SerializedName("postal_code")
  public String postalCode;

  @SerializedName("website")
  public String website;

  @SerializedName("rookie_year")
  public int rookieYear;

  @SerializedName("motto")
  public String motto;

  public TeamSimple toSimple() {
    return new TeamSimple(this);
  }
}
