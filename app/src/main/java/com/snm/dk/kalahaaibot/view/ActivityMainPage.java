package com.snm.dk.kalahaaibot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.snm.dk.kalahaaibot.R;

import java.util.ArrayList;
import java.util.List;

import static com.snm.dk.kalahaaibot.control.ControlReg.*;
import static com.snm.dk.kalahaaibot.control.GameControl.winningState;

public class ActivityMainPage extends AppCompatActivity implements View.OnClickListener {

    private List<Button> buttons;
    private List<TextView> textViews;
    private Button btnRestart;

    private final int[] BUTTON_IDS = {R.id.btnP2_1, R.id.btnP2_2, R.id.btnP2_3, R.id.btnP2_4, R.id.btnP2_5, R.id.btnP2_6,
            R.id.btnP1_1, R.id.btnP1_2, R.id.btnP1_3, R.id.btnP1_4, R.id.btnP1_5, R.id.btnP1_6};

    private final int[] TEXTVIEW_IDS = {R.id.tvPlayer1Pit, R.id.tvPlayer2Pit, R.id.tvStatus};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        btnRestart = findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        for (int i = 0; i < this.buttons.size(); i++) {
            if (this.buttons.get(i) == v) {
                if (getGameControl().getGameBoard().getAmboScores().get(i) == 0) {
                    return;
                }
               getGameControl().takeTurn(i, this.buttons, this.textViews);
            }
        }

        if (v == btnRestart) {
            for (Button b : this.buttons) {
                b.setClickable(true);
            }
            winningState = false;
            Log.i("restart", "Restart clicked!");
            setGameControl(null);
            getAIControl().setTree(null);
            getGameControl().updateBoard(this.buttons, this.textViews);
        }

    }
}
