package io.github.tigerbotics7125.tbaapi.schema.other;

import com.google.gson.annotations.SerializedName;

public class Media {

  @SerializedName("type")
  public String type;

  @SerializedName("foreign_key")
  public String foreignKey;

  @SerializedName("details")
  public String details;

  @SerializedName("preferred")
  public boolean preferred;

  @SerializedName("direct_url")
  public String directUrl;

  @SerializedName("view_url")
  public String viewUrl;
}
