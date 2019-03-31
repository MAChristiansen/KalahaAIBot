package com.snm.dk.kalahaaibot.model;

import java.util.List;

public class Board {

    private List<Integer> amboScores;
    private List<Integer> pitScores;

    public Board(List<Integer> amboScores, List<Integer> pitScores) {
        this.amboScores = amboScores;
        this.pitScores = pitScores;
    }

    public Board(Board board) {
        this.amboScores = board.getAmboScores();
        this.pitScores = board.getPitScores();
    }

    // Getter
    public List<Integer> getAmboScores() {
        return amboScores;
    }
    public List<Integer> getPitScores() {
        return pitScores;
    }

    @Override
    public String toString() {
        return "\n Board AMBO: " + this.amboScores + "\n" +
                "Player Pit: " + this.pitScores;
    }

}
