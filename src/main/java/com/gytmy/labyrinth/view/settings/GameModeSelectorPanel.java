package com.gytmy.labyrinth.view.settings;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class GameModeSelectorPanel extends JPanel {

    private GameModeSelector gameModeSelector;
    protected JPanel gameModeSettingsPanel;

    public GameModeSelectorPanel() {
        initGameModeSelector();
        initGameModeSettingsPanel();
    }

    private void initGameModeSelector() {
        gameModeSelector = new GameModeSelector();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        add(gameModeSelector, gbc);
        revalidate();
        repaint();
    }

    private void initGameModeSettingsPanel() {
        gameModeSettingsPanel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gameModeSelector.updateGameModeSettingsPanel((GameMode) gameModeSelector.getSelectedItem());
        add(gameModeSettingsPanel, gbc);
        revalidate();
        repaint();
    }

    private class GameModeSelector extends JComboBox<GameMode> {
        GameMode lastSelectedGameMode;

        public GameModeSelector() {
            for (GameMode gameMode : GameMode.values()) {
                addItem(gameMode);
            }

            addActionListener(e -> {
                GameMode gameMode = (GameMode) getSelectedItem();
                updateGameModeSettingsPanel(gameMode);
            });
        }

        private void updateGameModeSettingsPanel(GameMode gameMode) {
            cleanOldPanel();
            lastSelectedGameMode = (GameMode) getSelectedItem();
            GameModeSettingsPanelHandler handler = GameModeSettingsPanelFactory
                    .getGameModeSettingsPanel(gameMode);
            handler.initPanel(gameModeSettingsPanel);
        }

        private void cleanOldPanel() {
            if (lastSelectedGameMode == null) {
                return;
            }

            GameModeSettingsPanelHandler handler = GameModeSettingsPanelFactory
                    .getGameModeSettingsPanel(lastSelectedGameMode);
            handler.cleanPanel(gameModeSettingsPanel);

        }

    }

    public static void main(String[] args) {
        GameModeSelectorPanel panel = new GameModeSelectorPanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.add(panel);
        frame.setVisible(true);

    }
}
