package com.snm.dk.kalahaaibot.control;

import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class GameControl {

    private BoardControl boardControl;

    public GameControl() {
        boardControl = new BoardControl();
    }

    public GameControl takeTurn(int playerPick) {
        boardControl.moveAMBO(playerPick);
        return this;
    }

    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        boardControl.updateBoard(buttons, textViews);
    }

}
