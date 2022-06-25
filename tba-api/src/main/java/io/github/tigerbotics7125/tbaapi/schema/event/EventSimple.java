package io.github.tigerbotics7125.tbaapi.schema.event;

import com.google.gson.annotations.SerializedName;
import io.github.tigerbotics7125.tbaapi.schema.district.DistrictList;

public class EventSimple {

  @SerializedName("key")
  public String key;

  @SerializedName("name")
  public String name;

  @SerializedName("event_code")
  public String eventCode;

  @SerializedName("event_type")
  public int eventType;

  @SerializedName("district")
  public DistrictList district;

  @SerializedName("city")
  public String city;

  @SerializedName("state_prov")
  public String stateProv;

  @SerializedName("country")
  public String country;

  @SerializedName("start_date")
  public String startDate;

  @SerializedName("end_date")
  public String endDate;

  @SerializedName("year")
  public int year;

  public EventSimple(Event event) {
    this.key = event.key;
    this.name = event.name;
    this.eventCode = event.eventCode;
    this.eventType = event.eventType;
    this.district = event.district;
    this.city = event.city;
    this.stateProv = event.stateProv;
    this.country = event.country;
    this.startDate = event.startDate;
    this.endDate = event.endDate;
    this.year = event.year;
  }
}
