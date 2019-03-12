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
    private List<Integer> playerPits;

    public BoardControl() {
        this.playerAMBO = new ArrayList<>();
        for (int i = 0; i < this.ROW_LENGTH; i++) { this.playerAMBO.add(BALLS_PER_AMBO); }

        this.playerPits = new ArrayList<>();
        this.playerPits.add(0);
        this.playerPits.add(0);
    }

    public void updateBoard(List<Button> buttons, List<TextView> textViews) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(this.playerAMBO.get(i).toString());
        }
        // Player1
        textViews.get(0).setText(this.playerPits.get(0).toString());
        // Player2
        textViews.get(1).setText(this.playerPits.get(1).toString());
    }

    public boolean moveAMBO(int playerPick, boolean iteration, int AMBO, boolean player) {
        int i = 0;
        int tempAMBO;
        int iterationInt = 0;
        int iterationInt2 = 0;
        tempAMBO = AMBO;

        if (!iteration) {
            tempAMBO = this.playerAMBO.get(playerPick);
            iterationInt = 1;
            iterationInt2 = 2;
            this.playerAMBO.set(playerPick, 0);
        }

        // Player1 + recursive
        if (playerPick >= 6 && playerPick <= 13) {
            if (tempAMBO > 0) {
                for (i = playerPick+iterationInt; i < this.playerAMBO.size(); i++) {
                    if (tempAMBO == 1) { break; }
                    this.playerAMBO.set(i, 1 + this.playerAMBO.get(i));
                    tempAMBO--;
                }
                i--;

                // Recursive if anything is left in tempAMBO
                if (tempAMBO > 0) {
                    if (player) {
                        if (tempAMBO == 1) {
                            if (i != 11 && playerAMBO.get(i+1) == 0) {
                                Log.i("hello", "Vi er her!");
                            this.playerPits.set(0, tempAMBO + this.playerAMBO.get(i-6) + this.playerPits.get(0));
                            this.playerAMBO.set(i-5, 0);
                            return false;
                        }

                        this.playerPits.set(0, 1 + this.playerPits.get(0));
                        return true;

                        }
                        this.playerPits.set(0, 1 + this.playerPits.get(0));
                        tempAMBO--;
                    }
                    moveAMBO(5, true, tempAMBO, player);
                }
            }
        }

        // Player2 + recursive
        if (playerPick >= 0 && playerPick <= 5) {
            if (tempAMBO > 0) {
                for (i = playerPick-iterationInt2; i >= 0; i--) {
                    if (tempAMBO == 0) { break; }
                    this.playerAMBO.set(i, 1 + this.playerAMBO.get(i));
                    tempAMBO--;
                }
                i++;

                // Recursive if anything is left in tempAMBO
                if (tempAMBO > 0) {
                    if (!player) {
                        if (tempAMBO == 1) {
                            if (i != 0 && playerAMBO.get(i-1) == 0) {
                                this.playerPits.set(1, tempAMBO + this.playerAMBO.get(i+6) + this.playerPits.get(1));
                                this.playerAMBO.set(i+5, 0);
                                return true;
                            }

                            //End in own pit, still player 2 turn
                            this.playerPits.set(1, 1 + this.playerPits.get(1));
                            return false;

                        }
                        //Add ball to pit
                        this.playerPits.set(1, 1 + this.playerPits.get(1));
                        tempAMBO--;
                    }
                    moveAMBO(6, true, tempAMBO, player);
                }
            }
        }

        //Evaluate next player
        if (player) {return false;} else {return true;}
    }
}
