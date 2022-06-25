package io.github.tigerbotics7125.tbaapi.schema.api;

import com.google.gson.annotations.SerializedName;

public class APIStatusAppVersion {

  @SerializedName("min_app_version")
  public int minAppVersion;

  @SerializedName("latest_app_version")
  public int latestAppVersion;
}
