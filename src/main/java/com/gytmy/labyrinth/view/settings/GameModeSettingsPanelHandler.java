package com.gytmy.labyrinth.view.settings;

import javax.swing.JPanel;

public interface GameModeSettingsPanelHandler {

    public void initPanel(JPanel settingsPanel);

    public void cleanPanel(JPanel settingsPanel);

    public SettingsData getSettingsData();

    public default void updateGUI(JPanel settingsPanel) {
        settingsPanel.revalidate();
        settingsPanel.repaint();
    }

}
