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

    public List<Integer> getAmboScores() {
        return amboScores;
    }
    public void setAmboScores(List<Integer> amboScores) {
        this.amboScores = amboScores;
    }
    public List<Integer> getPitScores() {
        return pitScores;
    }
    public void setPitScores(List<Integer> pitScores) {
        this.pitScores = pitScores;
    }

    /*public State getCurrentGameState(List<Integer> amboScores, List<Integer> pitScores) {

        ArrayList<Integer> gameState = new ArrayList<>();
        gameState.add(pitScores.get(0));

        for (int i = 0; i < amboScores.size(); i++) {
            gameState.add(amboScores.get(i));
        }

        gameState.add(pitScores.get(1));

        // TODO: find a solution to player turn
        return new State(gameState, false);
    }*/

    @Override
    public String toString() {
        return "\n Board AMBO: " + this.amboScores + "\n" +
                "Player Pit: " + this.pitScores;
    }

}
