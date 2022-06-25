package io.github.tigerbotics7125.tbaapi.schema.other;

import com.google.gson.annotations.SerializedName;

public class EliminationAlliance {

  @SerializedName("name")
  public String name;

  @SerializedName("backup")
  public Backup backup;

  @SerializedName("declines")
  public String[] declines;

  @SerializedName("picks")
  public String[] picks;

  @SerializedName("status")
  public Status status;

  public class Backup {

    @SerializedName("in")
    public String in;

    @SerializedName("out")
    public String out;
  }

  public class Status {

    @SerializedName("playoff_average")
    public double playoffAverage;

    @SerializedName("level")
    public String level;

    @SerializedName("record")
    public WLTRecord record;

    @SerializedName("current_level_record")
    public WLTRecord currentLevelRecord;

    @SerializedName("status")
    public String status;
  }
}
