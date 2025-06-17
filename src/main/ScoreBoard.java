package main;

import main.model.Match;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {
    //TODO: Docs explaining what each method does, and type of exception if applicable

    // Consider data structure
    private final List<Match> matches;

    public ScoreBoard() {
        this.matches = new ArrayList<>();
    }

    public void startMatch(final String homeTeam, final String awayTeam) {
        // TODO: implement. Remember matches are initialized with 0-0 score, also input validation. Does match already exist?
    }

    public void updateScore(final int matchId, final int homeTeamScore, final int awayTeamScore) {
        // TODO: implement. Remember input validation. Can't receive negative scores. Goals can be disallowed, so shouldn't
        // have a need to check if values are incremented. Method more logically related to a Match, but if I decide to keep here then we need an identifier for a Match
    }

    public void finishMatch(final String homeTeam, final String awayTeam) {
        // TODO: implement. If library user can do operations like scoreBoard.getMatch(xyz).finishMatch
        // hmm skal denne være i match kanskje
    }

    public List<Match> getSummary() {
        // TODO: implement. Ordered by total score, and then by recency.
        return null;
    }

    public Match getMatchByTeamNames(final String homeTeam, final String awayTeam) {
        return matches.stream()
                .filter(match -> match.getHomeTeam().getName().equals(homeTeam) &&
                        match.getAwayTeam().getName().equals(awayTeam))
                .findFirst()
                .orElse(null);
    }
}