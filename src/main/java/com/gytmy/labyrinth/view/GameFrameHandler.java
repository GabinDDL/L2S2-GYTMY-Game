package com.gytmy.labyrinth.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.view.settings.SettingsMenu;

public class GameFrameHandler {

    public static final String GAME_TITLE = "Be AMazed";

    private static JFrame mainFrame;

    private static StartMenu startMenu = StartMenu.getInstance();
    private static SettingsMenu settingsMenu;
    private static AudioMenu audioMenu;

    private static final Dimension DEFAULT_DIMENSION = new Dimension(800, 500);

    private GameFrameHandler() {
    }

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(JFrame mainFrame) {
        GameFrameHandler.mainFrame = mainFrame;

    }

    public static void initMenus() {
        settingsMenu = SettingsMenu.getInstance();
        audioMenu = AudioMenu.getInstance();
    }

    public static void frameUpdate(String subTitle) {
        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.setTitle(GAME_TITLE + "\t(" + subTitle + ")");
    }

    public static void goToStartMenu() {
        mainFrame.setContentPane(startMenu);
        mainFrame.setSize(DEFAULT_DIMENSION);
        mainFrame.revalidate();
        mainFrame.setTitle(GAME_TITLE + "\t( Menu )");
    }

    public static void goToSettingsMenu() {
        mainFrame.setContentPane(settingsMenu);
        // Ensure that the frame remains the same default size
        mainFrame.setPreferredSize(DEFAULT_DIMENSION);
        GameFrameHandler.frameUpdate("SettingsMenu");
        // Allow other components to resize the frame
        mainFrame.setPreferredSize(null);
    }

    public static void goToAudioMenu() {
        mainFrame.setContentPane(audioMenu);
        // Ensure hat the frame remains the same default size
        mainFrame.setPreferredSize(DEFAULT_DIMENSION);
        GameFrameHandler.frameUpdate("AudioMenu");
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public static void goToGameOverPanel(LabyrinthModel model) {
        mainFrame.setContentPane(new GameOverPanel(model));
        // Ensure hat the frame remains the same default size
        mainFrame.setPreferredSize(DEFAULT_DIMENSION);
        GameFrameHandler.frameUpdate("GameOverPanel");
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public static void quitGame() {
        mainFrame.dispose();
    }

}
