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
    private final Integer depth = 2;

    public void findHeuristisk(Node node) {

        //Log.i(TAG, "findHeuristisk: Utility = " + node.getState().getUtility());

        if (!(node.getChildren().isEmpty())) {
            for (Node n : node.getChildren()) {
                findHeuristisk(n);
            }
        }
        else {
            //The node is a Leaf!
            //Log.i(TAG, "findHeuristisk = LEAF! ");
            node.getState().setHeuristic(node.getState().getUtility());
            calculateHeuristisk(node);
        }
    }

    public void calculateHeuristisk(Node node) {

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
            calculateHeuristisk(node.getParent());
        }
    }

    public Tree buildTree(Node root) {
        for (int i = 0; i < depth; i++) {
            Log.i(TAG, i + "");
            buildStatesToLeafs(root);
        }

        return new Tree(root);
    }

    public void buildStatesToLeafs(Node node) {
        if (!(node.getChildren().isEmpty())) {
            for (Node n : node.getChildren()) {
                buildStatesToLeafs(n);
            }
        }
        else {
            Log.i(TAG, "Im the leaf: " + node.getState().getBoard().toString());
            node.addChild(calculateStates(node));
            Log.i(TAG, "And this is my Children: \n" + node.getChildren().toString());
        }
    }

    public Node getOptimalMove(Tree tree) {
        Node optimal = null;

        for (Node n : tree.getRoot().getChildren()) {
            //Log.i(TAG, "getOptimalMove: " + tree.getRoot().getChildren().toString());
            if (optimal == null || n.getState().getHeuristic() > optimal.getState().getHeuristic()) {
                //Log.i(TAG, "getOptimalMove: " + n.getState().getHeuristic());
                optimal = n;
            }

        }

        return optimal;
    }

    private List<Node> nodes;

    public List<Node> calculateStates(Node node) {
        this.nodes = new ArrayList<>();

            for (int i = 0; i <= 5; i++) {
                List<Integer> AMBOs = new ArrayList<>();
                for (Integer ints : node.getState().getBoard().getAmboScores())
                    AMBOs.add(ints);

                List<Integer> PITs = new ArrayList<>();
                for (Integer ints : node.getState().getBoard().getPitScores())
                    PITs.add(ints);

                this.nodes.add(new Node(new State(new Board(node.getState().getBoard()), getBoardControl().moveAMBO(i,false, 0, false, node.getState().getBoard()))));
                node.getState().setBoard(new Board(AMBOs, PITs));
            }

       /* // TODO FJERN SENERE
        for (Node of : nodes) {
            Log.i(TAG, "calculateStates: " + of.getState().toString());
        }*/

        return this.nodes;

    }

}
