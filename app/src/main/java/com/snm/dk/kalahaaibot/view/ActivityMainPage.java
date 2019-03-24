package com.snm.dk.kalahaaibot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.snm.dk.kalahaaibot.R;
import com.snm.dk.kalahaaibot.model.Node;
import com.snm.dk.kalahaaibot.model.State;
import com.snm.dk.kalahaaibot.model.Tree;

import java.util.ArrayList;
import java.util.List;

import static com.snm.dk.kalahaaibot.control.ControlReg.*;

public class ActivityMainPage extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "GameMode";

    private List<Button> buttons;
    private List<TextView> textViews;
    private FrameLayout layer, fragmentContainer;

    private final int[] BUTTON_IDS = {R.id.btnP2_1, R.id.btnP2_2, R.id.btnP2_3, R.id.btnP2_4, R.id.btnP2_5, R.id.btnP2_6,
            R.id.btnP1_1, R.id.btnP1_2, R.id.btnP1_3, R.id.btnP1_4, R.id.btnP1_5, R.id.btnP1_6};

    private final int[] TEXTVIEW_IDS = {R.id.tvPlayer1Pit, R.id.tvPlayer2Pit, R.id.tvStatus};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        layer = findViewById(R.id.layer);
        fragmentContainer = findViewById(R.id.fragmentContainer);

        int k = 0;
        // Building Button Array
        this.buttons = new ArrayList<>();
        for(int id : this.BUTTON_IDS) {
            Button button = findViewById(id);
            button.setOnClickListener(this); // maybe
            button.setText(k+"");
            k++;
            this.buttons.add(button);
        }

        // Building TextView Array
        this.textViews = new ArrayList<>();
        for (int id : this.TEXTVIEW_IDS) {
            TextView textView = findViewById(id);
            this.textViews.add(textView);
        }

        // First Update to board.
        getGameControl().updateBoard(this.buttons, this.textViews);
    }

    private State state = new State(getGameControl().getGameBoard(), false);
    private Node root = new Node(state);
    private Tree tree = new Tree(root);
    private List<Node> nodes;

    @Override
    public void onClick(View v) {
        for (int i = 0; i < this.buttons.size(); i++) {
            if (this.buttons.get(i) == v) {
               /* View[] views = {layer, fragmentContainer};
                Fragment newFragment = new FragmentResult();*/

               getGameControl().takeTurn(i).updateBoard(this.buttons, this.textViews);
               getAIControl().buildTree(tree.getRoot());

                /*
                ControlReg.getAIControl().visitNode(root);
                 */

                /************* TEST AF TRÆ *************/
                boolean MAX = true;
                boolean MIN = false;

                /*
                State state = new State(
                        getGameControl().getGameBoard(),
                        getGameControl().getGameBoard().getPitScores().get(0) - getGameControl().getGameBoard().getPitScores().get(1),
                        MAX);
                Node root = new Node(state);
                Tree tree = new Tree(root);




                State state2 = new State(
                        getGameControl().getGameBoard(),
                        7,
                        MAX);

                State state3 = new State(
                        getGameControl().getGameBoard(),
                        -9,
                        MAX);

                State state4 = new State(
                        getGameControl().getGameBoard(),
                        10,
                        MAX);

                State state5 = new State(
                        getGameControl().getGameBoard(),
                        -21,
                        MAX);

                State state6 = new State(
                        getGameControl().getGameBoard(),
                        11,
                        MIN);

                State state7 = new State(
                        getGameControl().getGameBoard(),
                        80,
                       MAX);

                State state8 = new State(
                        getGameControl().getGameBoard(),
                        -23,
                        MAX);

                State state9 = new State(
                        getGameControl().getGameBoard(),
                        22,
                        MAX);

                State state10 = new State(
                        getGameControl().getGameBoard(),
                        2,
                        MAX);

                State state11 = new State(
                        getGameControl().getGameBoard(),
                        5,
                        MAX);

                Node node7 = new Node(state2);
                Node nodeM9 = new Node(state3);
                Node node10 = new Node(state4);
                Node nodeM21 = new Node(state5);
                Node node11 = new Node(state6);
                Node node80 = new Node(state7);
                Node nodeM23 = new Node(state8);
                Node node22 = new Node(state9);
                Node node2 = new Node(state10);
                Node node5 = new Node(state11);


                root.addChild(node7);
                root.addChild(nodeM9);

                node7.addChild(node10);
                node7.addChild(nodeM23);

                nodeM9.addChild(node11);

                node10.addChild(node80);
                node10.addChild(nodeM21);

                nodeM23.addChild(node22);
                nodeM23.addChild(node2);

                node11.addChild(node5);

                ControlReg.getAIControl().visitNode(tree.getRoot());

                Node optimal = ControlReg.getAIControl().getOptimalMove(tree);

                //Lige nu finder vi den mindste (MIN players tur)
                Log.i(TAG, "Optimal Move: " + optimal.getState().getHeuristic());
                */
            }
        }
    }

    private void makeTree(int dybte) {
        if (this.nodes == null) {
            this.nodes = new ArrayList<>();
            this.nodes = getAIControl().calculateStates(this.root);
            this.root.addChild(this.nodes);
        }

        if (dybte > 0) {

            dybte--;
            makeTree(dybte);
        }
        //getAIControl().visitNode(this.root);
    }

}
