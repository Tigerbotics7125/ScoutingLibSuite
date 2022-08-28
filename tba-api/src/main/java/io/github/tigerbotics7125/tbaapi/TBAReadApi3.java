package io.github.tigerbotics7125.tbaapi;

import io.github.tigerbotics7125.tbaapi.schema.api.APIStatus;
import io.github.tigerbotics7125.tbaapi.schema.award.Award;
import io.github.tigerbotics7125.tbaapi.schema.district.DistrictList;
import io.github.tigerbotics7125.tbaapi.schema.district.DistrictRanking;
import io.github.tigerbotics7125.tbaapi.schema.event.Event;
import io.github.tigerbotics7125.tbaapi.schema.event.EventDistrictPoints;
import io.github.tigerbotics7125.tbaapi.schema.event.EventOPRs;
import io.github.tigerbotics7125.tbaapi.schema.event.EventRanking;
import io.github.tigerbotics7125.tbaapi.schema.event.EventSimple;
import io.github.tigerbotics7125.tbaapi.schema.match.Match;
import io.github.tigerbotics7125.tbaapi.schema.match.MatchSimple;
import io.github.tigerbotics7125.tbaapi.schema.other.EliminationAlliance;
import io.github.tigerbotics7125.tbaapi.schema.other.Media;
import io.github.tigerbotics7125.tbaapi.schema.team.Team;
import io.github.tigerbotics7125.tbaapi.schema.team.TeamEventStatus;
import io.github.tigerbotics7125.tbaapi.schema.team.TeamSimple;
import io.github.tigerbotics7125.tbaapi.schema.zebra.Zebra;
import java.awt.Robot;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import okhttp3.OkHttpClient;

@SuppressWarnings("unchecked")
public class TBAReadApi3 {

  protected static final String kApiUrl = "https://www.thebluealliance.com/api/v3/";
  protected static final OkHttpClient kHttpClient = new OkHttpClient();
  protected static String authKey;

  public TBAReadApi3(String readAuthToken) {
    authKey = readAuthToken;
  }

  // TBA
  /** @return API status, and TBA status information. */
  public CompletableFuture<Optional<APIStatus>> getStatus() {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<APIStatus>) TBACalls.Status.apiCall.call();
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  // List
  /**
   * @param pageNum
   * @return A list of Team objects, paginated in groups of 500.
   */
  public CompletableFuture<Optional<Team[]>> getTeams(int pageNum) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Team[]>) ListCalls.Teams.apiCall.call(String.valueOf(pageNum));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param pageNum
   * @return A list of short form TeamSimple objects, paginated in groups of 500.
   */
  public CompletableFuture<Optional<TeamSimple[]>> getTeamsSimple(int pageNum) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<TeamSimple[]>)
                ListCalls.TeamsSimple.apiCall.call(String.valueOf(pageNum));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param pageNum
   * @return A list of team keys, paginated in groups of 500.
   */
  public CompletableFuture<Optional<String[]>> getTeamKeys(int pageNum) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<String[]>) ListCalls.TeamKeys.apiCall.call(String.valueOf(pageNum));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param year
   * @param pageNum
   * @return A list of Team objects that competed in the given year, paginated in groups of 500.
   */
  public CompletableFuture<Optional<Team[]>> getTeamsInYear(int year, int pageNum) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Team[]>)
                ListCalls.TeamsInYear.apiCall.call(String.valueOf(year), String.valueOf(pageNum));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param year
   * @return A list of short form TeamSimple objects that competed in the given year, paginated in
   *     groups of 500.
   */
  public CompletableFuture<Optional<TeamSimple[]>> getTeamsSimpleInYear(int year, int pageNum) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<TeamSimple[]>)
                ListCalls.TeamsSimpleInYear.apiCall.call(
                    String.valueOf(year), String.valueOf(pageNum));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param year
   * @return A list of team keys that competed in the given year, paginated in groups of 500
   *     <p>
   */
  public CompletableFuture<Optional<String[]>> getTeamKeysInYear(int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<String[]>) ListCalls.TeamKeysInYear.apiCall.call(String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param year
   * @return A key-value list of the event statuses for events this team has competed at in the
   *     given year.
   */
  public CompletableFuture<Optional<HashMap<String, TeamEventStatus>>> getTeamEventStatusInYear(
      String teamKey, int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<HashMap<String, TeamEventStatus>>)
                ListCalls.TeamEventStatusInYear.apiCall.call(teamKey, String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param year
   * @return A list of events in the given year.
   */
  public CompletableFuture<Optional<Event[]>> getEventsInYear(int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Event[]>) ListCalls.EventsInYear.apiCall.call(String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param year
   * @return A short-form list of events in the given year.
   */
  public CompletableFuture<Optional<EventSimple[]>> getEventsSimpleInYear(int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<EventSimple[]>)
                ListCalls.EventsSimpleInYear.apiCall.call(String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param year
   * @return A list of event keys in the given year.
   */
  public CompletableFuture<Optional<String[]>> getEventKeysInYear(int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<String[]>)
                ListCalls.EventKeysInYear.apiCall.call(String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param eventKey
   * @return A list of teams that competed in the given event.
   */
  public CompletableFuture<Optional<Team[]>> getTeamsInEvent(String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Team[]>) ListCalls.TeamsInEvent.apiCall.call(eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param eventKey
   * @return a short-form list of Team objects that competed in the given event.
   */
  public CompletableFuture<Optional<TeamSimple[]>> getTeamsSimpleInEvent(String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<TeamSimple[]>) ListCalls.TeamsSimpleInEvent.apiCall.call(eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param eventKey
   * @return A list of team keys that competed in the given event.
   */
  public CompletableFuture<Optional<String[]>> getTeamKeysInEvent(String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<String[]>) ListCalls.TeamKeysInEvent.apiCall.call(eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param eventKey
   * @return A key-value list of the event statuses for teams competing at the given event.
   */
  public CompletableFuture<Optional<HashMap<String, TeamEventStatus>>> getTeamEventStatusesInEvent(
      String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<HashMap<String, TeamEventStatus>>)
                ListCalls.TeamEventStatusesInEvent.apiCall.call(eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param districtKey
   * @return A list of events in the given district.
   */
  public CompletableFuture<Optional<Event[]>> getEventsInDistrict(String districtKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Event[]>) ListCalls.EventsInDistrict.apiCall.call(districtKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param districtKey
   * @return A short-form list of events in the given district.
   */
  public CompletableFuture<Optional<EventSimple[]>> getEventsSimpleInDistrict(String districtKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<EventSimple[]>)
                ListCalls.EventsSimpleInDistrict.apiCall.call(districtKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param districtKey
   * @return A list of event keys in the given district.
   */
  public CompletableFuture<Optional<String[]>> getEventKeysInDistrict(String districtKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<String[]>) ListCalls.EventKeysInDistrict.apiCall.call(districtKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param districtKey
   * @return A list of Team objects that competed in the given district.
   */
  public CompletableFuture<Optional<Team[]>> getTeamsInDistrict(String districtKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Team[]>) ListCalls.TeamsInDistrict.apiCall.call(districtKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param districtKey
   * @return A short-form list of Team objects that competed in the given district.
   */
  public CompletableFuture<Optional<TeamSimple[]>> getTeamsSimpleInDistrict(String districtKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<TeamSimple[]>)
                ListCalls.TeamsSimpleInDistrict.apiCall.call(districtKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param districtKey
   * @return A list of Team objects that competed in events in the given district.
   */
  public CompletableFuture<Optional<String[]>> getTeamKeysInDistrict(String districtKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<String[]>) ListCalls.TeamKeysInDistrict.apiCall.call(districtKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param districtKey
   * @return A list of team district rankings for the given district.
   */
  public CompletableFuture<Optional<DistrictRanking[]>> getTeamRankingsInDistrict(
      String districtKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<DistrictRanking[]>)
                ListCalls.TeamRankingsInDistrict.apiCall.call(districtKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  // Team

  /**
   * @param teamKey
   * @return A Team object for the given team key.
   */
  public CompletableFuture<Optional<Team>> getTeam(String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Team>) TeamCalls.Team.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @return A short-form Team object for the given team key.
   */
  public CompletableFuture<Optional<TeamSimple>> getTeamSimple(String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<TeamSimple>) TeamCalls.TeamSimple.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @return A list of years in which the given team has competed.
   */
  public CompletableFuture<Optional<Integer[]>> getYearsParticipated(String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Integer[]>) TeamCalls.YearsParticipated.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @return An array of Districts representing each year the team was in a district. Will return an
   *     empty array if the team was never in a district.
   */
  public CompletableFuture<Optional<DistrictList[]>> getDistricts(String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<DistrictList[]>) TeamCalls.Districts.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @return A list of year and robot name pairs for each year that a robot name was provided. Will
   *     return an empty array if the team has never named a robot.
   */
  public CompletableFuture<Optional<Robot[]>> getRobots(String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Robot[]>) TeamCalls.Robots.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @return A list of events that the team has competed in.
   */
  public CompletableFuture<Optional<Event[]>> getTeamEvents(String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Event[]>) TeamCalls.TeamEvents.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @return A list of short-form events that the team has competed in.
   */
  public CompletableFuture<Optional<EventSimple[]>> getTeamEventsSimple(String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<EventSimple[]>) TeamCalls.TeamEventsSimple.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @return A list of event keys that the team has competed in.
   */
  public CompletableFuture<Optional<String[]>> getTeamEventKeys(String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<String[]>) TeamCalls.TeamEventKeys.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param year
   * @return A list of event statuses for the given team in the given year.
   */
  public CompletableFuture<Optional<TeamEventStatus[]>> getTeamEventStatusesInYear(
      String teamKey, int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<TeamEventStatus[]>)
                TeamCalls.TeamEventStatusesInYear.apiCall.call(teamKey, String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param eventKey
   * @return A list of matches that the team will play at a given event.
   */
  public CompletableFuture<Optional<Match[]>> getMatchesInEvent(String teamKey, String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Match[]>) TeamCalls.TeamMatchesInEvent.apiCall.call(teamKey, eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param eventKey
   * @return A list of short-form matches that the team will play at a given event.
   */
  public CompletableFuture<Optional<MatchSimple[]>> getMatchesSimpleInEvent(
      String teamKey, String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<MatchSimple[]>)
                TeamCalls.TeamMatchesSimpleInEvent.apiCall.call(teamKey, eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param eventKey
   * @return A list of match keys that the team will play at a given event.
   */
  public CompletableFuture<Optional<String[]>> getMatchKeysInEvent(
      String teamKey, String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<String[]>)
                TeamCalls.TeamMatchKeysInEvent.apiCall.call(teamKey, eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param eventKey
   * @return A list of awards that the team has won at a given event.
   */
  public CompletableFuture<Optional<Award[]>> getTeamAwardsInEvent(
      String teamKey, String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Award[]>) TeamCalls.TeamAwardsInEvent.apiCall.call(teamKey, eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param eventKey
   * @return The event status for the given team at the given event.
   */
  public CompletableFuture<Optional<TeamEventStatus>> getTeamEventStatus(
      String teamKey, String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<TeamEventStatus>)
                TeamCalls.TeamEventStatus.apiCall.call(teamKey, eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @return A list of awards that the team has won.
   */
  public CompletableFuture<Optional<Award[]>> getAwards(String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Award[]>) TeamCalls.Awards.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param year
   * @return A list of awards that the team has won in the given year.
   */
  public CompletableFuture<Optional<Award[]>> getAwardsInYear(String teamKey, int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Award[]>)
                TeamCalls.AwardsInYear.apiCall.call(teamKey, String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param year
   * @return A list of matches that the team has played in the given year.
   */
  public CompletableFuture<Optional<Match[]>> getMatchesInYear(String teamKey, int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Match[]>)
                TeamCalls.MatchesInYear.apiCall.call(teamKey, String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param year
   * @return A list of short-form matches that the team has played in the given year.
   */
  public CompletableFuture<Optional<MatchSimple[]>> getMatchesSimpleInYear(
      String teamKey, int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<MatchSimple[]>)
                TeamCalls.MatchesSimpleInYear.apiCall.call(teamKey, String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param year
   * @return A list of match keys that the team has played in the given year.
   */
  public CompletableFuture<Optional<String[]>> getMatchKeysInYear(String teamKey, int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<String[]>)
                TeamCalls.MatchKeysInYear.apiCall.call(teamKey, String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param year
   * @return A list of media that the team has uploaded in the given year.
   */
  public CompletableFuture<Optional<Media[]>> getMediaInYear(String teamKey, int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Media[]>)
                TeamCalls.MediaInYear.apiCall.call(teamKey, String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param mediaTag
   * @return A list of Media (videos / pictures) for the given team and tag.
   */
  public CompletableFuture<Optional<Media[]>> getMediaByTag(String teamKey, String mediaTag) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Media[]>) TeamCalls.MediaByTag.apiCall.call(teamKey, mediaTag);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param mediaTag
   * @param year
   * @return A list of Media (videos / pictures) for the given team and tag in the given year.
   */
  public CompletableFuture<Optional<Media[]>> getMediaByTagInYear(
      String teamKey, String mediaTag, int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Media[]>)
                TeamCalls.MediaByTagInYear.apiCall.call(teamKey, mediaTag, String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @return A list of social media that the team has.
   */
  public CompletableFuture<Optional<Media[]>> getSocialMedia(String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Media[]>) TeamCalls.SocialMedia.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @return A map of event key to event status for the given team.
   */
  public CompletableFuture<Optional<HashMap<String, TeamEventStatus>>> getTeamEventStatuses(
      String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<HashMap<String, TeamEventStatus>>)
                TeamCalls.TeamEventStatuses.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param districtKey
   * @return A list of district rankings for the given district.
   */
  public CompletableFuture<Optional<DistrictRanking[]>> getDistrictRankings(String districtKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<DistrictRanking[]>)
                DistrictCalls.DistrictRankings.apiCall.call(districtKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  // Event

  /**
   * @param teamKey
   * @param year
   * @return A list of events that the team has played in the given year.
   */
  public CompletableFuture<Optional<Event[]>> getTeamEventsInYear(String teamKey, int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Event[]>)
                TeamCalls.TeamEventsInYear.apiCall.call(teamKey, String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param year
   * @return A list of short-form events that the team has played in the given year.
   */
  public CompletableFuture<Optional<Event[]>> getTeamEventsSimpleInYear(String teamKey, int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Event[]>)
                TeamCalls.TeamEventsSimpleInYear.apiCall.call(teamKey, String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param year
   * @return A list of event keys that the team has played in the given year.
   */
  public CompletableFuture<Optional<String[]>> getTeamEventKeysInYear(String teamKey, int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<String[]>)
                TeamCalls.TeamEventKeysInYear.apiCall.call(teamKey, String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param eventKey
   * @return A list of matches that the team has played in the given event.
   */
  public CompletableFuture<Optional<Match[]>> getTeamMatchesInEvent(
      String teamKey, String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Match[]>) TeamCalls.TeamMatchesInEvent.apiCall.call(teamKey, eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param eventKey
   * @return A list of short-form matches that the team has played in the given event.
   */
  public CompletableFuture<Optional<MatchSimple[]>> getTeamMatchesSimpleInEvent(
      String teamKey, String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<MatchSimple[]>)
                TeamCalls.TeamMatchesSimpleInEvent.apiCall.call(teamKey, eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param teamKey
   * @param eventKey
   * @return A list of match keys that the team has played in the given event.
   */
  public CompletableFuture<Optional<String[]>> getTeamMatchKeysInEvent(
      String teamKey, String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<String[]>)
                TeamCalls.TeamMatchKeysInEvent.apiCall.call(teamKey, eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param eventKey
   * @return The event with the given key.
   */
  public CompletableFuture<Optional<Event>> getEvent(String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Event>) EventCalls.Event.apiCall.call(eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param eventKey
   * @return The short-form event with the given key.
   */
  public CompletableFuture<Optional<EventSimple>> getEventSimple(String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<EventSimple>) EventCalls.EventSimple.apiCall.call(eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param eventKey
   * @return A list of alliances for the given event.
   */
  public CompletableFuture<Optional<EliminationAlliance[]>> getEventAlliances(String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<EliminationAlliance[]>)
                EventCalls.EventAlliances.apiCall.call(eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param eventKey
   * @return A list of OPRs for the given event.
   */
  public CompletableFuture<Optional<EventOPRs>> getEventOPRs(String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<EventOPRs>) EventCalls.EventOPRs.apiCall.call(eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param eventKey
   * @return A list of rankings for the given event.
   */
  public CompletableFuture<Optional<EventRanking>> getEventRankings(String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<EventRanking>) EventCalls.EventRankings.apiCall.call(eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param eventKey
   * @return A list of district points for the given event.
   */
  public CompletableFuture<Optional<EventDistrictPoints>> getEventDistrictPoints(String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<EventDistrictPoints>)
                EventCalls.EventDistrictPoints.apiCall.call(eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param eventKey
   * @return A list of awards for the given event.
   */
  public CompletableFuture<Optional<Award[]>> getEventAwards(String eventKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Award[]>) EventCalls.EventAwards.apiCall.call(eventKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  // Match

  /**
   * @param matchKey
   * @return The match with the given key.
   */
  public CompletableFuture<Optional<Match>> getMatch(String matchKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Match>) MatchCalls.Match.apiCall.call(matchKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param matchKey
   * @return The short-form match with the given key.
   */
  public CompletableFuture<Optional<MatchSimple>> getMatchSimple(String matchKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<MatchSimple>) MatchCalls.MatchSimple.apiCall.call(matchKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param matchKey
   * @return The zebra motion works for the given match.
   */
  public CompletableFuture<Optional<Zebra>> getZebraMotionWorksInMatch(String matchKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<Zebra>) MatchCalls.ZebraMotionWorksInMatch.apiCall.call(matchKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  // District

  /**
   * @param teamKey
   * @return The districts in which the given team is a member.
   */
  public CompletableFuture<Optional<DistrictList[]>> getTeamDistricts(String teamKey) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<DistrictList[]>) DistrictCalls.TeamDistricts.apiCall.call(teamKey);
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }

  /**
   * @param year
   * @return The districts in the given year.
   */
  public CompletableFuture<Optional<DistrictList[]>> getDistrictsInYear(int year) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            return (Optional<DistrictList[]>)
                DistrictCalls.DistrictsInYear.apiCall.call(String.valueOf(year));
          } catch (IOException e) {
            throw new CompletionException(e);
          }
        });
  }
}
