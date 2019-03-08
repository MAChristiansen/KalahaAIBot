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

    public BoardControl() {
        this.player1Score = new ArrayList<>();
        for (int i = 0; i < this.ROW_LENGTH; i++) { this.player1Score.add(BALLS_PER_AMBO); }
        this.player1Score.add(0);

        this.player2Score = new ArrayList<>();
        for (int i = 0; i < this.ROW_LENGTH; i++) { this.player2Score.add(BALLS_PER_AMBO); }
        this.player2Score.add(0);
    }

    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        for (int i = 0; i < buttons.size()/2; i++) {
            buttons.get(i).setText(this.player2Score.get(i).toString());
            buttons.get(i+6).setText(this.player1Score.get(i).toString());
        }
        // Player1
        textViews.get(0).setText(this.player1Score.get(this.player1Score.size()-1).toString());
        // Player2
        textViews.get(1).setText(this.player2Score.get(this.player2Score.size()-1).toString());
    }

    public void moveAMBO(int playerPick) {
        int translatedPick = 0;
        // Player1
        if (playerPick >= 5 && playerPick <= 11) {
            switch (playerPick) {
                case 6: translatedPick = 0; break;
                case 7: translatedPick = 1; break;
                case 8: translatedPick = 2; break;
                case 9: translatedPick = 3; break;
                case 10: translatedPick = 4; break;
                case 11: translatedPick = 5; break;
            }
            updateScore(playerPick, true);
        }

        // Player2
        if (playerPick >= 0 && playerPick < 5) {
            updateScore(playerPick, false);
        }
        this.player1Score.set(translatedPick,playerPick);
    }

    private void updateScore(int playerPick, boolean player) {
        if (player) {
            
        }

        if (!player) {

        }
    }

}
