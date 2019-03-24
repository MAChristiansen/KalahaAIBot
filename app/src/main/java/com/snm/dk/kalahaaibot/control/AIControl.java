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

    private State rootState;
    private Node root;
    private Tree tree;
    private boolean iteration = false;

    private List<Node> nodes;

    private Board lastBoard = new Board();

    public List<Node> calculateStates(Node node) {
        this.nodes = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            List<Integer> AMBOs = new ArrayList<>();
            for (Integer ints : node.getState().getBoard().getAmboScores())
                AMBOs.add(ints);

            List<Integer> PITs = new ArrayList<>();
            for (Integer ints : node.getState().getBoard().getPitScores())
                PITs.add(ints);

            boolean b = getBoardControl().moveAMBO(i,false, 0, false, node.getState().getBoard());

            this.nodes.add(new Node(new State(new Board(node.getState().getBoard()), b)));

            Log.i(TAG, "calculateStates: " + node.getState().getBoard().toString());
            node.getState().setBoard(new Board(AMBOs, PITs));
        }
        Log.i(TAG, "NODES: " + this.nodes.toString());
        return nodes;
    }

    /*
     if (!iteration) {
            this.rootState = new State(getBoardControl().getCurrentBoard());
            this.root = new Node(rootState);
            this.tree = new Tree(root);
        }
     */

}
