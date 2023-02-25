package com.gytmy.utils;

public class Bounds extends Coordinates {

    private final int width;
    private final int height;

    public Bounds(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isInside(int x, int y) {
        return x >= getX() && x <= getX() + width && y >= getY() && y <= getY() + height;
    }

    public boolean isInside(Coordinates coordinates) {
        return isInside(coordinates.getX(), coordinates.getY());
    }
}
