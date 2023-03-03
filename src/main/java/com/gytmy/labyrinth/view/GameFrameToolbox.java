package com.gytmy.labyrinth.view;

import javax.swing.JFrame;

import com.gytmy.labyrinth.view.settings.SettingsMenu;

public class GameFrameToolbox {

    public static final String GAME_TITLE = "Be AMazed";

    private GameFrameToolbox() {
    }

    public static void frameUpdate(JFrame frame, String subTitle) {
        frame.pack();
        frame.revalidate();
        frame.setTitle(GAME_TITLE + "\t(" + subTitle + ")");
    }

    public static void goToStartMenu(JFrame frame) {
        frame.setContentPane(new StartMenu(frame));
        frame.setSize(800, 500);
        frame.revalidate();
        frame.setTitle(GAME_TITLE + "\t( Menu )");
    }

    public static void goToSettingsMenu(JFrame frame) {
        frame.setContentPane(new SettingsMenu(frame));
        GameFrameToolbox.frameUpdate(frame, "SettingsMenu");
    }

}
