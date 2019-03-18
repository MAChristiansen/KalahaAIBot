package com.snm.dk.kalahaaibot.control;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class GameControl {

    private final String TAG = "GameControl";
    private BoardControl boardControl;
    private boolean player;

    public GameControl() {
        boardControl = new BoardControl();
        this.player = true;
    }

    public GameControl takeTurn(int playerPick) {
        // Player1
        if (this.player && playerPick > 5) {
            this.player = boardControl.moveAMBO(playerPick, false, 0, this.player);
        }
        // Player2
        else if (!this.player && playerPick <= 5) {
            this.player = boardControl.moveAMBO(playerPick, false, 0, this.player);
        }
        return this;
    }

    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        Log.i(TAG, "updateBoard: " + this.player);
        if (this.player) textViews.get(2).setText("Player 1 turn: Total " + boardControl.getCount()); else textViews.get(2).setText("Player 2 turn: Total " +boardControl.getCount());
        boardControl.updateBoard(buttons, textViews);
    }

    private boolean isGameDone() {
        return true;
    }

}
