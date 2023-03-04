
package com.gytmy.labyrinth.view.settings.game_mode;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gytmy.labyrinth.model.LabyrinthModelFactory;

// TODO: Refactor this
/**
 * This class is a singleton. It is used to create the settings panel for the
 * classic game mode.
 */
class ClassicSettingsPanelHandler implements GameModeSettingsPanelHandler {

    private JPanel settingsPanel;

    private JTextField widthInputField;
    private JLabel widthLabel;
    private JTextField heightInputField;
    private JLabel heightLabel;

    private static ClassicSettingsPanelHandler instance = null;

    public static ClassicSettingsPanelHandler getInstance() {
        if (instance == null) {
            instance = new ClassicSettingsPanelHandler();
        }
        return instance;
    }

    private ClassicSettingsPanelHandler() {
        initComponents();
    }

    private void initComponents() {
        widthInputField = DefaultHandlerComponentBuilder.buildInputField(
                LabyrinthModelFactory.MINIMUM_WIDTH_2D, LabyrinthModelFactory.MAXIMUM_SIZE);
        widthLabel = DefaultHandlerComponentBuilder.buildLabel("Width: ");

        heightInputField = DefaultHandlerComponentBuilder.buildInputField(
                LabyrinthModelFactory.MINIMUM_HEIGHT_2D, LabyrinthModelFactory.MAXIMUM_SIZE);
        heightLabel = DefaultHandlerComponentBuilder.buildLabel("Height: ");
    }

    @Override
    public void initPanel(JPanel settingsPanel) {
        settingsPanel.setLayout(new GridBagLayout());
        this.settingsPanel = settingsPanel;

        initWidthLabel();
        initWidthTextField();
        initHeightLabel();
        initHeightTextField();

        updateGUI(settingsPanel);
    }

    private void initWidthLabel() {
        GridBagConstraints gbc = DefaultHandlerComponentBuilder.buildGridBagConstraints(0, 0,
                new Insets(20, 20, 20, 0));
        settingsPanel.add(widthLabel, gbc);
    }

    private void initWidthTextField() {
        GridBagConstraints gbc = DefaultHandlerComponentBuilder.buildGridBagConstraints(1, 0,
                new Insets(20, 0, 20, 20));
        gbc.weightx = 0.7;
        gbc.weighty = 0.5;
        settingsPanel.add(widthInputField, gbc);
    }

    private void initHeightLabel() {
        GridBagConstraints gbc = DefaultHandlerComponentBuilder.buildGridBagConstraints(0, 1,
                new Insets(20, 20, 20, 0));
        settingsPanel.add(heightLabel, gbc);
    }

    private void initHeightTextField() {
        GridBagConstraints gbc = DefaultHandlerComponentBuilder.buildGridBagConstraints(1, 1,
                new Insets(20, 0, 20, 20));
        gbc.weightx = 0.7;
        gbc.weighty = 0.5;
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
    public GameModeData getSettingsData() {
        return new ClassicGameModeData(Integer.parseInt(widthInputField.getText()),
                Integer.parseInt(heightInputField.getText()));
    }
}