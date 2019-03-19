package com.snm.dk.kalahaaibot.model;

import com.snm.dk.kalahaaibot.interfaces.IPlayer;

public class Player implements IPlayer {

    private String name;
    private int score = 0;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public void setPlayerName(String name) {
        this.name = name;
    }

    @Override
    public String getPlayerName() {
        return this.name;
    }

    @Override
    public void setPlayerScore(int newScore) {
        this.score = newScore;
    }

    @Override
    public int getPlauyerScore() {
        return this.score;
    }
}
