package com.gytmy.labyrinth.view.settings.game_mode;

public class ClassicSettingsData implements SettingsData {

    private int width;
    private int height;

    public ClassicSettingsData(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}