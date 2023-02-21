package com.gytmy.labyrinth.view;

import javax.swing.JFrame;

public class GameFrameToolbox {

    private static final String GAME_TITLE = "Be AMazed";

    private GameFrameToolbox() {
    }

    public static void frameUpdate(JFrame frame, String subTitle) {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.setTitle(GAME_TITLE + "\t(" + subTitle + ")");
    }

}
