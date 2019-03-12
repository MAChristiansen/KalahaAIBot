package com.snm.dk.kalahaaibot.control;

import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class GameControl {

    private BoardControl boardControl;
    private boolean player;

    public GameControl() {
        boardControl = new BoardControl();
        this.player = true;
    }

    public GameControl takeTurn(int playerPick) {
        // Player1
        if (this.player && playerPick > 5) {
            boardControl.moveAMBO(playerPick, 0);
            this.player = false;
        }
        // Player2
        else if (!this.player && playerPick <= 5) {
            boardControl.moveAMBO(playerPick, 0);
            this.player = true;
        }
        return this;
    }

    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        // TODO: Remove boardcontrol.getcount(); later
        if (this.player) textViews.get(2).setText("Player 1 turn" + " " + boardControl.getCount()); else textViews.get(2).setText("Player 2 turn" + " " + boardControl.getCount());
        boardControl.updateBoard(buttons, textViews);
    }

}
