package com.snm.dk.kalahaaibot.control;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BoardControl {

    private final int ROW_LENGTH = 12;
    private final int BALLS_PER_AMBO = 1;

    private final String TAG = "BoardControl";
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

    public boolean moveAMBO(int playerPick, boolean iteration, int AMBO, boolean player) {
        int i = 0;
        int tempAMBO;
        int iterationInt = 0;
        tempAMBO = AMBO;

        if (!iteration) {
            tempAMBO = this.playerAMBO.get(playerPick);
            iterationInt = 1;
            this.playerAMBO.set(playerPick, 0);
        }

        // Player1 + recursive
        if (playerPick >= 6 && playerPick <= 13) {
            if (tempAMBO > 0) {
                for (i = playerPick+iterationInt; i < this.playerAMBO.size(); i++) {
                    if (tempAMBO == 0) {break;}
                    this.playerAMBO.set(i, 1 + this.playerAMBO.get(i));
                    tempAMBO--;
                }
                i--;

                Log.i(TAG, "AMBO: " + tempAMBO + " i: " + i);
                // If player1 put the last ball in a empty ambo, both the remaining ball and the opponents straight ambo.
                if (player && tempAMBO == 0 && playerAMBO.get(i) == 1) {
                    playerScores.set(0, playerAMBO.get(i) + playerScores.get(0) + playerAMBO.get(i-6));
                    playerAMBO.set(i, 0);
                    playerAMBO.set(i-6, 0);
                    return false;
                }

                // Recursive if anything is left in tempAMBO
                if (tempAMBO > 0) {
                    if (player) {
                        // If player1 put the last ball in his pit -> extra turn
                        if (tempAMBO == 1) {
                            this.playerScores.set(0, 1 + this.playerScores.get(0));
                            return true;
                        }
                        this.playerScores.set(0, 1 + this.playerScores.get(0));
                        tempAMBO--;
                    }
                    moveAMBO(5, true, tempAMBO, player);
                }
            }
        }

        // Player2 + recursive
        if (playerPick >= 0 && playerPick <= 5) {
            if (tempAMBO > 0) {
                for (i = playerPick-iterationInt; i >= 0; i--) {
                    if (tempAMBO == 0) {break;}
                    this.playerAMBO.set(i, 1 + this.playerAMBO.get(i));
                    tempAMBO--;
                }
                i++;

                Log.i(TAG, "AMBO: " + tempAMBO + " i: " + i);
                // If player2 put the last ball in a empty ambo, both the remaining ball and the opponents straight ambo.
                if (!player && tempAMBO == 0 && playerAMBO.get(i) == 1) {
                    playerScores.set(1, playerAMBO.get(i) + playerScores.get(1) + playerAMBO.get(i+6));
                    playerAMBO.set(i, 0);
                    playerAMBO.set(i+6, 0);
                    return true;
                }

                // Recursive if anything is left in tempAMBO
                if (tempAMBO > 0) {
                    if (!player) {
                        // If player1 put the last ball in his pit -> extra turn
                        if (tempAMBO == 1) {
                            this.playerScores.set(1, 1+this.playerScores.get(1));
                            return false;
                        }
                        this.playerScores.set(1, 1+this.playerScores.get(1));
                        tempAMBO--;
                    }
                    moveAMBO(6, true, tempAMBO, player);
                }
            }
        }

        //Change player
        if (player) {return  false;} else {return true;}
    }

    public int getCount() {
        int count = 0;
        Log.i(TAG, playerAMBO.toString());
        for (int i : playerAMBO) {
            count += i;

        }
            count += playerScores.get(0);
            count += playerScores.get(1);
            return count;
    }

    public List<Integer> getPlayerAMBO() {
        return playerAMBO;
    }
}
