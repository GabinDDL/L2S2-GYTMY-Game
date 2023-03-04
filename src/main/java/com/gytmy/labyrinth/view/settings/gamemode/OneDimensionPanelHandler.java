package com.gytmy.labyrinth.view.settings.gamemode;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gytmy.labyrinth.model.LabyrinthModelFactory;
import com.gytmy.labyrinth.model.gamemode.GameModeData;
import com.gytmy.labyrinth.model.gamemode.OneDimensionGameData;

/**
 * This class is a singleton. It is used to create the settings panel
 * for the OneDimension game mode.
 */
class OneDimensionPanelHandler implements PanelHandler {

    private JPanel settingsPanel;

    private JTextField widthInputField;
    private JLabel widthLabel;

    private static OneDimensionPanelHandler instance = null;

    public static OneDimensionPanelHandler getInstance() {
        if (instance == null) {
            instance = new OneDimensionPanelHandler();
        }
        return instance;
    }

    private OneDimensionPanelHandler() {
        initComponents();
    }

    private void initComponents() {

        widthInputField = DefaultComponentBuilder.buildInputField(
                LabyrinthModelFactory.MINIMUM_WIDTH_1D, LabyrinthModelFactory.MAXIMUM_SIZE);
        widthLabel = DefaultComponentBuilder.buildLabel("Width: ");
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

        GridBagConstraints gbc = DefaultComponentBuilder.buildGridBagConstraints(0, 0, insets);
        settingsPanel.add(widthLabel, gbc);
    }

    private void initWidthTextField() {
        Insets insets = new Insets(50, 0, 50, 50);
        GridBagConstraints gbc = DefaultComponentBuilder.buildGridBagConstraints(1, 0, insets);
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