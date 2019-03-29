package com.snm.dk.kalahaaibot.control;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.snm.dk.kalahaaibot.model.Board;

import java.util.List;

import static com.snm.dk.kalahaaibot.control.ControlReg.*;

public class GameControl {

    private boolean player;
    private Board gameBoard;
    public static boolean winningState = false;

    public GameControl() {
        // Init currentBoard
        this.gameBoard = getBoardControl().initBoard();
        this.player = true;
    }

    public GameControl takeTurn(int playerPick, List<Button> buttons, List<TextView> textViews) {
        if (!winningState) {
            // Player1
            if (this.player && playerPick > 5) {
                if (!winningState) {
                    this.player = getBoardControl().moveAMBO(playerPick, false, 0, this.player, this.gameBoard);
                }
            }
            // Player2
            else if (!this.player && playerPick <= 5) {
                if (!winningState) {
                    this.player = getBoardControl().moveAMBO(playerPick, false, 0, this.player, this.gameBoard);
                }
            }
            updateBoard(buttons, textViews);
        }
        winningState(buttons, textViews);
        return this;
    }

    private void winningState(List<Button> buttons, List<TextView> textViews) {

        if (getAIControl().AIWonBasedOnHeuristic(getAIControl().getTree()) || getAIControl().HumanWonBasedOnHeuristic(getAIControl().getTree())) {
            if (getAIControl().HumanWonBasedOnHeuristic(getAIControl().getTree())) {
                Log.i("Winner", "winningState: " + " Player should win");
                textViews.get(2).setText("You Won over the AI!");
                winningState = true;
            } else if (getAIControl().AIWonBasedOnHeuristic(getAIControl().getTree())) {
                Log.i("Winner", "winningState: " + " AI should win");
                textViews.get(2).setText("The AI beat you!");
                winningState = true;
            }
        }

        int player1Board = 0;
        int player2Board = 0;

        for (int i = 0; i < this.gameBoard.getAmboScores().size(); i++) {
            if (this.gameBoard.getAmboScores().get(i) == 0 && i > 5) {
                player1Board++;
            } else if (this.gameBoard.getAmboScores().get(i) == 0 && i <= 5) {
                player2Board++;
            }
        }

        if (player1Board == 6 || player2Board == 6) {
            this.winningState = true;

            if (this.gameBoard.getPitScores().get(0) > this.gameBoard.getPitScores().get(1)) {
                textViews.get(2).setText("You Won over the AI!");
            } else {
                textViews.get(2).setText("The AI beat you!");
            }
        }

        if (this.winningState) {
            Log.i("Winner", "winningState: NÅET");
            for (Button b : buttons) {
                b.setClickable(false);
            }
        }

        this.winningState = false;
    }

    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        getBoardControl().updateBoard(buttons, textViews, this.gameBoard);

        if (this.player) {
            textViews.get(2).setText("It´s your turn");
        }
        else {
            textViews.get(2).setText("The AI is thinking...");
            takeTurn(getAIControl().takeAITurn(), buttons, textViews);
            updateBoard(buttons, textViews);
        }
    }

    public int getSumOfStonesInAmbos(List<Integer> board) {
        int sum = 0;

        //calculate player 1 ambo score
        for (int i = 0; i < board.size(); i++) {
            sum += board.get(i);
        }

        return sum;
    }

    public boolean isGameDone(List<Integer> board) {

        Integer player1AmboScore = 0;
        Integer player2AmboScore = 0;

        //calculate player 1 ambo score
        for (int i = board.size() / 2; i < board.size(); i++) {
            player1AmboScore += board.get(i);
        }

        //calculate player 2 ambo score
        for (int i = 0; i < (board.size() / 2); i++) {
            player2AmboScore += board.get(i);
        }

        if (player1AmboScore == 0 || player2AmboScore == 0 ) {
            return true;
        }
        return false;
    }

    public Board getGameBoard() {
        return gameBoard;
    }
}
