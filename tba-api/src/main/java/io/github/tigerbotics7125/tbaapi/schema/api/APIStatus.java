package io.github.tigerbotics7125.tbaapi.schema.api;

import com.google.gson.annotations.SerializedName;

public class APIStatus {

  @SerializedName("current_season")
  public int currentSeason;

  @SerializedName("max_season")
  public int maxSeason;

  @SerializedName("is_datafeed_down")
  public boolean isDatafeedDown;

  @SerializedName("down_events")
  public String[] downEvents;

  @SerializedName("ios")
  public APIStatusAppVersion ios;

  @SerializedName("android")
  public APIStatusAppVersion android;
}
