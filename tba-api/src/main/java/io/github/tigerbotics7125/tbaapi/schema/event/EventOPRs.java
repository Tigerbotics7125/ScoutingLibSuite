package io.github.tigerbotics7125.tbaapi.schema.event;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class EventOPRs {
  // offense power rating
  @SerializedName("oprs")
  public Map<String, Double> oprs;

  // defense power rating
  @SerializedName("dprs")
  public Map<String, Double> dprs;

  // calculates contribution to win
  @SerializedName("ccwms")
  public Map<String, Double> ccwms;
}
