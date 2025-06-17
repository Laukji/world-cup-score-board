package main.model;

public class Team {

    private final String name;
    private Integer score;

    public Team(final String name) {
        this.name = name;
        this.score = 0;
    }

    public Team setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}