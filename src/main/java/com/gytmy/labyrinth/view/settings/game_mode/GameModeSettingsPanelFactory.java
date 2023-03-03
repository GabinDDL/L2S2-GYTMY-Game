package com.gytmy.labyrinth.view.settings.game_mode;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
            case ONE_DIMENSION:
                return OneDimensionSettingsPanelHandler.getInstance();
            default:
                throw new IllegalArgumentException("Game mode not supported");
        }
    }

}
