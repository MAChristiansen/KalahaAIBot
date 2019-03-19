package com.snm.dk.kalahaaibot.control;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class GameControl {

    private final String TAG = "GameControl";
    private BoardControl boardControl;
    private boolean player;

    public GameControl() {
        boardControl = ControlReg.getBoardControl();
        this.player = true;
    }

    public GameControl takeTurn(int playerPick) {
        // Player1
        if (this.player && playerPick > 5) {
            this.player = boardControl.moveAMBO(playerPick, false, 0, this.player);
        }
        // Player2
        else if (!this.player && playerPick <= 5) {
            //this.player = boardControl.moveAMBO(playerPick, false, 0, this.player);
            ControlReg.getAIControl().calculateStates(ControlReg.getBoardControl().getCurrentBoard());
        }
        return this;
    }

    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        boardControl.updateBoard(buttons, textViews);

        if (isGameDone(boardControl.getCurrentBoard().getAmboScores())) {
            if (whoWon(boardControl.getCurrentBoard().getPitScores())) {
                Log.i(TAG, "updateBoard: Game Is Won By: Player 1");
            } else {
                Log.i(TAG, "updateBoard: Game Is Won By: Player 2");
            }

        }
        if (this.player) {
            textViews.get(2).setText("Player 1 turn: Total " + boardControl.getCount());
        }
        else {
            textViews.get(2).setText("Player 2 turn: Total " +boardControl.getCount());
        }
    }

    private boolean isGameDone(List<Integer> board) {

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

        Log.i(TAG, "isGameDone: player1Score: " + player1AmboScore + " Player2Score: " + player2AmboScore);

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
}
