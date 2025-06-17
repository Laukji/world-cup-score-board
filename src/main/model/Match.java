package main.model;

import main.exception.InvalidScoreException;

import java.time.LocalDateTime;

/**
 * Provides a method for updating the score of an existing match
 */
public class Match {
    private final LocalDateTime startTime;
    private final Team homeTeam;
    private final Team awayTeam;

    public Match(String homeTeamName, String awayTeamName) {
        this.startTime = LocalDateTime.now();
        this.homeTeam = new Team(homeTeamName);
        this.awayTeam = new Team(awayTeamName);
    }

    /**
     * Updates the score of the teams in an ongoing match
     * @param homeTeamScore the previous or updated score of the homeTeam as Integer
     * @param awayTeamScore the previous or updated score of the awayTeam as Integer
     * @throws InvalidScoreException if no score or a negative score is provided
     */
    public void updateScore(Integer homeTeamScore, Integer awayTeamScore) {
        if (isValidScore(homeTeamScore, awayTeamScore)) {
            homeTeam.setScore(homeTeamScore);
            awayTeam.setScore(awayTeamScore);
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Integer getTotalScore() {
        return homeTeam.getScore() + awayTeam.getScore();
    }

    private boolean isValidScore(Integer homeTeamScore, Integer awayTeamScore) {
        if (homeTeamScore == null || awayTeamScore == null) {
            throw new InvalidScoreException("Score for one or both teams are missing");
        } else if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new InvalidScoreException("Score for one or both teams are negative");
        }
        return true;
    }
}