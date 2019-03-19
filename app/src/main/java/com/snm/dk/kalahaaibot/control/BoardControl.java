package com.snm.dk.kalahaaibot.control;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.snm.dk.kalahaaibot.model.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardControl {

    private final int ROW_LENGTH = 12;
    private final int BALLS_PER_AMBO = 6;

    private final String TAG = "BoardControl";
    private Board board;

    public BoardControl() {
        // Init board
        board = new Board();
        for (int i = 0; i < this.ROW_LENGTH; i++) {
            this.board.getAmboScores().add(BALLS_PER_AMBO);
        }

        this.board.getPitScores().add(0);
        this.board.getPitScores().add(0);
    }



    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(this.board.getAmboScores().get(i).toString());
        }
        // Player1
        textViews.get(0).setText(this.board.getPitScores().get(0).toString());
        // Player2
        textViews.get(1).setText(this.board.getPitScores().get(1).toString());
    }

    public List<String> updateAmbos() {

        List<String> amboScores = new ArrayList<>();

        for (int i = 0; i < ROW_LENGTH; i++) {
            amboScores.set(i, this.board.getAmboScores().get(i).toString());
        }

        return amboScores;
    }

    public List<String> updatePits() {

        List<String> pitScores = new ArrayList<>();

        pitScores.set(0, this.board.getPitScores().get(0).toString());
        pitScores.set(1, this.board.getPitScores().get(1).toString());

        return pitScores;
    }

    public boolean moveAMBO(int playerPick, boolean iteration, int AMBO, boolean player) {
        int i = 0;
        int tempAMBO;
        int iterationInt = 0;
        tempAMBO = AMBO;

        if (!iteration) {
            tempAMBO = this.board.getAmboScores().get(playerPick);
            iterationInt = 1;
            this.board.getAmboScores().set(playerPick, 0);
        }

        // Player1 + recursive
        if (playerPick >= 6 && playerPick <= 13) {
            if (tempAMBO > 0) {
                for (i = playerPick+iterationInt; i < this.board.getAmboScores().size(); i++) {
                    if (tempAMBO == 0) {break;}
                    this.board.getAmboScores().set(i, 1 + this.board.getAmboScores().get(i));
                    tempAMBO--;
                }
                i--;

                Log.i(TAG, "AMBO: " + tempAMBO + " i: " + i);
                // If player1 put the last ball in a empty ambo, both the remaining ball and the opponents straight ambo.
                if (player && tempAMBO == 0 && board.getAmboScores().get(i) == 1) {
                    board.getPitScores().set(0, board.getAmboScores().get(i) + board.getPitScores().get(0) + board.getAmboScores().get(i-6));
                    board.getAmboScores().set(i, 0);
                    board.getAmboScores().set(i-6, 0);
                    return false;
                }

                // Recursive if anything is left in tempAMBO
                if (tempAMBO > 0) {
                    if (player) {
                        // If player1 put the last ball in his pit -> extra turn
                        if (tempAMBO == 1) {
                            this.board.getPitScores().set(0, 1 + this.board.getPitScores().get(0));
                            return true;
                        }
                        this.board.getPitScores().set(0, 1 + this.board.getPitScores().get(0));
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
                    this.board.getAmboScores().set(i, 1 + this.board.getAmboScores().get(i));
                    tempAMBO--;
                }
                i++;

                Log.i(TAG, "AMBO: " + tempAMBO + " i: " + i);
                // If player2 put the last ball in a empty ambo, both the remaining ball and the opponents straight ambo.
                if (!player && tempAMBO == 0 && board.getAmboScores().get(i) == 1) {
                    board.getPitScores().set(1, board.getAmboScores().get(i) + board.getPitScores().get(1) + board.getAmboScores().get(i+6));
                    board.getAmboScores().set(i, 0);
                    board.getAmboScores().set(i+6, 0);
                    return true;
                }

                // Recursive if anything is left in tempAMBO
                if (tempAMBO > 0) {
                    if (!player) {
                        // If player1 put the last ball in his pit -> extra turn
                        if (tempAMBO == 1) {
                            this.board.getPitScores().set(1, 1+this.board.getPitScores().get(1));
                            return false;
                        }
                        this.board.getPitScores().set(1, 1+this.board.getPitScores().get(1));
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
        Log.i(TAG, board.getAmboScores().toString());
        for (int i : board.getAmboScores()) {
            count += i;

        }
            count += board.getPitScores().get(0);
            count += board.getPitScores().get(1);
            return count;
    }

    public Board getBoard() {
        return board;
    }
}
