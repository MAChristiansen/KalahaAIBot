package com.snm.dk.kalahaaibot.control;

import android.util.Log;

import com.snm.dk.kalahaaibot.model.Board;
import com.snm.dk.kalahaaibot.model.Node;
import com.snm.dk.kalahaaibot.model.State;
import com.snm.dk.kalahaaibot.model.Tree;

import java.util.ArrayList;
import java.util.List;

import static com.snm.dk.kalahaaibot.control.ControlReg.getAIControl;
import static com.snm.dk.kalahaaibot.control.ControlReg.getBoardControl;
import static com.snm.dk.kalahaaibot.control.ControlReg.getGameControl;

public class AIControl {

    private final String TAG = "AIControl";
    private final Integer depth = 6;

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

    public int takeAITurn() {
        List<Integer> AMBOs = new ArrayList<>();
        for (Integer ints : getGameControl().getGameBoard().getAmboScores())
            AMBOs.add(ints);

        List<Integer> PITs = new ArrayList<>();
        for (Integer ints : getGameControl().getGameBoard().getPitScores())
            PITs.add(ints);

        Board b = new Board(AMBOs, PITs);

        State state = new State(b, false);
        Node root = new Node(state, 0);
        Tree tree = new Tree(root);

        getAIControl().buildTree(tree.getRoot());

        //Log.i(TAG, "Im Starting to calculate the next move...");
        getAIControl().findHeuristisk(tree.getRoot());
        //Log.i(TAG, "Im done finding the move!");

        //Log.i(TAG, "Tree: " + tree.getRoot().getChildren().toString());

        int optimal = getAIControl().getOptimalMove(tree);

        Log.i(TAG, "Optimal index: " + optimal);

        //Den finder MAX spillerens bedste move

        return tree.getRoot().getChildren().get(optimal).getPlayerPick();
    }

    public Tree buildTree(Node root) {
        for (int i = 0; i < depth; i++) {
            //Log.i(TAG, i + "");
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
            //Log.i(TAG, "Im the leaf: " + node.getState().getBoard().toString() + " and my player is: " + node.getState().isPlayer());
            node.addChild(calculateStates(node));
            //Log.i(TAG, "boards " + node.getChildren().toString());
        }
    }

    public int getOptimalMove(Tree tree) {
        int optimal = 0;

        Integer optimalHueristic = 1000;

        for (int i = 0; i < tree.getRoot().getChildren().size(); i++) {
            Log.i(TAG, "My heuristik: " + tree.getRoot().getChildren().get(i).getState().getHeuristic());
            if (tree.getRoot().getChildren().get(i).getState().getHeuristic() < optimalHueristic) {
                optimal = i;
                optimalHueristic = tree.getRoot().getChildren().get(i).getState().getHeuristic();
            }
        }
        return optimal;
    }

    public List<Node> calculateStates(Node node) {
        List<Node> nodes = new ArrayList<>();
        int playerStart;
        int playerEnd;

        if (node.getState().isPlayer()) {
            playerStart = 6;
            playerEnd = 11;
        } else {
            playerStart = 0;
            playerEnd = 5;
        }

        for (int i = playerStart; i <= playerEnd; i++) {

            if (node.getState().getBoard().getAmboScores().get(i) != 0) {
                List<Integer> AMBOs = new ArrayList<>();
                for (Integer ints : node.getState().getBoard().getAmboScores())
                    AMBOs.add(ints);

                List<Integer> PITs = new ArrayList<>();
                for (Integer ints : node.getState().getBoard().getPitScores())
                    PITs.add(ints);

                nodes.add(new Node(new State(new Board(node.getState().getBoard()), getBoardControl().moveAMBO(i,false, 0, node.getState().isPlayer(), node.getState().getBoard())), i));
                node.getState().setBoard(new Board(AMBOs, PITs));
            }
        }
        return nodes;
    }

    /**
     * Checks the node for a Goal state. If the node is a Goal state we set the heuristic value of the node.
     * @param node
     */
    private void checkGoalState(Node node) {

        // If the game is done, hence one of the rows are empty, and the AI got more stones than the Human player
        if (getGameControl().isGameDone(node.getState().getBoard().getAmboScores()) && node.getState().getBoard().getPitScores().get(1)
                > node.getState().getBoard().getPitScores().get(0)) {
            node.getState().setHeuristic(-1000);

        // If the game is done, hence one of the rows are empty, and the AI got more stones than the Human player
        } else if (getGameControl().isGameDone(node.getState().getBoard().getAmboScores()) && node.getState().getBoard().getPitScores().get(1)
                < node.getState().getBoard().getPitScores().get(0)) {
            node.getState().setHeuristic(1000);
        }

        // If the difference between the pit (when the AI is in the lead) is bigger than the amount of stones left in play
        // set the heuristic-value to -1000.
        else if ((node.getState().getBoard().getPitScores().get(1) - node.getState().getBoard().getPitScores().get(0))
                > getGameControl().getSumOfStonesInAmbos(node.getState().getBoard().getAmboScores())) {
            node.getState().setHeuristic(-1000);
        }
        // If the difference between the pit (when the human player is in the lead) is bigger than the amount of stones left in play
        // set the heuristic-value to -1000.
        else  if ((node.getState().getBoard().getPitScores().get(0) - node.getState().getBoard().getPitScores().get(1))
                > getGameControl().getSumOfStonesInAmbos(node.getState().getBoard().getAmboScores())) {
            node.getState().setHeuristic(1000);
        }
    }
}
