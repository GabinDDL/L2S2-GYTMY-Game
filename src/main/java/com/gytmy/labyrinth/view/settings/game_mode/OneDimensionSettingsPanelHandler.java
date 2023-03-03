package com.gytmy.labyrinth.view.settings.game_mode;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gytmy.labyrinth.model.LabyrinthModelFactory;
import com.gytmy.utils.input.UserInputFieldNumberInBounds;

/**
 * This class is a singleton. It is used to create the settings panel for the
 * one-dimensional game mode.
 */
class OneDimensionSettingsPanelHandler implements GameModeSettingsPanelHandler {

    private JTextField widthInputField;
    private JLabel widthLabel;

    private static OneDimensionSettingsPanelHandler instance = null;

    public static OneDimensionSettingsPanelHandler getInstance() {
        if (instance == null) {
            instance = new OneDimensionSettingsPanelHandler();
        }
        return instance;
    }

    private OneDimensionSettingsPanelHandler() {
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
    }

    @Override
    public void initPanel(JPanel settingsPanel) {
        settingsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        initWidthLabel(settingsPanel, gbc);
        initWidthTextField(settingsPanel, gbc);

        updateGUI(settingsPanel);
    }

    private void initWidthLabel(JPanel settingsPanel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 50, 50, 50);
        gbc.fill = GridBagConstraints.BOTH;
        settingsPanel.add(widthLabel, gbc);
    }

    private void initWidthTextField(JPanel settingsPanel, GridBagConstraints gbc) {
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.5;
        settingsPanel.add(widthInputField, gbc);
    }

    @Override
    public void cleanPanel(JPanel settingsPanel) {
        settingsPanel.remove(widthLabel);
        settingsPanel.remove(widthInputField);

        updateGUI(settingsPanel);
    }

    @Override
    public GameModeData getSettingsData() {
        return new OneDimensionGameData(Integer.parseInt(widthInputField.getText()));
    }

}