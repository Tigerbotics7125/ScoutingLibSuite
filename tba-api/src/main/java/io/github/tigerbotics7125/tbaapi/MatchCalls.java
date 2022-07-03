package io.github.tigerbotics7125.tbaapi;

import com.google.gson.reflect.TypeToken;
import io.github.tigerbotics7125.tbaapi.schema.match.Match;
import io.github.tigerbotics7125.tbaapi.schema.match.MatchSimple;
import io.github.tigerbotics7125.tbaapi.schema.zebra.Zebra;

public enum MatchCalls {
  TeamMatchesInEvent("team/{team_key}/event/{event_key}/matches", TypeToken.get(Match[].class)),
  TeamMatchesSimpleInEvent(
      "team/{team_key}/event/{event_key}/simple", TypeToken.get(MatchSimple[].class)),
  TeamMatchKeysInEvent("team/{team_key}/event/{event_key}/keys", TypeToken.get(String[].class)),

  TeamMatchesInYear("team/{team_key}/matches/{year}", TypeToken.get(Match[].class)),
  TeamMatchesSimpleInYear("team/{team_key}/simple/{year}", TypeToken.get(MatchSimple[].class)),
  TeamMatchKeysInYear("team/{team_key}/keys/{year}", TypeToken.get(String[].class)),

  MatchesInEvent("event/{event_key}/matches", TypeToken.get(Match[].class)),
  MatchesSimpleInEvent("event/{event_key}/simple", TypeToken.get(MatchSimple[].class)),
  MatchKeysInEvent("event/{event_key}/keys", TypeToken.get(String[].class)),

  // no timeseries call, no worky

  Match("match/{match_key}", TypeToken.get(Match.class)),
  MatchSimple("match/{match_key}/simple", TypeToken.get(MatchSimple.class)),

  // no timeseries again

  ZebraMotionWorksInMatch("match/{match_key}/zebra_motionworks", TypeToken.get(Zebra.class));

  public final ApiCall<?> apiCall;

  MatchCalls(String endpoint, TypeToken<?> typeToken) {
    apiCall = new ApiCall<>(endpoint, typeToken);
  }
}
