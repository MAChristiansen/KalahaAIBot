package com.snm.dk.kalahaaibot.control;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardControl {

    private final int ROW_LENGTH = 6;
    private final int BALLS_PER_AMBO = 6;

    private List<Integer> player1Score;
    private List<Integer> player2Score;
    private List<Integer> playerScores;

    public BoardControl() {
        this.player1Score = new ArrayList<>();
        for (int i = 0; i < this.ROW_LENGTH; i++) { this.player1Score.add(BALLS_PER_AMBO); }

        this.player2Score = new ArrayList<>();
        for (int i = 0; i < this.ROW_LENGTH; i++) { this.player2Score.add(BALLS_PER_AMBO); }

        this.player1Score.addAll(this.player2Score);

        this.playerScores = new ArrayList<>();
        this.playerScores.add(0);
        this.playerScores.add(0);
    }

    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(this.player1Score.get(i).toString());
        }
        // Player1
        textViews.get(0).setText(this.playerScores.get(0).toString());
        // Player2
        textViews.get(1).setText(this.playerScores.get(1).toString());

        Log.e("HELLO", this.player1Score.toString());
    }

    public void moveAMBO(int playerPick, boolean iteration, int AMBO, boolean player) {
        int tempAMBO;
        int iterationInt = 0;
        tempAMBO = AMBO;

        if (!iteration) {
            tempAMBO = this.player1Score.get(playerPick);
            iterationInt = 1;
            this.player1Score.set(playerPick, 0);
        }

        if (playerPick >= 6 && playerPick <= 13) {
            if (tempAMBO > 0) {
                for (int i = playerPick+iterationInt; i < this.player1Score.size(); i++) {
                    if (tempAMBO == 0) {break;}
                    this.player1Score.set(i, 1 + this.player1Score.get(i));
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
                    this.player1Score.set(i, 1 + this.player1Score.get(i));
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
