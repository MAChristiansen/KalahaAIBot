package com.snm.dk.kalahaaibot.control;

import android.util.Log;

import com.snm.dk.kalahaaibot.model.Board;
import com.snm.dk.kalahaaibot.model.Node;
import com.snm.dk.kalahaaibot.model.State;
import com.snm.dk.kalahaaibot.model.Tree;

import java.util.ArrayList;
import java.util.List;

import static com.snm.dk.kalahaaibot.control.ControlReg.getBoardControl;

public class AIControl {

    private final String TAG = "AIControl";
    private final Integer depth = 4;

    public void visitNode(Node node) {

        //Log.i(TAG, "visitNode: Utility = " + node.getState().getUtility());

        if (!(node.getChildren().isEmpty())) {
            for (Node n : node.getChildren()) {
                visitNode(n);
            }
        }
        else {
            //The node is a Leaf!
            //Log.i(TAG, "visitNode = LEAF! ");
            node.getState().setHeuristic(node.getState().getUtility());
            visitParent(node);
        }
    }

    public void visitParent(Node node) {

        //Are we in the top layer?
        if (node.getParent().getParent() != null) {

            //generate parent from node
            Node parent = node.getParent();

            //Log.i(TAG, "Node Utility " + node.getState().getUtility() + " Node parent: " + parent.getState().getUtility());

            if (parent.getState().isPlayer()) {
                if (parent.getState().getHeuristic() == null || (node.getState().getHeuristic() + parent.getState().getUtility()) < parent.getState().getHeuristic()) {
                    //Log.i(TAG, "Im setting node  " + parent.getState().getUtility() + " from H " + parent.getState().getHeuristic() + " to " + (node.getState().getHeuristic() + parent.getState().getUtility()));
                    parent.getState().setHeuristic(node.getState().getHeuristic() + parent.getState().getUtility());
                }
            }
            else {
                if (parent.getState().getHeuristic() == null || (node.getState().getHeuristic() + parent.getState().getUtility()) > parent.getState().getHeuristic()) {
                    //Log.i(TAG, "Im setting node  " + parent.getState().getUtility() + " from H " + parent.getState().getHeuristic() + " to " + (node.getState().getHeuristic() + parent.getState().getUtility()));
                    parent.getState().setHeuristic(node.getState().getHeuristic() + parent.getState().getUtility());
                }
            }
            visitParent(node.getParent());
        }
    }

    public Node getOptimalMove(Tree tree) {
        Node optimal = null;

        for (Node n : tree.getRoot().getChildren()) {
            if (optimal == null || n.getState().getHeuristic() < optimal.getState().getHeuristic()) {
                //Log.i(TAG, "getOptimalMove: " + n.getState().getHeuristic());
                optimal = n;
            }

        }

        return optimal;
    }

    private List<Node> nodes;

    public List<Node> calculateStates(Node node) {
        this.nodes = new ArrayList<>();

        for (int i = 0; i <= 11; i++) {
            List<Integer> AMBOs = new ArrayList<>();
            for (Integer ints : node.getState().getBoard().getAmboScores())
                AMBOs.add(ints);

            List<Integer> PITs = new ArrayList<>();
            for (Integer ints : node.getState().getBoard().getPitScores())
                PITs.add(ints);

            this.nodes.add(new Node(new State(new Board(node.getState().getBoard()), getBoardControl().moveAMBO(i,false, 0, false, node.getState().getBoard()))));
            node.getState().setBoard(new Board(AMBOs, PITs));
        }


        // TODO FJERN SENERE
        for (Node of : nodes) {
            Log.i(TAG, "calculateStates: " + of.getState().toString());
        }

        return this.nodes;

    }

}
