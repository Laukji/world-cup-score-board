package main;

import main.exception.MatchAlreadyExistsException;
import main.exception.MissingTeamException;
import main.model.Match;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Provides methods for displaying ongoing matches and their respective scores, maintained in a Scoreboard
 */
public class ScoreBoard {

    private final List<Match> matches;

    /**
     * Constructor method for initializing a scoreboard with no ongoing matches
     */
    public ScoreBoard() {
        this.matches = new ArrayList<>();
    }

    /**
     * Adds a {@link Match} to the scoreboard with score set to 0-0
     * @param homeTeam the name of the homeTeam in String format
     * @param awayTeam the name of the awayTeam in String format
     * @throws MissingTeamException if one or both of the team names are not provided
     * @throws MatchAlreadyExistsException if one or both teams are already in a match
     */
    public void startMatch(final String homeTeam, final String awayTeam) {
        if (isValidMatch(homeTeam, awayTeam)) {
            matches.add(new Match(homeTeam, awayTeam));
        }
    }

    /**
     * Removes a {@link Match} from the scoreboard
     * @param homeTeam the name of the homeTeam in String format
     * @param awayTeam the name of the awayTeam in String format
     * @throws MissingTeamException if a match between the two teams does not exist
     */
    public void finishMatch(final String homeTeam, final String awayTeam) {
        Optional<Match> matchToFinish = getMatchByTeamNames(homeTeam, awayTeam);

        if (matchToFinish.isEmpty()) {
            throw new MissingTeamException(String.format("Unable to find an existing match between homeTeam=%s and awayTeam=%s", homeTeam, awayTeam));
        }
        matchToFinish.ifPresent(matches::remove);
    }

    /**
     * Provides a summary of ongoing matches ordered by their total score.
     * If two matches have the same total scores, the most recently started match will be ordered first
     * @return the ordered {@link Match}
     */
    public List<Match> getSummary() {
        return matches.stream()
                .sorted(Comparator.comparing(Match::getTotalScore).reversed()
                        .thenComparing(Match::getStartTime, Comparator.reverseOrder()))
                .toList();
    }

    /**
     * Provides an ongoing match between two teams, if it exists
     * @param homeTeam the name of the homeTeam in String format
     * @param awayTeam the name of the awayTeam in String format
     * @return an Optional {@link Match} between the two teams
     */
    public Optional<Match> getMatchByTeamNames(final String homeTeam, final String awayTeam) {
        return matches.stream()
                .filter(match -> match.getHomeTeam().getName().equals(homeTeam) &&
                        match.getAwayTeam().getName().equals(awayTeam))
                .findFirst();
    }

    private boolean isValidMatch(final String homeTeam, final String awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new MissingTeamException(String.format("One or both teams are missing, provided teams are homeTeam=%s, awayTeam=%s", homeTeam, awayTeam));
        } else if (areTeamsInExistingMatch(homeTeam, awayTeam)) {
            throw new MatchAlreadyExistsException("One or both teams are already in a match");
        }
        return true;
    }

    private boolean areTeamsInExistingMatch(final String homeTeam, final String awayTeam) {
        return isTeamInExistingMatch(homeTeam) || isTeamInExistingMatch(awayTeam);
    }

    private boolean isTeamInExistingMatch(final String teamName) {
        return matches.stream()
                .anyMatch(match -> {
                    String homeTeamName = match.getHomeTeam().getName();
                    String awayTeamName = match.getAwayTeam().getName();
                    return homeTeamName.equals(teamName) || awayTeamName.equals(teamName);
                });
    }
}