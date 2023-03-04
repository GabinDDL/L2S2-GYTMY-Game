package com.gytmy.labyrinth.view.settings.game_mode;

//TODO: Document this factory
public class GameModeSettingsPanelHandlerFactory {
    private GameModeSettingsPanelHandlerFactory() {
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
