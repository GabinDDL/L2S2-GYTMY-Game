package com.gytmy.maze.view.settings.gamemode;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.gytmy.maze.model.gamemode.BlackoutGameData;
import com.gytmy.maze.model.gamemode.GameModeData;
import com.gytmy.maze.model.gamemode.BlackoutGameData.Difficulty;

public class BlackoutPanelHandler implements PanelHandler {

    private JComboBox<Difficulty> difficultyComboBox;

    private static BlackoutPanelHandler instance = null;

    public static BlackoutPanelHandler getInstance() {
        if (instance == null) {
            instance = new BlackoutPanelHandler();
        }
        return instance;
    }

    private BlackoutPanelHandler() {
        initComponents();
    }

    private void initComponents() {
        difficultyComboBox = new JComboBox<>(Difficulty.values());
        difficultyComboBox.setSelectedItem(Difficulty.EASY);
        difficultyComboBox.setBackground(BACKGROUND_COLOR);
        difficultyComboBox.setForeground(FOREGROUND_COLOR);
        difficultyComboBox.setFont(SelectionPanel.COMBO_BOX_FONT);
    }

    @Override
    public void initPanel(JPanel settingsPanel) {
        GridBagConstraints gbc = DefaultComponentBuilder.buildGridBagConstraints(0, 0,
                new Insets(20, 20, 20, 0));
        settingsPanel.add(difficultyComboBox, gbc);
    }

    @Override
    public void cleanPanel(JPanel settingsPanel) {
        settingsPanel.remove(difficultyComboBox);
    }

    @Override
    public GameModeData getSettingsData() {
        return new BlackoutGameData((Difficulty) difficultyComboBox.getSelectedItem());
    }

}
