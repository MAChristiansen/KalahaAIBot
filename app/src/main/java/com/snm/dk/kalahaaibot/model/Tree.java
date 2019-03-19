package com.snm.dk.kalahaaibot.model;

public class Tree {

    private Node root;

    public Tree(Node node) {
        root = node;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
