package com.snm.dk.kalahaaibot.control;

import android.util.Log;

import com.snm.dk.kalahaaibot.model.Board;
import com.snm.dk.kalahaaibot.model.Node;
import com.snm.dk.kalahaaibot.model.State;
import com.snm.dk.kalahaaibot.model.Tree;

public class AIControl {

    private final String TAG = "AIControl";
    private final Integer depth = 4;

    public void visitNode(Node node) {

        Log.i(TAG, "visitNode: Utility = " + node.getState().getUtility());

        if (!(node.getChildren().isEmpty())) {
            for (Node n : node.getChildren()) {
                visitNode(n);
            }
        }
        else {
            //The node is a Leaf!
            Log.i(TAG, "visitNode = LEAF! ");
        }
    }

    public void calculateStates(Board board) {

        State rootState = new State(board);
        Node root = new Node(rootState);
        Tree tree = new Tree(root);

        //herfra skal vi findes alle børn til root. Og derefter børnene til børnene osv.

        //visitNode(root);
        for (int i = 0; i< depth; i++) {
            //laver sit shiiit!
        }


    }

}
