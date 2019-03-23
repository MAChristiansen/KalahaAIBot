package com.snm.dk.kalahaaibot.model;

public class State {

    private Board board;
    private Integer utility;
    private boolean player;

    public State(Board board, Integer utility, boolean player) {
        this.board = board;
        this.utility = utility;
        this.player = player;
    }

    public State(Board board, boolean player) {
        this.board = board;
        this.player = player;
        this.utility = board.getPitScores().get(0) - board.getPitScores().get(1);
    }

    public State(Board board) {
        this.board = board;
        this.utility = board.getPitScores().get(0) - board.getPitScores().get(1);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Integer getUtility() {
        return utility;
    }

    public void setUtility(Integer utility) {
        this.utility = utility;
    }

    public boolean isPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return this.board.toString();
    }
}
