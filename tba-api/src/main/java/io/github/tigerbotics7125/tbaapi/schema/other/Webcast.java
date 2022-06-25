package io.github.tigerbotics7125.tbaapi.schema.other;

import com.google.gson.annotations.SerializedName;

public class Webcast {

  @SerializedName("type")
  public String type;

  @SerializedName("channel")
  public String channel;

  @SerializedName("date")
  public String date;

  @SerializedName("file")
  public String file;
}
