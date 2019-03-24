package com.snm.dk.kalahaaibot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.snm.dk.kalahaaibot.R;
import com.snm.dk.kalahaaibot.control.ControlReg;
import com.snm.dk.kalahaaibot.control.TestClass;
import com.snm.dk.kalahaaibot.model.Board;
import com.snm.dk.kalahaaibot.model.Node;
import com.snm.dk.kalahaaibot.model.State;
import com.snm.dk.kalahaaibot.model.Tree;

import java.util.ArrayList;
import java.util.List;

import static com.snm.dk.kalahaaibot.control.ControlReg.*;

public class ActivityMainPage extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "Activity";

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

    private State state = new State(getGameControl().getGameBoard());
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
               makeTree(2);

                /*
                ControlReg.getAIControl().visitNode(root);
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
