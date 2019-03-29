package com.snm.dk.kalahaaibot.control;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.snm.dk.kalahaaibot.model.Board;

import java.util.List;

import static com.snm.dk.kalahaaibot.control.ControlReg.*;

public class GameControl {

    private boolean player;
    private Board gameBoard;

    public GameControl() {
        // Init currentBoard
        this.gameBoard = new Board();
        getBoardControl().initBoard(this.gameBoard);

        this.player = true;
    }

    public GameControl takeTurn(int playerPick) {
        // Player1
        if (this.player && playerPick > 5) {
            this.player = getBoardControl().moveAMBO(playerPick, false, 0, this.player, this.gameBoard);
        }
        // Player2
        else if (!this.player && playerPick <= 5) {
            this.player = getBoardControl().moveAMBO(playerPick, false, 0, this.player, this.gameBoard);
        }
        return this;
    }

    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        getBoardControl().updateBoard(buttons, textViews, this.gameBoard);

        if (getAIControl().AIWonBasedOnHeuristic(getAIControl().getTree()) || getAIControl().HumanWonBasedOnHeuristic(getAIControl().getTree())) {
            if (getAIControl().HumanWonBasedOnHeuristic(getAIControl().getTree())) {
                // TODO Skriv det i UI
                textViews.get(2).setText("You Won over the AI!");
                //Log.i(TAG, "updateBoard: Game Is Won By: Player 1");
            } else if (getAIControl().AIWonBasedOnHeuristic(getAIControl().getTree())) {
                // TODO Skriv det i UI
                textViews.get(2).setText("The AI beat you!");
                //Log.i(TAG, "updateBoard: Game Is Won By: Player 2");
            }
        }
        else if (this.player) {
            textViews.get(2).setText("It´s your turn");
        }
        else {
            textViews.get(2).setText("The AI is thinking...");
            takeTurn(getAIControl().takeAITurn());
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

    private boolean whoWon(List<Integer> pits) {

        if (pits.get(0) > pits.get(1)) {
            return true;
        }
        return false;
    }

    public boolean getCurrentPlayer() {
        return player;
    }
    public Board getGameBoard() {
        return gameBoard;
    }
    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }
}
