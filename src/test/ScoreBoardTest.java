package test;

import main.ScoreBoard;
import main.exception.InvalidScoreException;
import main.exception.MissingTeamException;
import main.model.Match;
import main.model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class ScoreBoardTest {

    private static final String URUGUAY = "Uruguay";
    private static final String MEXICO = "Mexico";
    private static final String ARGENTINA = "Argentina";
    private static final String BRAZIL = "Brazil";
    private static final String CHILE = "Chile";
    private static final String COLOMBIA = "Colombia";
    private static final String PERU = "Peru";
    private static final String VENEZUELA = "Venezuela";

    private ScoreBoard scoreBoard;

    @BeforeEach
    void initScoreBoard() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    void createMatch() {
        scoreBoard.startMatch(URUGUAY, MEXICO);

        Assertions.assertEquals(URUGUAY, scoreBoard.getSummary().getFirst().getHomeTeam().getName());
        Assertions.assertEquals(URUGUAY, scoreBoard.getSummary().getFirst().getHomeTeam().getName());
    }

    @Test
    void updateScore() {
        scoreBoard.startMatch(URUGUAY, MEXICO);
        Match copaAmericaFinal = scoreBoard.getMatchByTeamNames(URUGUAY, MEXICO);

        copaAmericaFinal.updateScore(2, 3);

        //Want to test that the scoreboard is updated
        Team updatedHomeTeam = scoreBoard.getSummary().getFirst().getHomeTeam();
        Team updatedAwayTeam = scoreBoard.getSummary().getFirst().getAwayTeam();

        Assertions.assertEquals(2, updatedHomeTeam.getScore());
        Assertions.assertEquals(3, updatedAwayTeam.getScore());
    }

    @Test
    void finishMatch() {
        scoreBoard.startMatch(URUGUAY, MEXICO);
        scoreBoard.finishMatch(URUGUAY, MEXICO);

        Assertions.assertTrue(scoreBoard.getSummary().isEmpty());
    }

    @Test
    void getSummary() {//Maybe some parameterized test
        updateScoreBoard(scoreBoard, URUGUAY, MEXICO, 0, 0);
        updateScoreBoard(scoreBoard, PERU, CHILE, 3, 3);
        updateScoreBoard(scoreBoard, BRAZIL, ARGENTINA, 1, 2);
        updateScoreBoard(scoreBoard, COLOMBIA, VENEZUELA, 2, 1);

        List<Match> actualSummary = scoreBoard.getSummary();

        Assertions.assertEquals(PERU, actualSummary.getFirst().getHomeTeam().getName());
        Assertions.assertEquals(BRAZIL, actualSummary.get(1).getHomeTeam().getName());
        Assertions.assertEquals(COLOMBIA, actualSummary.get(2).getHomeTeam().getName());
        Assertions.assertEquals(PERU, actualSummary.getLast().getHomeTeam().getName());
    }

    @Test
    void getSummaryWhenNull() {
        List<Match> actualSummary = scoreBoard.getSummary();

        Assertions.assertTrue(actualSummary.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideInputForStartMatch")
    void startMatchThrowsException(String ignoredTitle,
                                   String homeTeamName,
                                   String awayTeamName) {

        Assertions.assertThrows(MissingTeamException.class, ()-> scoreBoard.startMatch(homeTeamName, awayTeamName));
    }

    private static Stream<Arguments> provideInputForStartMatch() {
        return Stream.of(
                Arguments.of("Both teams are missing",
                        null, null),
                Arguments.of("Home team is missing",
                        null, URUGUAY),
                Arguments.of("Away team is missing",
                        URUGUAY, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInputForUpdateScore")
    void updateScoreThrowsException(String ignoredTitle,
                                    Integer homeTeamScore,
                                    Integer awayTeamScore) {
        scoreBoard.startMatch(URUGUAY, BRAZIL);
        Match match = scoreBoard.getMatchByTeamNames(URUGUAY, BRAZIL);

        Assertions.assertThrows(InvalidScoreException.class,
                () -> match.updateScore(homeTeamScore, awayTeamScore));
    }

    private static Stream<Arguments> provideInputForUpdateScore() {
        return Stream.of(
                Arguments.of("Home team score is missing",
                        null, 0),
                Arguments.of("Away team score is missing",
                        2, null),
                Arguments.of("Score is negative",
                        -2, 3)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInputForFinishMatch")
    void finishMatchThrowsException(String ignoredTitle,
                                    String homeTeamName,
                                    String awayTeamName) {
        scoreBoard.startMatch(URUGUAY, BRAZIL);

        Assertions.assertThrows(MissingTeamException.class,
                () -> scoreBoard.finishMatch(homeTeamName, awayTeamName));
    }

    private static Stream<Arguments> provideInputForFinishMatch() {
        return Stream.of(
                Arguments.of("Home team is missing",
                        null, BRAZIL),
                Arguments.of("Away team is missing",
                        URUGUAY, null),
                Arguments.of("No teams are present",
                        null, null),
                Arguments.of("Match does not exist",
                        URUGUAY, PERU)
        );
    }

    private void updateScoreBoard(ScoreBoard scoreBoard, String homeTeam, String awayTeam, Integer homeTeamScore, Integer awayTeamScore) {
        scoreBoard.startMatch(homeTeam, awayTeam);
        Match match = scoreBoard.getMatchByTeamNames(homeTeam, awayTeam);
        match.updateScore(homeTeamScore, awayTeamScore);
    }
}