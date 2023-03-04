package com.gytmy.labyrinth.view.settings.game_mode;

import java.awt.Color;

import javax.swing.JPanel;

//TODO: Document this interface
public interface GameModeSettingsPanelHandler {

    public static final Color BACKGROUND_COLOR = GameModeSelectionPanel.BACKGROUND_COLOR;
    public static final Color FOREGROUND_COLOR = GameModeSelectionPanel.FOREGROUND_COLOR;

    public void initPanel(JPanel settingsPanel);

    public void cleanPanel(JPanel settingsPanel);

    public GameModeData getSettingsData();

    public default void updateGUI(JPanel settingsPanel) {
        settingsPanel.revalidate();
        settingsPanel.repaint();
    }

}
