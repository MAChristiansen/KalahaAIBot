package com.snm.dk.kalahaaibot.model;

import com.snm.dk.kalahaaibot.interfaces.IPlayer;

public class PlayerAI implements IPlayer {

    private String name = "JohnDOE!";
    private int score = 0;

    public PlayerAI() {
        // Empty Constructor, should not have to build the AI player.
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
