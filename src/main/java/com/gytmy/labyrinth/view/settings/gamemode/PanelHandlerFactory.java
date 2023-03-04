package com.gytmy.labyrinth.view.settings.gamemode;

import com.gytmy.labyrinth.model.gamemode.GameMode;

//TODO: Document this factory
public class PanelHandlerFactory {
    private PanelHandlerFactory() {
    }

    public static PanelHandler getGameModeSettingsPanel(GameMode gameMode) {
        switch (gameMode) {
            case CLASSIC:
                return ClassicPanelHandler.getInstance();
            case ONE_DIMENSION:
                return OneDimensionanelHandler.getInstance();
            default:
                throw new IllegalArgumentException("Game mode not supported");
        }
    }

}
