package com.snm.dk.kalahaaibot.control;

public class ControlReg {

    private static GameControl gameControl;
    private static BoardControl boardControl;

    public static GameControl getGameControl() {
        if (gameControl == null) gameControl = new GameControl();
        return gameControl;
    }

    public static BoardControl getBoardControl() {
        if (boardControl == null) boardControl = new BoardControl();
        return boardControl;
    }

}
