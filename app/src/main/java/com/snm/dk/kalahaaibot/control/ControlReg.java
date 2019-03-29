package com.snm.dk.kalahaaibot.control;

public class ControlReg {

    private static GameControl gameControl;
    private static BoardControl boardControl;
    private static AIControl AIControl;

    public static GameControl getGameControl() {
        if (gameControl == null) gameControl = new GameControl();
        return gameControl;
    }

    public static BoardControl getBoardControl() {
        if (boardControl == null) boardControl = new BoardControl();
        return boardControl;
    }

    public static AIControl getAIControl() {
        if (AIControl == null) AIControl = new AIControl();
        return AIControl;
    }

    public static void setGameControl(GameControl gameControl) {
        ControlReg.gameControl = gameControl;
    }
}
