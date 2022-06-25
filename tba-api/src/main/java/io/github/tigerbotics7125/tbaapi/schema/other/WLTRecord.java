package io.github.tigerbotics7125.tbaapi.schema.other;

import com.google.gson.annotations.SerializedName;

public class WLTRecord {

  @SerializedName("losses")
  public int losses;

  @SerializedName("wins")
  public int wins;

  @SerializedName("ties")
  public int ties;
}
