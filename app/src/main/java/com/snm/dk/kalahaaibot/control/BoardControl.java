package com.snm.dk.kalahaaibot.control;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BoardControl {

    private final int ROW_LENGTH = 12;
    private final int BALLS_PER_AMBO = 6;

    private final String TAG = "Test";
    private List<Integer> AMBOs;
    private List<Integer> playerPits;

    public BoardControl() {
        this.AMBOs = new ArrayList<>();
        for (int i = 0; i < this.ROW_LENGTH; i++) { this.AMBOs.add(BALLS_PER_AMBO); }

        this.playerPits = new ArrayList<>();
        this.playerPits.add(0);
        this.playerPits.add(0);
    }

    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(this.AMBOs.get(i).toString());
        }
        // Player1
        textViews.get(0).setText(this.playerPits.get(0).toString());
        // Player2
        textViews.get(1).setText(this.playerPits.get(1).toString());
    }

    public void moveAMBO(int playerPick, int AMBO) {

        int pickedAMBO = AMBO;
        int i = 0;

        // Player1
        if (playerPick > 5) {
            for (i = playerPick; i < AMBOs.size()-1; i++) {
                if (i == playerPick) {
                    pickedAMBO = AMBOs.get(i);
                    AMBOs.set(i, 0);
                }
                AMBOs.set(i+1, AMBOs.get(i+1)+1);
                pickedAMBO--;
            }

            if (pickedAMBO > 0) {
                if (i == 11 && pickedAMBO >= 1) {
                    playerPits.set(0, 1 + playerPits.get(0));
                    pickedAMBO--;
                    moveAMBO(5, pickedAMBO);
                }
            }
        }

        // Player2
        if (playerPick <= 5) {

        }

    }

    // TODO: Remove Later.
    public int getCount() {
        int count = 0;

        for (int i : AMBOs) {
            count += i;
        }

        count += playerPits.get(0);
        count += playerPits.get(1);

        return count;
    }

}
