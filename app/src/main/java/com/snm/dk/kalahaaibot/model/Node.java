package com.snm.dk.kalahaaibot.model;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private State state;
    private Node parent;
    private int playerPick;
    private List<Node> children;

    public Node(State state, int playerPick) {
        this.state = state;
        this.playerPick = playerPick;
        children = new ArrayList<>();
    }

    public void addChild(List<Node> nodes) {
        for (Node node : nodes) {
            node.parent = this;
            children.add(node);
        }
    }

    @Override
    public String toString() {
        return this.state.toString();
    }

    public State getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getPlayerPick() {
        return playerPick;
    }
}
