package com.snm.dk.kalahaaibot.control;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BoardControl {

    private final int ROW_LENGTH = 12;
    private final int BALLS_PER_AMBO = 6;

    private List<Integer> playerAMBO;
    private List<Integer> playerScores;

    public BoardControl() {
        this.playerAMBO = new ArrayList<>();
        for (int i = 0; i < this.ROW_LENGTH; i++) { this.playerAMBO.add(BALLS_PER_AMBO); }

        this.playerScores = new ArrayList<>();
        this.playerScores.add(0);
        this.playerScores.add(0);
    }

    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(this.playerAMBO.get(i).toString());
        }
        // Player1
        textViews.get(0).setText(this.playerScores.get(0).toString());
        // Player2
        textViews.get(1).setText(this.playerScores.get(1).toString());
    }

    public void moveAMBO(int playerPick, boolean iteration, int AMBO, boolean player) {
        int tempAMBO;
        int iterationInt = 0;
        tempAMBO = AMBO;

        if (!iteration) {
            tempAMBO = this.playerAMBO.get(playerPick);
            iterationInt = 1;
            this.playerAMBO.set(playerPick, 0);
        }

        if (playerPick >= 6 && playerPick <= 13) {
            if (tempAMBO > 0) {
                for (int i = playerPick+iterationInt; i < this.playerAMBO.size(); i++) {
                    if (tempAMBO == 0) {break;}
                    this.playerAMBO.set(i, 1 + this.playerAMBO.get(i));
                    tempAMBO--;
                }
                // Recursive if anything is left in tempAMBO
                if (tempAMBO > 0) {
                    if (player) {
                        this.playerScores.set(0, 1 + this.playerScores.get(0));
                        tempAMBO--;
                    }
                    moveAMBO(5, true, tempAMBO, player);
                }
            }
        }

        if (playerPick >= 0 && playerPick <= 5) {
            if (tempAMBO > 0) {
                for (int i = playerPick-iterationInt; i >= 0; i--) {
                    if (tempAMBO == 0) {break;}
                    this.playerAMBO.set(i, 1 + this.playerAMBO.get(i));
                    tempAMBO--;
                }

                if (tempAMBO > 0) {
                    if (!player) {
                        this.playerScores.set(1, 1+this.playerScores.get(1));
                        tempAMBO--;
                    }
                    moveAMBO(6, true, tempAMBO, player);
                }
            }
        }

    }

}
