package com.gytmy.labyrinth.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.gytmy.labyrinth.view.settings.SettingsMenu;

public class GameFrameToolbox {

    public static final String GAME_TITLE = "Be AMazed";

    private static JFrame mainFrame;

    private static StartMenu startMenu = StartMenu.getInstance();
    private static SettingsMenu settingsMenu;
    private static AudioMenu audioMenu;

    private GameFrameToolbox() {
    }

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(JFrame mainFrame) {
        GameFrameToolbox.mainFrame = mainFrame;

    }

    public static void initMenus() {
        settingsMenu = SettingsMenu.getInstance();
    }

    public static void frameUpdate(String subTitle) {
        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.setTitle(GAME_TITLE + "\t(" + subTitle + ")");
    }

    public static void goToStartMenu() {
        mainFrame.setContentPane(startMenu);
        mainFrame.setSize(800, 500);
        mainFrame.revalidate();
        mainFrame.setTitle(GAME_TITLE + "\t( Menu )");
    }

    public static void goToSettingsMenu() {
        mainFrame.setContentPane(SettingsMenu.getInstance());
        // Ensure hat the frame remains the same default size
        mainFrame.setPreferredSize(new Dimension(800, 500));
        GameFrameToolbox.frameUpdate("SettingsMenu");
        // Allow other components to resize the frame
        mainFrame.setPreferredSize(null);
    }

}
