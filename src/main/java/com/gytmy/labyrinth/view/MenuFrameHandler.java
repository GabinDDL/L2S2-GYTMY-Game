package com.gytmy.labyrinth.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gytmy.labyrinth.view.settings.SettingsMenu;

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
        changeJPanel(settingsMenu, "Setting");
    }

    public static void goToAudioMenu() {
        changeJPanel(audioMenu, "Audio");
    }

    private static void changeJPanel(JPanel panel, String subtitle) {
        mainFrame.setContentPane(panel);

        // Ensure that the frame remains the same default size
        mainFrame.setPreferredSize(DEFAULT_DIMENSION);

        setSubtitle(subtitle);
        mainFrame.pack();
        mainFrame.revalidate();
        mainFrame.repaint();

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
