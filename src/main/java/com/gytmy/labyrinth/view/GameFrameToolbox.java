package com.gytmy.labyrinth.view;

import javax.swing.JFrame;

import com.gytmy.labyrinth.view.settings.SettingsMenu;

public class GameFrameToolbox {

    public static final String GAME_TITLE = "Be AMazed";

    private static JFrame mainFrame;

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(JFrame mainFrame) {
        GameFrameToolbox.mainFrame = mainFrame;
    }

    private GameFrameToolbox() {
    }

    public static void frameUpdate(String subTitle) {
        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.setTitle(GAME_TITLE + "\t(" + subTitle + ")");
    }

    public static void goToStartMenu() {
        mainFrame.setContentPane(new StartMenu(mainFrame));
        mainFrame.setSize(800, 500);
        mainFrame.revalidate();
        mainFrame.setTitle(GAME_TITLE + "\t( Menu )");
    }

    public static void goToSettingsMenu() {
        mainFrame.setContentPane(SettingsMenu.getInstance());
        mainFrame.setSize(800, 500);
        mainFrame.revalidate();
        GameFrameToolbox.frameUpdate("SettingsMenu");
    }

}
