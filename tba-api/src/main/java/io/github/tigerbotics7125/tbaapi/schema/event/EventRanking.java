package io.github.tigerbotics7125.tbaapi.schema.event;

import com.google.gson.annotations.SerializedName;
import io.github.tigerbotics7125.tbaapi.schema.other.WLTRecord;

public class EventRanking {

  @SerializedName("rankings")
  public Rankings[] rankings;

  @SerializedName("extra_stats_info")
  public ExtraStatsInfo[] extraStatsInfo;

  @SerializedName("sort_order_info")
  public SortOrderInfo[] sortOrderInfo;

  public class Rankings {

    @SerializedName("matches_played")
    public int matchesPlayed;

    @SerializedName("qual_average")
    public int qualAverage;

    @SerializedName("extra_stats")
    public double[] extraStats;

    @SerializedName("sort_orders")
    public double[] sortOrders;

    @SerializedName("record")
    public WLTRecord record;

    @SerializedName("rank")
    public int rank;

    @SerializedName("dq")
    public int dq;

    @SerializedName("team_key")
    public String teamKey;
  }

  public class ExtraStatsInfo {

    @SerializedName("precision")
    public double precision;

    @SerializedName("name")
    public String name;
  }

  public class SortOrderInfo {

    @SerializedName("precision")
    public int precision;

    @SerializedName("name")
    public String name;
  }
}
