package com.gytmy.maze.model.gamemode;

/**
 * This enum represents the game modes. It is used to select the game mode
 * and to get the settings data of the game mode. It also allows to get the
 * display name of the game mode.
 * 
 * In order to add a new game mode, you need to follow these steps:
 * <ol>
 * <li>Add a new enum value</li>
 * <li>Add a new {@code PanelHandler} implementation</li>
 * <li>Add a new {@code PanelHandler} instance to the
 * {@code PanelHandlerFactory}</li>
 * <li>Add a new {@code GameModeData} implementation</li>
 * <li>Add a new {@code MazeModel} creation method on the switch case
 * inside {@code MazeModelFactory}
 * </li>
 * </ol>
 * 
 * @see com.gytmy.maze.view.settings.gamemode.PanelHandler
 * @see com.gytmy.maze.view.settings.gamemode.PanelHandlerFactory
 * @see com.gytmy.maze.model.gamemode.GameModeData
 * @see com.gytmy.maze.model.MazeModelFactory
 */
public enum GameMode {
    CLASSIC("Classic Mode"),
    ONE_DIMENSION("One-Dimensional Mode"),
    BLACKOUT("Blackout Mode");

    private final String displayName;

    private GameMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getMaxNumberOfPlayers() {
        switch (this) {
            case CLASSIC:
            case ONE_DIMENSION:
                return 5;
            case BLACKOUT:
                return 1;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
