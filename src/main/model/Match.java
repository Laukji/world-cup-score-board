package main.model;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Match {
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    //Maybe score logically belongs to a Match rather than a Team, will see if implementation changes
    private final Integer matchId;
    private final LocalDate startTime;
    private final Team homeTeam;
    private final Team awayTeam;

    public Match(final String homeTeamName, final String awayTeamName) {
        this.matchId = idCounter.incrementAndGet();
        this.startTime = LocalDate.now();
        this.homeTeam = new Team(homeTeamName);
        this.awayTeam = new Team(awayTeamName);
    }

    public void updateScore(final int homeTeamScore, final int awayTeamScore) {
        //TODO: validation
        homeTeam.setScore(homeTeamScore);
        awayTeam.setScore(awayTeamScore);
    }

    public Integer getMatchId() {
        return matchId;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }
}