package io.github.tigerbotics7125.tbaapi;

import com.google.gson.reflect.TypeToken;
import io.github.tigerbotics7125.tbaapi.schema.district.DistrictRanking;
import io.github.tigerbotics7125.tbaapi.schema.event.Event;
import io.github.tigerbotics7125.tbaapi.schema.event.EventSimple;
import io.github.tigerbotics7125.tbaapi.schema.team.Team;
import io.github.tigerbotics7125.tbaapi.schema.team.TeamEventStatus;
import io.github.tigerbotics7125.tbaapi.schema.team.TeamSimple;
import java.util.HashMap;

public enum ListCalls {
  Teams("teams/{page_num}", TypeToken.get(Team[].class)),
  TeamsSimple("teams/{page_num}/simple", TypeToken.get(TeamSimple[].class)),
  TeamKeys("teams/{page_num}/keys", TypeToken.get(String[].class)),

  TeamsInYear("teams/{year}/{page_num}", TypeToken.get(Team[].class)),
  TeamsSimpleInYear("teams/{year}/{page_num}/simple", TypeToken.get(TeamSimple[].class)),
  TeamKeysInYear("teams/{year}/{page_num}/keys", TypeToken.get(String[].class)),
  TeamEventStatusInYear(
      "team/{team_key}/events/{year}/statuses",
      new TypeToken<HashMap<String, TeamEventStatus>>() {}),

  EventsInYear("events/{year}", TypeToken.get(Event[].class)),
  EventsSimpleInYear("events/{year}/simple", TypeToken.get(EventSimple[].class)),
  EventKeysInYear("events/{year}/keys", TypeToken.get(String[].class)),

  TeamsInEvent("event/{event_key}/teams", TypeToken.get(Team[].class)),
  TeamsSimpleInEvent("event/{event_key}/teams/simple", TypeToken.get(TeamSimple[].class)),
  TeamKeysInEvent("event/{event_key}/teams/keys", TypeToken.get(String[].class)),
  TeamEventStatusesInEvent(
      "event/{event_key}/teams/statuses", new TypeToken<HashMap<String, TeamEventStatus>>() {}),

  EventsInDistrict("district/{district_key}/events", TypeToken.get(Event[].class)),
  EventsSimpleInDistrict(
      "district/{district_key}/events/simple", TypeToken.get(EventSimple[].class)),
  EventKeysInDistrict("district/{district_key}/events/keys", TypeToken.get(String[].class)),

  TeamsInDistrict("district/{district_key}/teams", TypeToken.get(Team[].class)),
  TeamsSimpleInDistrict("district/{district_key}/teams/simple", TypeToken.get(TeamSimple[].class)),
  TeamKeysInDistrict("district/{district_key}/teams/keys", TypeToken.get(String[].class)),
  TeamRankingsInDistrict(
      "district/{district_key}/rankings", TypeToken.get(DistrictRanking[].class));

  public final ApiCall<?> kCall;

  ListCalls(String endpoint, TypeToken<?> typeToken) {
    kCall = new ApiCall<>(endpoint, typeToken);
  }
}
