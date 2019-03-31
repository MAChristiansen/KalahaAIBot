package com.snm.dk.kalahaaibot.model;

public class State {

    private Board board;
    private Integer utility;
    private Integer heuristic = null;
    private boolean player;

    public State(Board board, boolean player) {
        this.board = board;
        this.player = player;
        this.utility = board.getPitScores().get(0) - board.getPitScores().get(1);
    }

    // Getter
    public Board getBoard() {
        return board;
    }
    public Integer getHeuristic() {
        return heuristic;
    }
    public Integer getUtility() {
        return utility;
    }
    public boolean isPlayer() {
        return player;
    }

    // Setter
    public void setBoard(Board board) {
        this.board = board;
    }
    public void setHeuristic(Integer heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public String toString() {
        return this.board.toString();
    }

}
