package io.github.tigerbotics7125.tbaapi;

import com.google.gson.reflect.TypeToken;
import io.github.tigerbotics7125.tbaapi.schema.award.Award;
import io.github.tigerbotics7125.tbaapi.schema.district.DistrictList;
import io.github.tigerbotics7125.tbaapi.schema.district.DistrictRanking;
import io.github.tigerbotics7125.tbaapi.schema.event.Event;
import io.github.tigerbotics7125.tbaapi.schema.event.EventSimple;
import io.github.tigerbotics7125.tbaapi.schema.match.Match;
import io.github.tigerbotics7125.tbaapi.schema.match.MatchSimple;
import io.github.tigerbotics7125.tbaapi.schema.other.Media;
import io.github.tigerbotics7125.tbaapi.schema.team.Team;
import io.github.tigerbotics7125.tbaapi.schema.team.TeamEventStatus;
import io.github.tigerbotics7125.tbaapi.schema.team.TeamSimple;
import java.awt.Robot;
import java.util.HashMap;

public enum TeamCalls {
  Teams("teams/{page_num}", TypeToken.get(Team[].class)),
  TeamsSimple("teams/{page_num}", TypeToken.get(TeamSimple[].class)),
  TeamKeys("teams/{page_num}", TypeToken.get(String[].class)),

  TeamsInYear("teams/{year}/{page_num}", TypeToken.get(Team[].class)),
  TeamsSimpleInYear("teams/{year}/{page_num}/simple", TypeToken.get(TeamSimple[].class)),
  TeamKeysInYear("teams/{year}/{page_num}/keys", TypeToken.get(String[].class)),

  Team("team/{team_key}", TypeToken.get(Team.class)),
  TeamSimple("team/{team_key}/simple", TypeToken.get(TeamSimple.class)),

  YearsParticipated("team/{team_key}/years_participated", TypeToken.get(Integer[].class)),
  Districts("team/{team_key}/districts", TypeToken.get(DistrictList[].class)),
  Robots("team/{team_key}/robots", TypeToken.get(Robot[].class)),

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

  TeamMatchesInEvent("team/{team_key}/event/{event_key}/matches", TypeToken.get(Match[].class)),
  TeamMatchesSimpleInEvent(
      "team/{team_key}/event/{event_key}/matches/simple", TypeToken.get(MatchSimple[].class)),
  TeamMatchKeysInEvent(
      "team/{team_key}/event/{event_key}/matches/keys", TypeToken.get(String[].class)),
  TeamAwardsInEvent("team/{team_key}/event/{event_key}/awards", TypeToken.get(Award[].class)),
  TeamEventStatus("team/{team_key}/event/{event_key}/status", TypeToken.get(TeamEventStatus.class)),

  Awards("team/{team_key}/awards", TypeToken.get(Award[].class)),

  AwardsInYear("team/{team_key}/awards/{year}", TypeToken.get(Award[].class)),
  MatchesInYear("team/{team_key}/matches/{year}", TypeToken.get(Match[].class)),
  MatchesSimpleInYear("team/{team_key}/matches/{year}/simple", TypeToken.get(MatchSimple[].class)),
  MatchKeysInYear("team/{team_key}/matches/{year}/keys", TypeToken.get(String[].class)),
  MediaInYear("team/{team_key}/media/{year}", TypeToken.get(Media[].class)),
  MediaByTag("team/{team_key}/media/tag/{media_tag}", TypeToken.get(Media[].class)),
  MediaByTagInYear("team/{team_key}/media/tag/{media_tag}/{year}", TypeToken.get(Media[].class)),
  SocialMedia("team/{team_key}/social_media", TypeToken.get(Media[].class)),

  TeamsInEvent("event/{event_key}/teams", TypeToken.get(Team[].class)),
  TeamsSimpleInEvent("event/{event_key}/teams/simple", TypeToken.get(TeamSimple[].class)),
  TeamKeysInEvent("event/{event_key}/teams/keys", TypeToken.get(String[].class)),
  TeamEventStatuses(
      "event/{event_key}/teams/statuses", new TypeToken<HashMap<String, TeamEventStatus>>() {}),

  TeamsInDistrict("district/{district_key}/teams", TypeToken.get(Team[].class)),
  TeamsSimpleInDistrict("district/{district_key}/teams/simple", TypeToken.get(TeamSimple[].class)),
  TeamKeysInDistrict("district/{district_key}/teams/keys", TypeToken.get(String[].class)),
  DistrictRankings("district/{district_key}/rankings", TypeToken.get(DistrictRanking.class));

  public final ApiCall<?> apiCall;

  TeamCalls(String endpoint, TypeToken<?> typeToken) {
    apiCall = new ApiCall<>(endpoint, typeToken);
  }
}
