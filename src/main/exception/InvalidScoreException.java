package main.exception;

public class InvalidScoreException extends ScoreBoardException {

    public InvalidScoreException(String message) {
        super(message);
    }
}