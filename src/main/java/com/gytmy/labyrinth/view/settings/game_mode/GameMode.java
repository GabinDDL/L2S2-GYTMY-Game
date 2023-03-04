package com.gytmy.labyrinth.view.settings.game_mode;

//TODO: Document this enum
public enum GameMode {
    CLASSIC("Classic Mode"),
    ONE_DIMENSION("One-Dimensional Mode");

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
}
