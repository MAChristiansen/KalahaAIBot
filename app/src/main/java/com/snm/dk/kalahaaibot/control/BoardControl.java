package com.snm.dk.kalahaaibot.control;

import android.widget.Button;
import android.widget.TextView;

import com.snm.dk.kalahaaibot.model.Board;

import java.util.List;

public class BoardControl {

    public static final int ROW_LENGTH = 12;
    public static final int BALLS_PER_AMBO = 6;

    public BoardControl() {}

    /**
     * Generate a the starting board.
     * @param board
     * @return A initial board.
     */
    public Board initBoard(Board board) {
        for (int i = 0; i < ROW_LENGTH; i++) {
            board.getAmboScores().add(BALLS_PER_AMBO);
        }

        board.getPitScores().add(0);
        board.getPitScores().add(0);

        return new Board(board.getAmboScores(), board.getPitScores());
    }

    /**
     * Updates the games board, by the param: board.
     * @param buttons
     * @param textViews
     * @param board
     */
    public void updateBoard(List<Button> buttons, List<TextView> textViews, Board board) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(board.getAmboScores().get(i).toString());
        }
        // Player1
        textViews.get(0).setText(board.getPitScores().get(0).toString());
        // Player2
        textViews.get(1).setText(board.getPitScores().get(1).toString());
    }

    /**
     * Game logic. Here we make the move based on the players pick.
     * @param playerPick - witch button is pressed.
     * @param iteration - have we done this recursive.
     * @param AMBO - how many stones do we have in the hand.
     * @param player - which player is playing
     * @param board - witch board are we playing on.
     * @return - return a boolean to represent the next player.
     */
    public boolean moveAMBO(int playerPick, boolean iteration, int AMBO, boolean player, Board board) {
        int i = 0;
        int tempAMBO;
        int iterationInt = 0;
        tempAMBO = AMBO;

        if (!iteration) {
            tempAMBO = board.getAmboScores().get(playerPick);
            iterationInt = 1;
            board.getAmboScores().set(playerPick, 0);
        }

        // Player1 + recursive
        if (playerPick >= 6 && playerPick <= 13) {
            if (tempAMBO > 0) {
                for (i = playerPick+iterationInt; i < board.getAmboScores().size(); i++) {
                    if (tempAMBO == 0) {break;}
                    board.getAmboScores().set(i, 1 + board.getAmboScores().get(i));
                    tempAMBO--;
                }
                i--;

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
                            board.getPitScores().set(0, 1 + board.getPitScores().get(0));
                            return true;
                        }
                        board.getPitScores().set(0, 1 + board.getPitScores().get(0));
                        tempAMBO--;
                    }
                    moveAMBO(5, true, tempAMBO, player, board);
                }
            }
        }

        // Player2 + recursive
        if (playerPick >= 0 && playerPick <= 5) {
            if (tempAMBO > 0) {
                for (i = playerPick-iterationInt; i >= 0; i--) {
                    if (tempAMBO == 0) {break;}
                    board.getAmboScores().set(i, 1 + board.getAmboScores().get(i));
                    tempAMBO--;
                }
                i++;

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
                            board.getPitScores().set(1, 1 + board.getPitScores().get(1));
                            return false;
                        }
                        board.getPitScores().set(1, 1 + board.getPitScores().get(1));
                        tempAMBO--;
                    }
                    moveAMBO(6, true, tempAMBO, player, board);
                }
            }
        }

        //Change player
        if (player) {return  false;} else {return true;}
    }

    /**
     * A help method for counting all the stones on the board.
     * @param board
     * @return
     */
    public int getCount(Board board) {
        int count = 0;
        for (int i : board.getAmboScores()) {
            count += i;

        }
            count += board.getPitScores().get(0);
            count += board.getPitScores().get(1);
            return count;
    }

}
