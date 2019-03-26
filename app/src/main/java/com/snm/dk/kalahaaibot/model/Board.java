package com.snm.dk.kalahaaibot.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Integer> amboScores;
    private List<Integer> pitScores;

    public Board(List<Integer> amboScores, List<Integer> pitScores) {
        this.amboScores = amboScores;
        this.pitScores = pitScores;
    }

    public Board() {
        this.amboScores = new ArrayList<>();
        this.pitScores = new ArrayList<>();
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

    // Setter
    public void setAmboScores(List<Integer> amboScores) {
        this.amboScores = amboScores;
    }
    public void setPitScores(List<Integer> pitScores) {
        this.pitScores = pitScores;
    }

    @Override
    public String toString() {
        return "\n Board AMBO: " + this.amboScores + "\n" +
                "Player Pit: " + this.pitScores;
    }

}
