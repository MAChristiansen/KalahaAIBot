package com.snm.dk.kalahaaibot.control;

public class ControlReg {

    private static GameControl gameControl;

    public static GameControl getGameControl() {
        if (gameControl == null) gameControl = new GameControl();
        return gameControl;
    }

}
