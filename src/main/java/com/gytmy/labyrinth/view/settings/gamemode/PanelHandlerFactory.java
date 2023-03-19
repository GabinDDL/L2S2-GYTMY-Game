package com.gytmy.labyrinth.view.settings.gamemode;

import com.gytmy.labyrinth.model.gamemode.GameMode;

/**
 * Factory for creating panel handlers for game mode settings. It is used to
 * separate the logic of creating panels from the logic of the game mode
 * settings panel.
 */
public class PanelHandlerFactory {
    private PanelHandlerFactory() {
    }

    public static PanelHandler getGameModeSettingsPanel(GameMode gameMode) {
        switch (gameMode) {
            case CLASSIC:
                return ClassicPanelHandler.getInstance();
            case ONE_DIMENSION:
                return OneDimensionPanelHandler.getInstance();
            default:
                throw new IllegalArgumentException("Game mode not supported");
        }
    }

}
