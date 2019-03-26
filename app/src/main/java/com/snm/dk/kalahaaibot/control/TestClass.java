package com.snm.dk.kalahaaibot.control;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class TestClass {

    private final String TAG = "TestClass";

    public class Board {
        private List<Integer> amboScores;
        private List<Integer> pitScores;

        public String getHej() {
            return hej;
        }

        public void setHej(String hej) {
            this.hej = hej;
        }

        private String hej;

        public Board(List<Integer> amboScores, List<Integer> pitScores) {
            this.amboScores = amboScores;
            this.pitScores = pitScores;
        }

        public Board(Board board) {
            this.amboScores = board.getAmboScores();
            this.pitScores = board.getPitScores();
            this.hej = board.hej;
        }

        public Board (String s) {
            this.hej = s;
        }

        public List<Integer> getAmboScores() {
            return amboScores;
        }
        public void setAmboScores(List<Integer> amboScores) {
            this.amboScores = amboScores;
        }
        public List<Integer> getPitScores() {
            return pitScores;
        }
        public void setPitScores(List<Integer> pitScores) {
            this.pitScores = pitScores;
        }

        @Override
        public String toString() {
            return "\n Board AMBO: " + this.amboScores + "\n" +
                    "Player Pit: " + this.pitScores + " | \n" +
                    this.hej;

        }
    }


    private Board currentBoard;
    private List<Integer> amboScores;
    private List<Integer> pitScores;


    public void makeBoard() {
        // Init currentBoard

        this.amboScores = new ArrayList<>();
        this.pitScores = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            this.amboScores.add(6);
        }

        this.pitScores.add(0);
        this.pitScores.add(0);

        this.currentBoard = new Board(amboScores, pitScores);
    }


    // Prøver at iterer og hele tiden holde styr på det første board state.
    private List<Board> boards = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void testForLoop() {
        makeBoard();
     /*
        List<Integer> amboScores = new ArrayList<>();
        List<Integer> pitScores = new ArrayList<>();

        List<Integer> amboScores2 = new ArrayList<>();
        List<Integer> pitScores2 = new ArrayList<>();

        amboScores.add(1);
        pitScores.add(1);

        amboScores2.add(2);
        pitScores2.add(2);

        Board test3 = new Board(amboScores, pitScores);
        Log.i(TAG, test3.toString());

        Board test4 = new Board(test3);
        Log.i(TAG, test4.toString());

        List<Integer> amboScores3 = new ArrayList<>();
        amboScores3.add(4);
        test3.setAmboScores(amboScores3);
        Log.i(TAG, test3.toString() + " | " + test4.toString());



        Board test = new Board("Hej");
        Log.i(TAG, test.toString());


        Board test2 = new Board(test);
        Log.i(TAG, test2.toString());


        test2.setHej("Hello");
        Log.i(TAG, test.hej + " | " + test2.hej);

        */

        Board newBoard = new Board(currentBoard);
        //Log.i(TAG, newBoard.toString());

        for (int i = 0; i <= 5; i++) {
            List<Integer> output = new ArrayList<>();
            for (Integer Ints : currentBoard.getAmboScores()) {
                output.add(Ints);
            }

            currentBoard.getAmboScores().set(i, 0);
            this.boards.add(new Board(this.currentBoard));
            currentBoard = new Board(output, this.pitScores);
            //Log.i(TAG, "SET BOARD: " + currentBoard);
        }

        //Log.i(TAG, this.boards + "");

    }


}
