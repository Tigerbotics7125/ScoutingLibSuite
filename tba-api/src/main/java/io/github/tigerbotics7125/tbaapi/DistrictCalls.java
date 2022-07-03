package io.github.tigerbotics7125.tbaapi;

import com.google.gson.reflect.TypeToken;
import io.github.tigerbotics7125.tbaapi.schema.district.DistrictList;
import io.github.tigerbotics7125.tbaapi.schema.district.DistrictRanking;
import io.github.tigerbotics7125.tbaapi.schema.district.DistrictRanking.Event;
import io.github.tigerbotics7125.tbaapi.schema.event.EventDistrictPoints;
import io.github.tigerbotics7125.tbaapi.schema.team.Team;
import io.github.tigerbotics7125.tbaapi.schema.team.TeamSimple;

public enum DistrictCalls {
  TeamDistricts("team/{team_key}/districts", TypeToken.get(DistrictList[].class)),
  EventDistrictPoints(
      "event/{event_key}/district_points", TypeToken.get(EventDistrictPoints.class)),

  DistrictsInYear("districts/{year}", TypeToken.get(DistrictList[].class)),

  EventsInDistrict("district/{district_key}/events", TypeToken.get(Event[].class)),
  EventsSimpleInDistrict("district/{district_key}/events/simple", TypeToken.get(Event[].class)),
  EventKeysInDistrict("district/{district_key}/event_keys", TypeToken.get(String[].class)),

  TeamsInDistrict("district/{district_key}/teams", TypeToken.get(Team[].class)),
  TeamsSimpleInDistrict("district/{district_key}/teams/simple", TypeToken.get(TeamSimple[].class)),
  TeamKeysInDistrict("district/{district_key}/teams/keys", TypeToken.get(String[].class)),

  DistrictRankings("district/{district_key}/rankings", TypeToken.get(DistrictRanking.class));

  public final ApiCall<?> apiCall;

  DistrictCalls(String endpoint, TypeToken<?> typeToken) {
    this.apiCall = new ApiCall<>(endpoint, typeToken);
  }
}
