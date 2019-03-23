package com.snm.dk.kalahaaibot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.snm.dk.kalahaaibot.R;
import com.snm.dk.kalahaaibot.control.ControlReg;
import com.snm.dk.kalahaaibot.control.TestClass;

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

    private TestClass test = new TestClass();

    @Override
    public void onClick(View v) {
        for (int i = 0; i < this.buttons.size(); i++) {
            if (this.buttons.get(i) == v) {
               /* View[] views = {layer, fragmentContainer};
                Fragment newFragment = new FragmentResult();*/

               //test.testForLoop();

               getGameControl().takeTurn(i).updateBoard(this.buttons, this.textViews);
                ControlReg.getAIControl().calculateStates();

                /************* TEST AF TRÆ *************/
                /*State state = new State(
                        ControlReg.getBoardControl().getCurrentBoard(),
                        ControlReg.getBoardControl().getCurrentBoard().getPitScores().get(0) - ControlReg.getBoardControl().getCurrentBoard().getPitScores().get(1),
                        ControlReg.getGameControl().getCurrentPlayer());
                Node root = new Node(state);
                Tree tree = new Tree(root);


                State state2 = new State(
                        ControlReg.getBoardControl().getCurrentBoard(),
                        7,
                        ControlReg.getGameControl().getCurrentPlayer());

                State state3 = new State(
                        ControlReg.getBoardControl().getCurrentBoard(),
                        -9,
                        ControlReg.getGameControl().getCurrentPlayer());

                State state4 = new State(
                        ControlReg.getBoardControl().getCurrentBoard(),
                        10,
                        ControlReg.getGameControl().getCurrentPlayer());

                State state5 = new State(
                        ControlReg.getBoardControl().getCurrentBoard(),
                        -21,
                        ControlReg.getGameControl().getCurrentPlayer());

                State state6 = new State(
                        ControlReg.getBoardControl().getCurrentBoard(),
                        66,
                        ControlReg.getGameControl().getCurrentPlayer());

                Node node1 = new Node(state2);
                Node node2 = new Node(state3);

                root.addChild(node1);
                root.addChild(node2);
                node1.addChild(new Node(state4));
                node1.addChild(new Node(state5));
                node2.addChild(new Node(state6));

                ControlReg.getAIControl().visitNode(root);*/


            }
        }
    }

}
