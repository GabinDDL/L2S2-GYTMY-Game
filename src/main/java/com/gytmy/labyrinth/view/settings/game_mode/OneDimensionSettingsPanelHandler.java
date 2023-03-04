package com.gytmy.labyrinth.view.settings.game_mode;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gytmy.labyrinth.model.LabyrinthModelFactory;

//TODO: Document this class
/**
 * This class is a singleton. It is used to create the settings panel for the
 * one-dimensional game mode.
 */
class OneDimensionSettingsPanelHandler implements GameModeSettingsPanelHandler {

    private JPanel settingsPanel;

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

        widthInputField = DefaultHandlerComponentBuilder.buildInputField(
                LabyrinthModelFactory.MINIMUM_WIDTH_1D, LabyrinthModelFactory.MAXIMUM_SIZE);
        widthLabel = DefaultHandlerComponentBuilder.buildLabel("Width: ");
    }

    @Override
    public void initPanel(JPanel settingsPanel) {
        this.settingsPanel = settingsPanel;
        settingsPanel.setLayout(new GridBagLayout());

        initWidthLabel();
        initWidthTextField();

        updateGUI(settingsPanel);
    }

    private void initWidthLabel() {
        Insets insets = new Insets(50, 50, 50, 0);

        GridBagConstraints gbc = DefaultHandlerComponentBuilder.buildGridBagConstraints(0, 0, insets);
        settingsPanel.add(widthLabel, gbc);
    }

    private void initWidthTextField() {
        Insets insets = new Insets(50, 0, 50, 50);
        GridBagConstraints gbc = DefaultHandlerComponentBuilder.buildGridBagConstraints(1, 0, insets);
        gbc.weightx = 0.7;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;

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