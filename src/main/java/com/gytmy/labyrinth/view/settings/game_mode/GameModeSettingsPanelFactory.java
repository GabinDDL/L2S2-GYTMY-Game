package com.gytmy.labyrinth.view.settings.game_mode;

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
                return new ClassicGameModeSettingsPanelHandler();
            default:
                throw new IllegalArgumentException("Game mode not supported");
        }
    }

    private static class ClassicGameModeSettingsPanelHandler implements GameModeSettingsPanelHandler {

        private static JTextField widthInputField = new UserInputFieldNumberInBounds(
                LabyrinthModelFactory.MINIMUM_WIDTH_2D,
                LabyrinthModelFactory.MAXIMUM_SIZE).getTextField();
        private static JLabel widthLabel = new JLabel("Width: ");
        private static JTextField heightInputField = new UserInputFieldNumberInBounds(
                LabyrinthModelFactory.MINIMUM_WIDTH_2D,
                LabyrinthModelFactory.MAXIMUM_SIZE).getTextField();
        private static JLabel heightLabel = new JLabel("Height: ");

        public ClassicGameModeSettingsPanelHandler() {
            // Empty constructor as the fields are static
        }

        @Override
        public void initPanel(JPanel settingsPanel) {
            settingsPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            settingsPanel.add(widthLabel, gbc);
            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.weighty = 0.5;
            settingsPanel.add(widthInputField, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0.3;
            gbc.weighty = 0.5;
            settingsPanel.add(heightLabel, gbc);
            gbc.gridx = 1;
            gbc.weightx = 0.7;
            settingsPanel.add(heightInputField, gbc);

            updateGUI(settingsPanel);
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
