package com.gytmy.maze.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gytmy.maze.view.settings.SettingsMenu;

public class MenuFrameHandler {

    public static final String GAME_TITLE = "Be AMazed";

    private static JFrame mainFrame;

    private static StartMenu startMenu = StartMenu.getInstance();
    private static SettingsMenu settingsMenu;
    private static AudioMenu audioMenu;

    public static final Dimension DEFAULT_DIMENSION = new Dimension(800, 500);

    private MenuFrameHandler() {
    }

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(JFrame mainFrame) {
        MenuFrameHandler.mainFrame = mainFrame;

    }

    public static void initMenus() {
        settingsMenu = SettingsMenu.getInstance();
        audioMenu = AudioMenu.getInstance();
    }

    public static void goToStartMenu() {
        changeJPanel(startMenu, "Start");
    }

    public static void goToSettingsMenu() {
        settingsMenu.updateUsers();
        changeJPanel(settingsMenu, "Settings");
    }

    public static void goToAudioMenu() {
        audioMenu.handleRecreateModelsButtonState();
        changeJPanel(audioMenu, "Audio");
        audioMenu.updateGUI();
    }

    private static void changeJPanel(JPanel panel, String subtitle) {
        mainFrame.setContentPane(panel);

        // Ensure that the frame remains the same default size
        mainFrame.setPreferredSize(DEFAULT_DIMENSION);

        // Update the frame
        frameUpdate(subtitle);

        // Allow other components to resize the frame
        mainFrame.setPreferredSize(null);
    }

    public static void frameUpdate(String subTitle) {
        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.repaint();
        setSubtitle(subTitle);
    }

    public static void setSubtitle(String subtitle) {
        mainFrame.setTitle(GAME_TITLE + " - " + subtitle);
    }

    public static void quitGame() {
        mainFrame.dispose();
    }
}
