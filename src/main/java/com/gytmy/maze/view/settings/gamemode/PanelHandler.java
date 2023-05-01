package com.gytmy.maze.view.settings.gamemode;

import java.awt.Color;

import javax.swing.JPanel;

import com.gytmy.maze.model.gamemode.GameModeData;

/**
 * This interface represents a handler for a game mode settings panel.
 * It allows to initialize, clean and get the settings data of the panel.
 * The panel is passed as a parameter to the methods and the handler only
 * has to update the panel based on the game mode.
 * 
 * The handler is also responsible for updating the GUI. This is done
 * by calling the updateGUI method.
 */
public interface PanelHandler {

    public static final Color BACKGROUND_COLOR = SelectionPanel.BACKGROUND_COLOR;
    public static final Color FOREGROUND_COLOR = SelectionPanel.FOREGROUND_COLOR;

    /**
     * This method initializes the settings panel. It is called when the user
     * selects a game mode and it adds the components to the panel.
     */
    public void initPanel(JPanel settingsPanel);

    /**
     * This method cleans the settings panel. It is called when the user
     * selects another game mode and it removes the components from the panel.
     */
    public void cleanPanel(JPanel settingsPanel);

    /**
     * This method returns the settings data of the panel.
     */
    public GameModeData getSettingsData();

    public default void updateGUI(JPanel settingsPanel) {
        settingsPanel.revalidate();
        settingsPanel.repaint();
    }

}
