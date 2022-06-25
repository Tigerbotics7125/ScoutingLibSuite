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
import okhttp3.OkHttpClient;

@SuppressWarnings("unchecked")
public class TBAReadApi3 {

  protected static final String kApiUrl = "https://www.thebluealliance.com/api/v3/";
  protected static final OkHttpClient kHttpClient = new OkHttpClient();
  protected static String authKey;

  public TBAReadApi3(String readAuthToken) {
    authKey = readAuthToken;
  }

  public static String getBuildVersion() {
    return TBAReadApi3.class.getPackage().getImplementationVersion();
  }

  // TBA
  private final ApiCall<APIStatus> status = (ApiCall<APIStatus>) TBACalls.Status.kCall;
  /**
   * @return API status, and TBA status information.
   * @throws IOException
   */
  public APIStatus getStatus() throws IOException {
    return status.call();
  }

  // List
  private final ApiCall<Team[]> teams = (ApiCall<Team[]>) ListCalls.Teams.kCall;
  /**
   * @param pageNum
   * @return A list of Team objects, paginated in groups of 500.
   * @throws IOException
   */
  public Team[] getTeams(int pageNum) throws IOException {
    return teams.call(String.valueOf(pageNum));
  }

  private final ApiCall<TeamSimple[]> teamsSimple =
      (ApiCall<TeamSimple[]>) ListCalls.TeamsSimple.kCall;
  /**
   * @param pageNum
   * @return A list of short form TeamSimple objects, paginated in groups of 500.
   * @throws IOException
   */
  public TeamSimple[] getTeamsSimple(int pageNum) throws IOException {
    return teamsSimple.call(String.valueOf(pageNum));
  }

  private final ApiCall<String[]> teamKeys = (ApiCall<String[]>) ListCalls.TeamKeys.kCall;
  /**
   * @param pageNum
   * @return A list of team keys, paginated in groups of 500
   *     <p>(Note, each page will not have 500 teams, but will include the teams within that range
   *     of 500.)
   * @throws IOException
   */
  public String[] getTeamKeys(int pageNum) throws IOException {
    return teamKeys.call(String.valueOf(pageNum));
  }

  private final ApiCall<Team[]> teamsInYear = (ApiCall<Team[]>) ListCalls.TeamsInYear.kCall;
  /**
   * @param year
   * @return A list of Team objects that competed in the given year, paginated in groups of 500.
   * @throws IOException
   */
  public Team[] getTeamsInYear(int year) throws IOException {
    return teamsInYear.call(String.valueOf(year));
  }

  private final ApiCall<TeamSimple[]> teamsSimpleInYear =
      (ApiCall<TeamSimple[]>) ListCalls.TeamsSimpleInYear.kCall;
  /**
   * @param year
   * @return A list of short form TeamSimple objects that competed in the given year, paginated in
   *     groups of 500.
   * @throws IOException
   */
  public TeamSimple[] getTeamsSimpleInYear(int year) throws IOException {
    return teamsSimpleInYear.call(String.valueOf(year));
  }

  private final ApiCall<String[]> teamKeysInYear =
      (ApiCall<String[]>) ListCalls.TeamKeysInYear.kCall;

  /**
   * @param year
   * @return A list of team keys that competed in the given year, paginated in groups of 500
   *     <p>
   * @throws IOException
   */
  public String[] getTeamKeysInYear(int year) throws IOException {
    return teamKeysInYear.call(String.valueOf(year));
  }

  private final ApiCall<HashMap<String, TeamEventStatus>> teamEventStatusInYear =
      (ApiCall<HashMap<String, TeamEventStatus>>) ListCalls.TeamEventStatusInYear.kCall;
  /**
   * @param teamKey
   * @param year
   * @return A key-value list of the event statuses for events this team has competed at in the
   *     given year.
   * @throws IOException
   */
  public HashMap<String, TeamEventStatus> getTeamEventStatusInYear(String teamKey, int year)
      throws IOException {
    return teamEventStatusInYear.call(teamKey, String.valueOf(year));
  }

  private final ApiCall<Event[]> eventsInYear = (ApiCall<Event[]>) ListCalls.EventsInYear.kCall;
  /**
   * @param year
   * @return A list of events in the given year.
   * @throws IOException
   */
  public Event[] getEventsInYear(int year) throws IOException {
    return eventsInYear.call(String.valueOf(year));
  }

  private final ApiCall<EventSimple[]> eventsSimpleInYear =
      (ApiCall<EventSimple[]>) ListCalls.EventsSimpleInYear.kCall;
  /**
   * @param year
   * @return A short-form list of events in the given year.
   * @throws IOException
   */
  public EventSimple[] getEventsSimpleInYear(int year) throws IOException {
    return eventsSimpleInYear.call(String.valueOf(year));
  }

  private final ApiCall<String[]> eventKeysInYear =
      (ApiCall<String[]>) ListCalls.EventKeysInYear.kCall;
  /**
   * @param year
   * @return A list of event keys in the given year.
   * @throws IOException
   */
  public String[] getEventKeysInYear(int year) throws IOException {
    return eventKeysInYear.call(String.valueOf(year));
  }

  private final ApiCall<Team[]> teamsInEvent = (ApiCall<Team[]>) ListCalls.TeamsInEvent.kCall;
  /**
   * @param eventKey
   * @return A list of teams that competed in the given event.
   * @throws IOException
   */
  public Team[] getTeamsInEvent(String eventKey) throws IOException {
    return teamsInEvent.call(eventKey);
  }

  private final ApiCall<TeamSimple[]> teamsSimpleInEvent =
      (ApiCall<TeamSimple[]>) ListCalls.TeamsSimpleInEvent.kCall;
  /**
   * @param eventKey
   * @return a short-form list of Team objects that competed in the given event.
   * @throws IOException
   */
  public TeamSimple[] getTeamsSimpleInEvent(String eventKey) throws IOException {
    return teamsSimpleInEvent.call(eventKey);
  }

  private final ApiCall<String[]> teamKeysInEvent =
      (ApiCall<String[]>) ListCalls.TeamKeysInEvent.kCall;
  /**
   * @param eventKey
   * @return A list of team keys that competed in the given event.
   * @throws IOException
   */
  public String[] getTeamKeysInEvent(String eventKey) throws IOException {
    return teamKeysInEvent.call(eventKey);
  }

  private final ApiCall<HashMap<String, TeamEventStatus>> teamEventStatusesInEvent =
      (ApiCall<HashMap<String, TeamEventStatus>>) ListCalls.TeamEventStatusesInEvent.kCall;
  /**
   * @param eventKey
   * @return A key-value list of the event statuses for teams competing at the given event.
   * @throws IOException
   */
  public HashMap<String, TeamEventStatus> getTeamEventStatusesInEvent(String eventKey)
      throws IOException {
    return teamEventStatusesInEvent.call(eventKey);
  }

  private final ApiCall<Event[]> eventsInDistrict =
      (ApiCall<Event[]>) ListCalls.EventsInDistrict.kCall;
  /**
   * @param districtKey
   * @return A list of events in the given district.
   * @throws IOException
   */
  public Event[] getEventsInDistrict(String districtKey) throws IOException {
    return eventsInDistrict.call(districtKey);
  }

  private final ApiCall<EventSimple[]> eventsSimpleInDistrict =
      (ApiCall<EventSimple[]>) ListCalls.EventsSimpleInDistrict.kCall;
  /**
   * @param districtKey
   * @return A short-form list of events in the given district.
   * @throws IOException
   */
  public EventSimple[] getEventsSimpleInDistrict(String districtKey) throws IOException {
    return eventsSimpleInDistrict.call(districtKey);
  }

  private final ApiCall<String[]> eventKeysInDistrict =
      (ApiCall<String[]>) ListCalls.EventKeysInDistrict.kCall;
  /**
   * @param districtKey
   * @return A list of event keys in the given district.
   * @throws IOException
   */
  public String[] getEventKeysInDistrict(String districtKey) throws IOException {
    return eventKeysInDistrict.call(districtKey);
  }

  private final ApiCall<Team[]> teamsInDistrict = (ApiCall<Team[]>) ListCalls.TeamsInDistrict.kCall;
  /**
   * @param districtKey
   * @return A list of Team objects that competed in the given district.
   * @throws IOException
   */
  public Team[] getTeamsInDistrict(String districtKey) throws IOException {
    return teamsInDistrict.call(districtKey);
  }

  private final ApiCall<TeamSimple[]> teamsSimpleInDistrict =
      (ApiCall<TeamSimple[]>) ListCalls.TeamsSimpleInDistrict.kCall;
  /**
   * @param districtKey
   * @return A short-form list of Team objects that competed in the given district.
   * @throws IOException
   */
  public TeamSimple[] getTeamsSimpleInDistrict(String districtKey) throws IOException {
    return teamsSimpleInDistrict.call(districtKey);
  }

  private final ApiCall<String[]> teamKeysInDistrict =
      (ApiCall<String[]>) ListCalls.TeamKeysInDistrict.kCall;
  /**
   * @param districtKey
   * @return A list of Team objects that competed in events in the given district.
   * @throws IOException
   */
  public String[] getTeamKeysInDistrict(String districtKey) throws IOException {
    return teamKeysInDistrict.call(districtKey);
  }

  private final ApiCall<DistrictRanking[]> teamRankingsInDistrict =
      (ApiCall<DistrictRanking[]>) ListCalls.TeamRankingsInDistrict.kCall;
  /**
   * @param districtKey
   * @return A list of team district rankings for the given district.
   * @throws IOException
   */
  public DistrictRanking[] getTeamRankingsInDistrict(String districtKey) throws IOException {
    return teamRankingsInDistrict.call(districtKey);
  }

  // Team

  // private final ApiCall<Team[]> teams = (ApiCall<Team[]>) TeamApi.Teams.kCall;
  // private final ApiCall<TeamSimple[]> (ApiCall<TeamSimple[]) teamsSimple =
  // TeamApi.TeamsSimple.kCall;
  // private final ApiCall<String[]> teamKeys = (ApiCall<String[]>) TeamApi.TeamKeys.kCall;
  // private final ApiCall<Team[]> teamsInYear = (ApiCall<Team[]>) TeamApi.TeamsInYear.kCall;
  // private final ApiCall<TeamSimple[]> teamsSimpleInYear = (ApiCall<TeamSimple[]>)
  // TeamApi.TeamsSimpleInYear.kCall;
  // private final ApiCall<String[]> teamKeysInYear = (ApiCall<String[]>)
  // TeamApi.TeamKeysInYear.kCall;

  private final ApiCall<Team> team = (ApiCall<Team>) TeamCalls.Team.kCall;
  /**
   * @param teamKey
   * @return A Team object for the given team key.
   * @throws IOException
   */
  public Team getTeam(String teamKey) throws IOException {
    return team.call(teamKey);
  }

  private final ApiCall<TeamSimple> teamSimple = (ApiCall<TeamSimple>) TeamCalls.TeamSimple.kCall;
  /**
   * @param teamKey
   * @return A short-form Team object for the given team key.
   * @throws IOException
   */
  public TeamSimple getTeamSimple(String teamKey) throws IOException {
    return teamSimple.call(teamKey);
  }

  private final ApiCall<Integer[]> yearsParticipated =
      (ApiCall<Integer[]>) TeamCalls.YearsParticipated.kCall;
  /**
   * @param teamKey
   * @return A list of years in which the given team has competed.
   * @throws IOException
   */
  public Integer[] getYearsParticipated(String teamKey) throws IOException {
    return yearsParticipated.call(teamKey);
  }

  private final ApiCall<DistrictList[]> districts =
      (ApiCall<DistrictList[]>) TeamCalls.Districts.kCall;
  /**
   * @param teamKey
   * @return An array of Districts representing each year the team was in a district. Will return an
   *     empty array if the team was never in a district.
   * @throws IOException
   */
  public DistrictList[] getDistricts(String teamKey) throws IOException {
    return districts.call(teamKey);
  }

  private final ApiCall<Robot[]> robots = (ApiCall<Robot[]>) TeamCalls.Robots.kCall;
  /**
   * @param teamKey
   * @return A list of year and robot name pairs for each year that a robot name was provided. Will
   *     return an empty array if the team has never named a robot.
   */
  public Robot[] getRobots(String teamKey) throws IOException {
    return robots.call(teamKey);
  }

  private final ApiCall<Event[]> teamEvents = (ApiCall<Event[]>) TeamCalls.TeamEvents.kCall;
  /**
   * @param teamKey
   * @return A list of events that the team has competed in.
   */
  public Event[] getEvents(String teamKey) throws IOException {
    return teamEvents.call(teamKey);
  }

  private final ApiCall<EventSimple[]> teamEventsSimple =
      (ApiCall<EventSimple[]>) TeamCalls.TeamEventsSimple.kCall;
  /**
   * @param teamKey
   * @return A list of short-form events that the team has competed in.
   */
  public EventSimple[] getEventsSimple(String teamKey) throws IOException {
    return teamEventsSimple.call(teamKey);
  }

  private final ApiCall<String[]> teamEventKeys = (ApiCall<String[]>) TeamCalls.TeamEventKeys.kCall;
  /**
   * @param teamKey
   * @return A list of event keys that the team has competed in.
   */
  public String[] getEventKeys(String teamKey) throws IOException {
    return teamEventKeys.call(teamKey);
  }

  // private final ApiCall<Event[]> eventsInYear = (ApiCall<Event[]>) TeamApi.EventsInYear.kCall;
  // private final ApiCall<EventSimple[]> eventsSimpleInYear = (ApiCall<EventSimpe[]>)
  // TeamApi.EventsSimpleInYear.kCall;
  // private final ApiCall<String[]> eventKeysInYear = (ApiCall<String[]>) TeamApi.EventKeysInYear;

  private final ApiCall<TeamEventStatus[]> teamEventStatusesInYear =
      (ApiCall<TeamEventStatus[]>) TeamCalls.TeamEventStatusesInYear.kCall;
  /**
   * @param teamKey
   * @param year
   * @return A list of event statuses for the given team in the given year.
   */
  public TeamEventStatus[] getTeamEventStatusesInYear(String teamKey, int year) throws IOException {
    return teamEventStatusesInYear.call(teamKey, String.valueOf(year));
  }

  private final ApiCall<Match[]> matchesInEvent = (ApiCall<Match[]>) TeamCalls.MatchesInEvent.kCall;
  /**
   * @param teamKey
   * @param eventKey
   * @return A list of matches that the team will play at a given event.
   */
  public Match[] getMatchesInEvent(String teamKey, String eventKey) throws IOException {
    return matchesInEvent.call(teamKey, eventKey);
  }

  private final ApiCall<MatchSimple[]> matchesSimpleInEvent =
      (ApiCall<MatchSimple[]>) TeamCalls.MatchesSimpleInEvent.kCall;
  /**
   * @param teamKey
   * @param eventKey
   * @return A list of short-form matches that the team will play at a given event.
   */
  public MatchSimple[] getMatchesSimpleInEvent(String teamKey, String eventKey) throws IOException {
    return matchesSimpleInEvent.call(teamKey, eventKey);
  }

  private final ApiCall<String[]> matchKeysInEvent =
      (ApiCall<String[]>) TeamCalls.MatchKeysInEvent.kCall;
  /**
   * @param teamKey
   * @param eventKey
   * @return A list of match keys that the team will play at a given event.
   */
  public String[] getMatchKeysInEvent(String teamKey, String eventKey) throws IOException {
    return matchKeysInEvent.call(teamKey, eventKey);
  }

  private final ApiCall<Award[]> teamAwardsInEvent =
      (ApiCall<Award[]>) TeamCalls.TeamAwardsInEvent.kCall;
  /**
   * @param teamKey
   * @param eventKey
   * @return A list of awards that the team has won at a given event.
   */
  public Award[] getEventAwards(String teamKey, String eventKey) throws IOException {
    return teamAwardsInEvent.call(teamKey, eventKey);
  }

  private final ApiCall<TeamEventStatus> teamEventStatus =
      (ApiCall<TeamEventStatus>) TeamCalls.TeamEventStatus.kCall;
  /**
   * @param teamKey
   * @param eventKey
   * @return The event status for the given team at the given event.
   */
  public TeamEventStatus getTeamEventStatus(String teamKey, String eventKey) throws IOException {
    return teamEventStatus.call(teamKey, eventKey);
  }

  private final ApiCall<Award[]> awards = (ApiCall<Award[]>) TeamCalls.Awards.kCall;
  /**
   * @param teamKey
   * @return A list of awards that the team has won.
   */
  public Award[] getAwards(String teamKey) throws IOException {
    return awards.call(teamKey);
  }

  private final ApiCall<Award[]> awardsInYear = (ApiCall<Award[]>) TeamCalls.AwardsInYear.kCall;
  /**
   * @param teamKey
   * @param year
   * @return A list of awards that the team has won in the given year.
   */
  public Award[] getAwardsInYear(String teamKey, int year) throws IOException {
    return awardsInYear.call(teamKey, String.valueOf(year));
  }

  private final ApiCall<Match[]> matchesInYear = (ApiCall<Match[]>) TeamCalls.MatchesInYear.kCall;
  /**
   * @param teamKey
   * @param year
   * @return A list of matches that the team has played in the given year.
   */
  public Match[] getMatchesInYear(String teamKey, int year) throws IOException {
    return matchesInYear.call(teamKey, String.valueOf(year));
  }

  private final ApiCall<MatchSimple[]> matchesSimpleInYear =
      (ApiCall<MatchSimple[]>) TeamCalls.MatchSimpleInYear.kCall;
  /**
   * @param teamKey
   * @param year
   * @return A list of short-form matches that the team has played in the given year.
   */
  public MatchSimple[] getMatchesSimpleInYear(String teamKey, int year) throws IOException {
    return matchesSimpleInYear.call(teamKey, String.valueOf(year));
  }

  private final ApiCall<String[]> matchKeysInYear =
      (ApiCall<String[]>) TeamCalls.MatchKeysInYear.kCall;
  /**
   * @param teamKey
   * @param year
   * @return A list of match keys that the team has played in the given year.
   */
  public String[] getMatchKeysInYear(String teamKey, int year) throws IOException {
    return matchKeysInYear.call(teamKey, String.valueOf(year));
  }

  private final ApiCall<Media[]> mediaInYear = (ApiCall<Media[]>) TeamCalls.MediaInYear.kCall;
  /**
   * @param teamKey
   * @param year
   * @return A list of media that the team has uploaded in the given year.
   */
  public Media[] getMediaInYear(String teamKey, int year) throws IOException {
    return mediaInYear.call(teamKey, String.valueOf(year));
  }

  private final ApiCall<Media[]> mediaByTag = (ApiCall<Media[]>) TeamCalls.MediaByTag.kCall;
  /**
   * @param teamKey
   * @param mediaTag
   * @return A list of Media (videos / pictures) for the given team and tag.
   * @throws IOException
   */
  public Media[] getMediaByTag(String teamKey, String mediaTag) throws IOException {
    return mediaByTag.call(teamKey, mediaTag);
  }

  private final ApiCall<Media[]> mediaByTagInYear =
      (ApiCall<Media[]>) TeamCalls.MediaByTagInYear.kCall;
  /**
   * @param teamKey
   * @param mediaTag
   * @param year
   * @return A list of Media (videos / pictures) for the given team and tag in the given year.
   * @throws IOException
   */
  public Media[] getMediaByTagInYear(String teamKey, String mediaTag, int year) throws IOException {
    return mediaByTagInYear.call(teamKey, mediaTag, String.valueOf(year));
  }

  private final ApiCall<Media[]> socialMedia = (ApiCall<Media[]>) TeamCalls.SocialMedia.kCall;
  /**
   * @param teamKey
   * @return A list of social media that the team has.
   * @throws IOException
   */
  public Media[] getSocialMedia(String teamKey) throws IOException {
    return socialMedia.call(teamKey);
  }

  // private final ApiCall<Team[]> teamsInEvent = (ApiCall<Team[]>) TeamApi.TeamsInEvent.kCall;
  // private final ApiCall<TeamSimple[]> teamsSimpleInEvent = (ApiCall<TeamSimple[]>)
  // TeamApi.TeamsSimpleInEvent.kCall;
  // private final ApiCall<String[]> teamKeysInEvent = (ApiCall<String[]>)
  // TeamApi.TeamKeysInEvent.kCall;

  private final ApiCall<HashMap<String, TeamEventStatus>> teamEventStatuses =
      (ApiCall<HashMap<String, TeamEventStatus>>) TeamCalls.TeamEventStatuses.kCall;
  /**
   * @param teamKey
   * @return A map of event key to event status for the given team.
   */
  public HashMap<String, TeamEventStatus> getTeamEventStatuses(String teamKey) throws IOException {
    return teamEventStatuses.call(teamKey);
  }

  // private final ApiCall<Team[]> teamsInDistrict = (ApiCall<Team[]>)
  // TeamApi.TeamsInDistrict.kCall;
  // private final ApiCall<TeamSimple[]> teamsSimpleInDistrict = (ApiCall<TeamSimple[]>)
  // TeamApi.TeamsSimpleInDistrict.kCall;
  // private final ApiCall<String[]> teamKeysInDistrict = (ApiCall<String[]>)
  // TeamApi.TeamKeysInDistrict.kCall;

  private final ApiCall<DistrictRanking[]> districtRankings =
      (ApiCall<DistrictRanking[]>) TeamCalls.DistrictRankings.kCall;
  /**
   * @param districtKey
   * @return A list of district rankings for the given district.
   * @throws IOException
   */
  public DistrictRanking[] getDistrictRankings(String districtKey) throws IOException {
    return districtRankings.call(districtKey);
  }

  // Event

  // private final ApiCall<Event[]> teamEvents = (ApiCall<Event[]>) EventCalls.TeamEvents.kCall;
  // private final ApiCall<Event[]> teamEventsSimple = (ApiCall<Event[]>)
  // EventCalls.TeamEventsSimple.kCall;
  // private final ApiCall<String[]> teamEventKeys = (ApiCall<String[]>)
  // EventCalls.TeamEventKeys.kCall;

  private final ApiCall<Event[]> teamEventsInYear =
      (ApiCall<Event[]>) EventCalls.TeamEventsInYear.kCall;
  /**
   * @param teamKey
   * @param year
   * @return A list of events that the team has played in the given year.
   */
  public Event[] getTeamEventsInYear(String teamKey, int year) throws IOException {
    return teamEventsInYear.call(teamKey, String.valueOf(year));
  }

  private final ApiCall<Event[]> teamEventsSimpleInYear =
      (ApiCall<Event[]>) EventCalls.TeamEventsSimpleInYear.kCall;
  /**
   * @param teamKey
   * @param year
   * @return A list of short-form events that the team has played in the given year.
   */
  public Event[] getTeamEventsSimpleInYear(String teamKey, int year) throws IOException {
    return teamEventsSimpleInYear.call(teamKey, String.valueOf(year));
  }

  private final ApiCall<String[]> teamEventKeysInYear =
      (ApiCall<String[]>) EventCalls.TeamEventKeysInYear.kCall;
  /**
   * @param teamKey
   * @param year
   * @return A list of event keys that the team has played in the given year.
   */
  public String[] getTeamEventKeysInYear(String teamKey, int year) throws IOException {
    return teamEventKeysInYear.call(teamKey, String.valueOf(year));
  }

  // private final ApiCall<HashMap<String, TeamEventStatus>> teamEventStatusesInYear =
  // (ApiCall<HashMap<String, TeamEventStatus>>) EventCalls.TeamEventStatusesInYear.kCall;

  private final ApiCall<Match[]> teamMatchesInEvent =
      (ApiCall<Match[]>) EventCalls.TeamMatchesInEvent.kCall;
  /**
   * @param teamKey
   * @param eventKey
   * @return A list of matches that the team has played in the given event.
   */
  public Match[] getTeamMatchesInEvent(String teamKey, String eventKey) throws IOException {
    return teamMatchesInEvent.call(teamKey, eventKey);
  }

  private final ApiCall<MatchSimple[]> teamMatchesSimpleInEvent =
      (ApiCall<MatchSimple[]>) EventCalls.TeamMatchesSimpleInEvent.kCall;
  /**
   * @param teamKey
   * @param eventKey
   * @return A list of short-form matches that the team has played in the given event.
   */
  public MatchSimple[] getTeamMatchesSimpleInEvent(String teamKey, String eventKey)
      throws IOException {
    return teamMatchesSimpleInEvent.call(teamKey, eventKey);
  }

  private final ApiCall<String[]> teamMatchKeysInEvent =
      (ApiCall<String[]>) EventCalls.TeamMatchKeysInEvent.kCall;
  /**
   * @param teamKey
   * @param eventKey
   * @return A list of match keys that the team has played in the given event.
   */
  public String[] getTeamMatchKeysInEvent(String teamKey, String eventKey) throws IOException {
    return teamMatchKeysInEvent.call(teamKey, eventKey);
  }

  // private final ApiCall<Award[]> teamAwardsInEvent = (ApiCall<Award[]>)
  // EventCalls.TeamAwardsInEvent.kCall;
  // private final ApiCall<TeamEventStatus> teamEventStatus = (ApiCall<TeamEventStatus>)
  // EventCalls.TeamEventStatus.kCall;
  // private final ApiCall<Event[]> eventsInYear = (ApiCall<Event[]>) EventCalls.EventsInYear.kCall;
  // private final ApiCall<Event[]> eventsSimpleInYear = (ApiCall<Event[]>)
  // EventCalls.EventsSimpleInYear.kCall;
  // private final ApiCall<String[]> eventKeysInYear = (ApiCall<String[]>)
  // EventCalls.EventKeysInYear.kCall;

  private final ApiCall<Event> event = (ApiCall<Event>) EventCalls.Event.kCall;
  /**
   * @param eventKey
   * @return The event with the given key.
   */
  public Event getEvent(String eventKey) throws IOException {
    return event.call(eventKey);
  }

  private final ApiCall<EventSimple> eventSimple =
      (ApiCall<EventSimple>) EventCalls.EventSimple.kCall;
  /**
   * @param eventKey
   * @return The short-form event with the given key.
   */
  public EventSimple getEventSimple(String eventKey) throws IOException {
    return eventSimple.call(eventKey);
  }

  private final ApiCall<EliminationAlliance[]> eventAlliances =
      (ApiCall<EliminationAlliance[]>) EventCalls.EventAlliances.kCall;
  /**
   * @param eventKey
   * @return A list of alliances for the given event.
   */
  public EliminationAlliance[] getEventAlliances(String eventKey) throws IOException {
    return eventAlliances.call(eventKey);
  }

  private final ApiCall<EventOPRs> eventOPRs = (ApiCall<EventOPRs>) EventCalls.EventOPRs.kCall;
  /**
   * @param eventKey
   * @return A list of OPRs for the given event.
   */
  public EventOPRs getEventOPRs(String eventKey) throws IOException {
    return eventOPRs.call(eventKey);
  }

  private final ApiCall<EventRanking> eventRankings =
      (ApiCall<EventRanking>) EventCalls.EventRankings.kCall;
  /**
   * @param eventKey
   * @return A list of rankings for the given event.
   */
  public EventRanking getEventRankings(String eventKey) throws IOException {
    return eventRankings.call(eventKey);
  }

  private final ApiCall<EventDistrictPoints> eventDistrictPoints =
      (ApiCall<EventDistrictPoints>) EventCalls.EventDistrictPoints.kCall;
  /**
   * @param eventKey
   * @return A list of district points for the given event.
   */
  public EventDistrictPoints getEventDistrictPoints(String eventKey) throws IOException {
    return eventDistrictPoints.call(eventKey);
  }

  // private final ApiCall<Team[]> teamsInEvent = (ApiCall<Team[]>) EventCalls.TeamsInEvent.kCall;
  // private final ApiCall<TeamSimple[]> teamsSimpleInEvent = (ApiCall<TeamSimple[]>)
  // EventCalls.TeamsSimpleInEvent.kCall;
  // private final ApiCall<String[]> teamKeysInEvent = (ApiCall<String[]>)
  // EventCalls.TeamKeysInEvent.kCall;
  // private final ApiCall<Match[]> matchesInEvent = (ApiCall<Match[]>)
  // EventCalls.MatchesInEvent.kCall;
  // private final ApiCall<MatchSimple[]> matchesSimpleInEvent = (ApiCall<MatchSimple[]>)
  // EventCalls.MatchesSimpleInEvent.kCall;
  // private final ApiCall<String[]> matchKeysInEvent = (ApiCall<String[]>)
  // EventCalls.MatchKeysInEvent.kCall;

  private final ApiCall<Award[]> eventAwards = (ApiCall<Award[]>) EventCalls.EventAwards.kCall;
  /**
   * @param eventKey
   * @return A list of awards for the given event.
   */
  public Award[] getEventAwards(String eventKey) throws IOException {
    return eventAwards.call(eventKey);
  }

  // private final ApiCall<Event[]> eventsInDistrict = (ApiCall<Event[]>)
  // EventCalls.EventsInDistrict.kCall;
  // private final ApiCall<Event[]> eventsSimpleInDistrict = (ApiCall<Event[]>)
  // EventCalls.EventsSimpleInDistrict.kCall;
  // private final ApiCall<String[]> eventKeysInDistrict = (ApiCall<String[]>)
  // EventCalls.EventKeysInDistrict.kCall;

  // Match

  // private final ApiCall<Match[]> teamMatchesInEvent = (ApiCall<Match[]>)
  // MatchCalls.TeamMatchesInEvent.kCall;
  // private final ApiCall<MatchSimple[]> teamMatchesSimpleInEvent = (ApiCall<MatchSimple[]>)
  // MatchCalls.TeamMatchesSimpleInEvent.kCall;
  // private final ApiCall<String[]> teamMatchKeysInEvent = (ApiCall<String[]>)
  // MatchCalls.TeamMatchKeysInEvent.kCall;
  // private final ApiCall<Match[]> matchesInYear = (ApiCall<Match[]>)
  // MatchCalls.MatchesInYear.kCall;
  // private final ApiCall<MatchSimple[]> matchesSimpleInYear = (ApiCall<MatchSimple[]>)
  // MatchCalls.MatchesSimpleInYear.kCall;
  // private final ApiCall<String[]> matchKeysInYear = (ApiCall<String[]>)
  // MatchCalls.MatchKeysInYear.kCall;
  // private final ApiCall<Match[]> matchesInEvent = (ApiCall<Match[]>)
  // MatchCalls.MatchesInEvent.kCall;
  // private final ApiCall<MatchSimple[]> matchesSimpleInEvent = (ApiCall<MatchSimple[]>)
  // MatchCalls.MatchesSimpleInEvent.kCall;
  // private final ApiCall<String[]> matchKeysInEvent = (ApiCall<String[]>)
  // MatchCalls.MatchKeysInEvent.kCall;
  private final ApiCall<Match> match = (ApiCall<Match>) MatchCalls.Match.kCall;
  /**
   * @param matchKey
   * @return The match with the given key.
   */
  public Match getMatch(String matchKey) throws IOException {
    return match.call(matchKey);
  }

  private final ApiCall<MatchSimple> matchSimple =
      (ApiCall<MatchSimple>) MatchCalls.MatchSimple.kCall;
  /**
   * @param matchKey
   * @return The short-form match with the given key.
   */
  public MatchSimple getMatchSimple(String matchKey) throws IOException {
    return matchSimple.call(matchKey);
  }

  private final ApiCall<Zebra> zebraMotionWorksInMatch =
      (ApiCall<Zebra>) MatchCalls.ZebraMotionWorksInMatch.kCall;
  /**
   * @param matchKey
   * @return The zebra motion works for the given match.
   */
  public Zebra getZebraMotionWorksInMatch(String matchKey) throws IOException {
    return zebraMotionWorksInMatch.call(matchKey);
  }

  // District

  private final ApiCall<DistrictList[]> teamDistricts =
      (ApiCall<DistrictList[]>) DistrictCalls.TeamDistricts.kCall;
  /**
   * @param teamKey
   * @return The districts in which the given team is a member.
   */
  public DistrictList[] getTeamDistricts(String teamKey) throws IOException {
    return teamDistricts.call(teamKey);
  }

  // private final ApiCall<EventDistrictPoints> eventDistrictPoints = (ApiCall<EventDistrictPoints>)
  // DistrictCalls.EventDistrictPoints.kCall;

  private final ApiCall<DistrictList[]> districtsInYear =
      (ApiCall<DistrictList[]>) DistrictCalls.DistrictsInYear.kCall;
  /**
   * @param year
   * @return The districts in the given year.
   */
  public DistrictList[] getDistrictsInYear(int year) throws IOException {
    return districtsInYear.call(String.valueOf(year));
  }

  // private final ApiCall<Event[]> eventsInDistrict = (ApiCall<Event[]>)
  // DistrictCalls.EventsInDistrict.kCall;
  // private final ApiCall<Event[]> eventsSimpleInDistrict = (ApiCall<Event[]>)
  // DistrictCalls.EventsSimpleInDistrict.kCall;
  // private final ApiCall<String[]> eventKeysInDistrict = (ApiCall<String[]>)
  // DistrictCalls.EventKeysInDistrict.kCall;
  // private final ApiCall<Team[]> teamsInDistrict = (ApiCall<Team[]>)
  // DistrictCalls.TeamsInDistrict.kCall;
  // private final ApiCall<TeamSimple[]> teamsSimpleInDistrict = (ApiCall<TeamSimple[]>)
  // DistrictCalls.TeamsSimpleInDistrict.kCall;
  // private final ApiCall<String[]> teamKeysInDistrict = (ApiCall<String[]>)
  // DistrictCalls.TeamKeysInDistrict.kCall;

  private final ApiCall<EventDistrictPoints> eventDistrictRanking =
      (ApiCall<EventDistrictPoints>) DistrictCalls.DistrictRankings.kCall;
  /**
   * @param districtKey
   * @return The rankings for the given district.
   */
  public EventDistrictPoints getEventDistrictRanking(String districtKey) throws IOException {
    return eventDistrictRanking.call(districtKey);
  }
}
