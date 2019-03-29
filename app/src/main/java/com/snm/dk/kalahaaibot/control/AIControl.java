package com.snm.dk.kalahaaibot.control;

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
    private final Integer depth = 5;
    private Tree tree;

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
        this.tree = new Tree(root);

        //Building the tree based on the suggested depth.
        buildTree(tree.getRoot());

        //Set heuristic values to the leafs
        setHeuristicToLeafs(tree.getRoot());

        //Find the heuristic values for all the nodes in the tree.
        tree.getRoot().getState().setHeuristic(miniMaxAB(tree.getRoot(), depth, (int) Double.NEGATIVE_INFINITY,(int) Double.POSITIVE_INFINITY, tree.getRoot().getState().isPlayer()));

        //Find the index to the optimal child.
        int optimal = getAIControl().getOptimalNode(tree);

        if (tree.getRoot().getChildren().size() == 0 && optimal == 0) {
            return 0;
        }

        // Return the playerPick for the optimal AI move.
        return tree.getRoot().getChildren().get(optimal).getPlayerPick();
    }

    /**
     * The minimax algo, with alpha-beta pruning. The algo finds the optimal heuristic value.
     * @param node
     * @param depth
     * @param alpha
     * @param beta
     * @param maximizingPlayer
     * @return The best heuristic value. (The heuristic value for the root node)
     */
    public int miniMaxAB(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {


        if (depth == 0 || checkGoalState(node)) {
            return node.getState().getHeuristic();
        }


        if (maximizingPlayer) {
            int bestValue = (int) Double.NEGATIVE_INFINITY;

            for (Node child : node.getChildren()) {
                bestValue = Math.max(bestValue, miniMaxAB(child, depth - 1, alpha, beta, child.getState().isPlayer()));

                //beta cuttina
                alpha = Math.max(alpha, bestValue);
                if (alpha >= beta) {
                    break;
                }


                //Sets the heuristic value to the children to the root. Later we cant evaluate which move is the best.
                if (child.getParent().getParent() == null) {
                    child.getState().setHeuristic(bestValue);
                }
            }

            return bestValue;
        }
        else {
            int bestValue = (int) Double.POSITIVE_INFINITY;

            for (Node child : node.getChildren()) {
                bestValue = Math.min(bestValue, miniMaxAB(child, depth - 1, alpha, beta, child.getState().isPlayer()));

                //alpha cutting
                beta = Math.min(beta, bestValue);
                if (alpha >= beta) {
                    break;
                }

                //Sets the heuristic value to the children to the root. Later we cant evaluate which move is the best.
                if (child.getParent().getParent() == null) {
                    child.getState().setHeuristic(bestValue);
                }
            }

            return bestValue;
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
     * Sets the starting heuristic values to the leaf nodes.
     * @param node
     */
    private void setHeuristicToLeafs(Node node) {
        if (!(node.getChildren().isEmpty())) {
            for (Node n : node.getChildren()) {
                setHeuristicToLeafs(n);
            }
        }
        else {
            //We found the leaf, and now build the children to the node.
            node.getState().setHeuristic(node.getState().getUtility());
        }
    }

    /**
     * Takes a tree and return a integer that represent the index for the optimal child from the root node.
     * @param tree
     * @return the index of the button to press.
     */
    private int getOptimalNode(Tree tree) {

        List<Integer> sameHeuristic = new ArrayList<>();

        for (int i = 0; i < tree.getRoot().getChildren().size(); i++) {
            if (tree.getRoot().getChildren().get(i).getState().getHeuristic() == null) {
                tree.getRoot().getChildren().get(i).getState().setHeuristic(tree.getRoot().getChildren().get(i).getState().getUtility());
            }
            if ((tree.getRoot().getChildren().get(i).getState().getHeuristic()) == (tree.getRoot().getState().getHeuristic())) {
                sameHeuristic.add(i);
            }
        }

        if (sameHeuristic.size() < 1) {
            return 0;
        }

        //If we have several of the same heuristic values, we choose one of the optimal, by chance
        return sameHeuristic.get((randomWithRange(0, sameHeuristic.size())));
    }



    /**
     * Generate states based on a node. A help method for 'buildStatesToLeafs' that generates children to the leaf.
     * @param node
     * @return - A list of children to the node.
     */
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

                nodes.add(new Node(
                                new State(
                                new Board(
                                        node.getState().getBoard()),
                                        getBoardControl().moveAMBO(i,
                                        false,
                                        0,
                                        node.getState().isPlayer(),
                                        node.getState().getBoard())),
                                        i));

                node.getState().setBoard(new Board(AMBOs, PITs));
            }
        }
        return nodes;
    }

    public boolean AIWonBasedOnHeuristic(Tree tree) {

        if (tree != null) {
            for (Node n : tree.getRoot().getChildren()) {
                if (n.getState().getHeuristic() != -100) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean HumanWonBasedOnHeuristic(Tree tree) {

        if (tree != null) {
            for (Node n : tree.getRoot().getChildren()) {
                if (n.getState().getHeuristic() != 100) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checks the node for a Goal state. If the node is a Goal state we set the heuristic value of the node.
     * @param node
     */
    private boolean checkGoalState(Node node) {

        // If the game is done, hence one of the rows are empty, and the AI got more stones than the Human player
        if (getGameControl().isGameDone(node.getState().getBoard().getAmboScores()) && node.getState().getBoard().getPitScores().get(1)
                > node.getState().getBoard().getPitScores().get(0)) {
            node.getState().setHeuristic(-100);
            return true;

        // If the game is done, hence one of the rows are empty, and the AI got more stones than the Human player
        } else if (getGameControl().isGameDone(node.getState().getBoard().getAmboScores()) && node.getState().getBoard().getPitScores().get(1)
                < node.getState().getBoard().getPitScores().get(0)) {
            node.getState().setHeuristic(100);
            return true;
        }

        // If the difference between the pit (when the AI is in the lead) is bigger than the amount of stones left in play
        // set the heuristic-value to -100.
        else if ((node.getState().getBoard().getPitScores().get(1) - node.getState().getBoard().getPitScores().get(0))
                > getGameControl().getSumOfStonesInAmbos(node.getState().getBoard().getAmboScores())) {
            node.getState().setHeuristic(-100);
            return true;
        }
        // If the difference between the pit (when the human player is in the lead) is bigger than the amount of stones left in play
        // set the heuristic-value to -100.
        else  if ((node.getState().getBoard().getPitScores().get(0) - node.getState().getBoard().getPitScores().get(1))
                > getGameControl().getSumOfStonesInAmbos(node.getState().getBoard().getAmboScores())) {
            node.getState().setHeuristic(100);
            return true;
        }
        return false;
    }

    /**
     * Calculate a random int value for our chose of optimal move.
     * @param min
     * @param max
     * @return
     */
    private int randomWithRange(int min, int max) {
        int range = Math.abs(max-min);

        return (int) (Math.random() * range) + min;
    }

    public Tree getTree() {
        return this.tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }
}
