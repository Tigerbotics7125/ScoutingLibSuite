package io.github.tigerbotics7125.tbaapi;

import com.google.gson.reflect.TypeToken;
import io.github.tigerbotics7125.tbaapi.schema.award.Award;
import io.github.tigerbotics7125.tbaapi.schema.event.Event;
import io.github.tigerbotics7125.tbaapi.schema.event.EventDistrictPoints;
import io.github.tigerbotics7125.tbaapi.schema.event.EventOPRs;
import io.github.tigerbotics7125.tbaapi.schema.event.EventRanking;
import io.github.tigerbotics7125.tbaapi.schema.event.EventSimple;
import io.github.tigerbotics7125.tbaapi.schema.match.Match;
import io.github.tigerbotics7125.tbaapi.schema.match.MatchSimple;
import io.github.tigerbotics7125.tbaapi.schema.other.EliminationAlliance;
import io.github.tigerbotics7125.tbaapi.schema.team.Team;
import io.github.tigerbotics7125.tbaapi.schema.team.TeamEventStatus;
import io.github.tigerbotics7125.tbaapi.schema.team.TeamSimple;
import java.util.HashMap;

public enum EventCalls {
  TeamEvents("team/{team_key}/events", TypeToken.get(Event[].class)),
  TeamEventsSimple("team/{team_key}/events/simple", TypeToken.get(EventSimple[].class)),
  TeamEventKeys("team/{team_key}/events/keys", TypeToken.get(String[].class)),
  TeamEventsInYear("team/{team_key}/events/{year}", TypeToken.get(Event[].class)),
  TeamEventsSimpleInYear(
      "team/{team_key}/events/{year}/simple", TypeToken.get(EventSimple[].class)),
  TeamEventKeysInYear("team/{team_key}/events/{year}/keys", TypeToken.get(String[].class)),
  TeamEventStatusesInYear(
      "team/{team_key}/events/{year}/statuses",
      new TypeToken<HashMap<String, TeamEventStatus>>() {}),

  TeamMatchesInEvent("team/{team_key}/event/{event_key}/matches", TypeToken.get(Event[].class)),
  TeamMatchesSimpleInEvent(
      "team/{team_key}/event/{event_key}/matches/simple", TypeToken.get(EventSimple[].class)),
  TeamMatchKeysInEvent(
      "team/{team_key}/event/{event_key}/matches/keys", TypeToken.get(String[].class)),
  TeamAwardsInEvent("team/{team_key}/event/{event_key}/awards", TypeToken.get(Event[].class)),
  TeamEventStatus("team/{team_key}/event/{event_key}/status", TypeToken.get(TeamEventStatus.class)),

  EventsInYear("events/{year}", TypeToken.get(Event[].class)),
  EventsSimpleInYear("events/{year}/simple", TypeToken.get(EventSimple[].class)),
  EventKeysInYear("events/{year}/keys", TypeToken.get(String[].class)),

  Event("event/{event_key}", TypeToken.get(Event.class)),
  EventSimple("event/{event_key}/simple", TypeToken.get(EventSimple.class)),
  EventAlliances("event/{event_key}/alliances", TypeToken.get(EliminationAlliance[].class)),
  // as these vary by year and are only for old years I'm not going to support them, you can still
  // get them manually with your own ApiCall<>
  // EventInsights("event/{event_key}/insights", )),
  EventOPRs("event/{event_key}/oprs", TypeToken.get(EventOPRs.class)),
  // same for predictions
  // EventPredictions("event/{event_key}/predictions", )),
  EventRankings("event/{event_key}/rankings", TypeToken.get(EventRanking.class)),
  EventDistrictPoints(
      "event/{event_key}/district_points", TypeToken.get(EventDistrictPoints.class)),

  TeamsInEvent("event/{event_key}/teams", TypeToken.get(Team[].class)),
  TeamsSimpleInEvent("event/{event_key}/teams/simple", TypeToken.get(TeamSimple[].class)),
  TeamKeysInEvent("event/{event_key}/teams/keys", TypeToken.get(String[].class)),
  TeamEventStatuses(
      "event/{event_key}/statuses", new TypeToken<HashMap<String, TeamEventStatus>>() {}),

  MatchesInEvent("event/{event_key}/matches", TypeToken.get(Match[].class)),
  MatchesSimpleInEvent("event/{event_key}/matches/simple", TypeToken.get(MatchSimple[].class)),
  MatchKeysInEvent("event/{event_key}/matches/keys", TypeToken.get(String[].class)),
  // timeseries no worky, so no includy

  EventAwards("event/{event_key}/awards", TypeToken.get(Award[].class)),

  EventsInDistrict("district/{district_key}/events", TypeToken.get(Event[].class)),
  EventsSimpleInDistrict(
      "district/{district_key}/events/simple", TypeToken.get(EventSimple[].class)),
  EventKeysInDistrict("district/{district_key}/events/keys", TypeToken.get(String[].class));

  public final ApiCall<?> kCall;

  private EventCalls(String endpoint, TypeToken<?> typetoken) {
    kCall = new ApiCall<>(endpoint, typetoken);
  }
}
