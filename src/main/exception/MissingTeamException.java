package main.exception;

public class MissingTeamException extends ScoreBoardException{

    public MissingTeamException(String message) {
        super(message);
    }
}