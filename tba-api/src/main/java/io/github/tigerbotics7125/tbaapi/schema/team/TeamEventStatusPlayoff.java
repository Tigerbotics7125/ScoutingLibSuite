package io.github.tigerbotics7125.tbaapi.schema.team;

import com.google.gson.annotations.SerializedName;
import io.github.tigerbotics7125.tbaapi.schema.other.WLTRecord;

public class TeamEventStatusPlayoff {

  @SerializedName("description")
  public String description;

  @SerializedName("level")
  public String level;

  @SerializedName("current_level_record")
  public WLTRecord current_level_record;

  @SerializedName("record")
  public WLTRecord record;

  @SerializedName("status")
  public String status;
}
