package com.gytmy.labyrinth.model.gamemode;

/**
 * Implementation of {@link GameModeData} for the one dimension game mode.
 */
public class OneDimensionGameData implements GameModeData {

    private int width;

    public OneDimensionGameData(int width) {
        this.width = width;

    }

    public int getWidth() {
        return width;
    }

}
