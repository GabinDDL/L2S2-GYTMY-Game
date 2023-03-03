package com.gytmy.labyrinth.view.settings;

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