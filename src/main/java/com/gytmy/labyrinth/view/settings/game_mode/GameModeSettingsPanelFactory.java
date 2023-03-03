package com.gytmy.labyrinth.view.settings.game_mode;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gytmy.labyrinth.model.LabyrinthModelFactory;
import com.gytmy.utils.input.UserInputFieldNumberInBounds;

public class GameModeSettingsPanelFactory {
    private GameModeSettingsPanelFactory() {
    }

    public static GameModeSettingsPanelHandler getGameModeSettingsPanel(GameMode gameMode) {
        switch (gameMode) {
            case CLASSIC:
                return ClassicSettingsPanelHandler.getInstance();
            default:
                throw new IllegalArgumentException("Game mode not supported");
        }
    }

    /**
     * This class is a singleton. It is used to create the settings panel for the
     * classic game mode.
     */
    private static class ClassicSettingsPanelHandler implements GameModeSettingsPanelHandler {

        private JTextField widthInputField;
        private JLabel widthLabel;
        private JTextField heightInputField;
        private JLabel heightLabel;

        private static final Color BACKGROUND_COLOR = GameModeSelectionPanel.BACKGROUND_COLOR;
        private static final Color FOREGROUND_COLOR = GameModeSelectionPanel.FOREGROUND_COLOR;

        private static ClassicSettingsPanelHandler instance = null;

        private ClassicSettingsPanelHandler() {
            initComponents();

        }

        private void initComponents() {

            widthInputField = new UserInputFieldNumberInBounds(LabyrinthModelFactory.MINIMUM_WIDTH_2D,
                    LabyrinthModelFactory.MAXIMUM_SIZE).getTextField();
            widthInputField.setBackground(BACKGROUND_COLOR);
            widthInputField.setForeground(FOREGROUND_COLOR);

            widthLabel = new JLabel("Width: ");
            widthLabel.setBackground(BACKGROUND_COLOR);
            widthLabel.setForeground(FOREGROUND_COLOR);

            heightInputField = new UserInputFieldNumberInBounds(
                    LabyrinthModelFactory.MINIMUM_WIDTH_2D,
                    LabyrinthModelFactory.MAXIMUM_SIZE).getTextField();
            heightInputField.setBackground(BACKGROUND_COLOR);
            heightInputField.setForeground(FOREGROUND_COLOR);

            heightLabel = new JLabel("Height: ");
            heightLabel.setBackground(BACKGROUND_COLOR);
            heightLabel.setForeground(FOREGROUND_COLOR);
        }

        public static ClassicSettingsPanelHandler getInstance() {
            if (instance == null) {
                instance = new ClassicSettingsPanelHandler();
            }
            return instance;
        }

        @Override
        public void initPanel(JPanel settingsPanel) {
            settingsPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            initWidthLabel(settingsPanel, gbc);
            initWidthTextField(settingsPanel, gbc);
            initHeightLabel(settingsPanel, gbc);
            initHeightTextField(settingsPanel, gbc);

            updateGUI(settingsPanel);
        }

        private void initWidthLabel(JPanel settingsPanel, GridBagConstraints gbc) {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            settingsPanel.add(widthLabel, gbc);
        }

        private void initWidthTextField(JPanel settingsPanel, GridBagConstraints gbc) {
            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.weighty = 0.5;
            settingsPanel.add(widthInputField, gbc);
        }

        private void initHeightLabel(JPanel settingsPanel, GridBagConstraints gbc) {
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0.3;
            gbc.weighty = 0.5;
            settingsPanel.add(heightLabel, gbc);
        }

        private void initHeightTextField(JPanel settingsPanel, GridBagConstraints gbc) {
            gbc.gridx = 1;
            gbc.weightx = 0.7;
            settingsPanel.add(heightInputField, gbc);
        }

        @Override
        public void cleanPanel(JPanel settingsPanel) {
            settingsPanel.remove(widthLabel);
            settingsPanel.remove(widthInputField);
            settingsPanel.remove(heightLabel);
            settingsPanel.remove(heightInputField);

            updateGUI(settingsPanel);
        }

        @Override
        public SettingsData getSettingsData() {
            return new ClassicSettingsData(Integer.parseInt(widthInputField.getText()),
                    Integer.parseInt(heightInputField.getText()));
        }
    }

}
