package com.gytmy.labyrinth.view.settings.game_mode;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.gytmy.labyrinth.view.Cell;

//TODO: Refacctor this
public class GameModeSelectionPanel extends JPanel {

    public static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    public static final Color FOREGROUND_COLOR = Cell.PATH_COLOR;

    private GameModeSelector gameModeSelector;
    protected JPanel gameModeSettingsPanel;

    public GameModeSelectionPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new GridBagLayout());

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
        gbc.weighty = 0.3;
        add(gameModeSelector, gbc);

        revalidate();
        repaint();
    }

    private void initGameModeSettingsPanel() {
        gameModeSettingsPanel = new JPanel();
        gameModeSelector.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.7;
        gameModeSelector.updateGameModeSettingsPanel((GameMode) gameModeSelector.getSelectedItem());
        gameModeSettingsPanel.setBackground(BACKGROUND_COLOR);
        add(gameModeSettingsPanel, gbc);

        revalidate();
        repaint();
    }

    public GameModeData getGameModeData() {
        GameModeSettingsPanelHandler handler = GameModeSettingsPanelHandlerFactory
                .getGameModeSettingsPanel((GameMode) gameModeSelector.getSelectedItem());
        return handler.getSettingsData();
    }

    public GameMode getSelectedGameMode() {
        return (GameMode) gameModeSelector.getSelectedItem();
    }

    private class GameModeSelector extends JComboBox<GameMode> {
        private GameMode lastSelectedGameMode;

        private final Font FONT = new Font("Arial", Font.BOLD, 15);

        public GameModeSelector() {
            setFont(FONT);

            for (GameMode gameMode : GameMode.values()) {
                addItem(gameMode);
            }

            addActionListener(e -> {
                GameMode gameMode = (GameMode) getSelectedItem();
                updateGameModeSettingsPanel(gameMode);
            });

            setBackground(BACKGROUND_COLOR);
            setForeground(FOREGROUND_COLOR);
        }

        private void updateGameModeSettingsPanel(GameMode gameMode) {
            cleanOldPanel();
            lastSelectedGameMode = (GameMode) getSelectedItem();
            GameModeSettingsPanelHandler handler = GameModeSettingsPanelHandlerFactory
                    .getGameModeSettingsPanel(gameMode);
            handler.initPanel(gameModeSettingsPanel);
        }

        private void cleanOldPanel() {
            if (lastSelectedGameMode == null) {
                return;
            }

            GameModeSettingsPanelHandler handler = GameModeSettingsPanelHandlerFactory
                    .getGameModeSettingsPanel(lastSelectedGameMode);
            handler.cleanPanel(gameModeSettingsPanel);
        }

    }

}
