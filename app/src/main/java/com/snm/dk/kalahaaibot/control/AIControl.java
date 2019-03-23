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


    private boolean iteration = false;

    private List<Node> nodes;

    private Board lastBoard = new Board();

    public Tree calculateStates(Node root) {

        Tree tree = new Tree(root);

        if (!iteration) {
            this.lastBoard = new Board(getBoardControl().getCurrentBoard());
            this.nodes = new ArrayList<>();
        }

        //herfra skal vi findes alle børn til root. Og derefter børnene til børnene osv.

        //visitNode(root);

        for (int i = 0; i <= 5; i++) {
            List<Integer> AMBOs = new ArrayList<>();
            for (Integer ints : getBoardControl().getCurrentBoard().getAmboScores())
                AMBOs.add(ints);

            List<Integer> PITs = new ArrayList<>();
            for (Integer ints : getBoardControl().getCurrentBoard().getPitScores())
                PITs.add(ints);

            this.nodes.add(new Node(new State(new Board(getBoardControl().getCurrentBoard()), getBoardControl().moveAMBO(i,false, 0, false))));

            getBoardControl().setCurrentBoard(new Board(AMBOs, PITs));
        }

        Log.i(TAG, "Board: " + this.nodes.toString());

        for (int i = 0; i < this.nodes.size(); i++) {
            root.addChild(this.nodes.get(i));
        }

        Log.i(TAG, root.toString());

       return tree;

    }

}
