package com.snm.dk.kalahaaibot.model;

import java.util.ArrayList;

public class State {

    private ArrayList<Integer> state;
    private Integer utility;

    public State(ArrayList<Integer> state, Integer utility) {
        this.state = state;
        this.utility = utility;
    }

    public ArrayList<Integer> getState() {
        return state;
    }

    public void setState(ArrayList<Integer> state) {
        this.state = state;
    }

    public Integer getUtility() {
        return utility;
    }

    public void setUtility(Integer utility) {
        this.utility = utility;
    }
}
