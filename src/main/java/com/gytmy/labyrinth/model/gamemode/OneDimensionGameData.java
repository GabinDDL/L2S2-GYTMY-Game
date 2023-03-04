package com.gytmy.labyrinth.model.gamemode;

//TODO: Document this class
public class OneDimensionGameData implements GameModeData {

    private int width;

    public OneDimensionGameData(int width) {
        this.width = width;

    }

    public int getWidth() {
        return width;
    }

}
