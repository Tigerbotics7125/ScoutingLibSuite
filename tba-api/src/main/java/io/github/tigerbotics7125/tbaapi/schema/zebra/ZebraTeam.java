package io.github.tigerbotics7125.tbaapi.schema.zebra;

import com.google.gson.annotations.SerializedName;

public class ZebraTeam {

  @SerializedName("team_key")
  public String teamKey;

  @SerializedName("xs")
  public double[] xs;

  @SerializedName("ys")
  public double[] ys;
}
