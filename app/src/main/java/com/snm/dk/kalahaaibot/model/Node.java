package com.snm.dk.kalahaaibot.model;

import java.util.ArrayList;

public class Node {

    private State state;
    private Node parent;
    private ArrayList<Node> children;

    public Node(State state, Node parent, ArrayList<Node> children) {
        this.state = state;
        this.parent = parent;
        this.children = children;
    }

    public Node(State state) {
        this.state = state;
        children = new ArrayList<>();
    }

    public void addChild(Node node) {
        node.parent = this;
        children.add(node);
    }

    public void removeChild(int child) {
        if(children.isEmpty()) {
            children.remove(child);
        }
        else
        {
            for(int i = 0; i < children.size(); i++)
            {
                children.get(child).removeChild(i);
            }
        }
    }

    public State getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }
}
