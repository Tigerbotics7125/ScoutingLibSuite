package io.github.tigerbotics7125.tbaapi.schema.award;

import com.google.gson.annotations.SerializedName;

public class AwardRecipient {

  @SerializedName("team_key")
  public String teamKey;

  @SerializedName("awaredee")
  public String awaredee;
}
