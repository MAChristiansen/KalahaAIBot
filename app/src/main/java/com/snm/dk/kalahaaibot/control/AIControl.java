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
    private final Integer depth = 8;

    /**
     * Takes a AI move based on MiniMax tree-search.
     * @return a int that represent the optimal chose of ambo.
     */
    public int takeAITurn() {
        List<Integer> AMBOs = new ArrayList<>();
        for (Integer ints : getGameControl().getGameBoard().getAmboScores())
            AMBOs.add(ints);

        List<Integer> PITs = new ArrayList<>();
        for (Integer ints : getGameControl().getGameBoard().getPitScores())
            PITs.add(ints);

        //Build a new board based on the gameBoard from the GameControl. A workaround to avoid pointer issue.
        Board board = new Board(AMBOs, PITs);

        //Generate the tree with the root node.
        //We always start the tree from a AI chose. Therefore the player is false = AI.
        State state = new State(board, false);
        Node root = new Node(state,0);
        Tree tree = new Tree(root);

        //Building the tree based on the suggested depth.
        buildTree(tree.getRoot());

        //Find the heuristic values for all the nodes in the tree.
        findHeuristic(tree.getRoot());

        //Find the index to the optimal child.
        int optimal = getAIControl().getOptimalMove(tree);

        Log.i(TAG, "Optimal index: " + optimal);

        // Return the playerPick for the optimal AI move.
        return tree.getRoot().getChildren().get(optimal).getPlayerPick();
    }

    /**
     * Goes to the leafs, and calculate the heuristic values - recursive.
     * @param node
     */
    private void findHeuristic(Node node) {
        if (!(node.getChildren().isEmpty())) {
            for (Node n : node.getChildren()) {
                findHeuristic(n);
            }
        }
        else {
            //We found a leaf! - now starts the calculation.
            node.getState().setHeuristic(node.getState().getUtility());
            calculateHeuristic(node);
        }
    }

    /**
     * Method use in "findHeuristic()". Calculate the heuristic value for all the nodes.
     * @param node
     */
    private void calculateHeuristic(Node node) {

        //Are we in the top layer?
        if (node.getParent().getParent() != null) {

            //generate parent from node
            Node parent = node.getParent();

            // checks if the node is a goal state - if so, we set the heuristic value to 1000,
            // or if the node is a losing state, we set we set the heuristic value to 1000.
            checkGoalState(node);

            // if the parent is a Human-player node, calculate the heuristic value.
            if (parent.getState().isPlayer()) {
                if (parent.getState().getHeuristic() == null || (node.getState().getHeuristic() + parent.getState().getUtility()) < parent.getState().getHeuristic()) {
                    parent.getState().setHeuristic(node.getState().getHeuristic() + parent.getState().getUtility());
                }
            }
            // if the parent is a AI-player node, calculate the heuristic value.
            else {
                if (parent.getState().getHeuristic() == null || (node.getState().getHeuristic() + parent.getState().getUtility()) > parent.getState().getHeuristic()) {
                    parent.getState().setHeuristic(node.getState().getHeuristic() + parent.getState().getUtility());
                }
            }

            //start the recursive algo to the root.
            calculateHeuristic(node.getParent());
        }
    }

    /**
     * A method that build the tree structure.
     * @param node
     */
    private void buildTree(Node node) {
        // creating the tree by looping over from 0 -> 'depth', and build children to the leafs.
        for (int i = 0; i < depth; i++) {
            buildStatesToLeafs(node);
        }
    }

    /**
     * Takes in a node, and build til children to that node.
     * @param node
     */
    private void buildStatesToLeafs(Node node) {
        if (!(node.getChildren().isEmpty())) {
            for (Node n : node.getChildren()) {
                buildStatesToLeafs(n);
            }
        }
        else {
            //We found the leaf, and now build the children to the node.
            node.addChild(calculateStates(node));

        }
    }

    /**
     * Takes a tree and return a integer that represent the index for the optimal child from the root node.
     * @param tree - the tree you want to find the best move from.
     * @return a integer that represent the index for the optimal child from the root node.
     */
    private int getOptimalMove(Tree tree) {
        int optimal = 0;

        Integer optimalHueristic = 1001;

        for (int i = 0; i < tree.getRoot().getChildren().size(); i++) {
            Log.i(TAG, "My heuristik: " + tree.getRoot().getChildren().get(i).getState().getHeuristic());
            if (tree.getRoot().getChildren().get(i).getState().getHeuristic() < optimalHueristic) {
                optimal = i;
                optimalHueristic = tree.getRoot().getChildren().get(i).getState().getHeuristic();
            }
        }
        return optimal;
    }


    private List<Node> calculateStates(Node node) {
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
