package com.gytmy.utils;

import java.util.Objects;

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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Bounds)) {
            return false;
        }
        Bounds other = (Bounds) o;
        return getX() == other.getX() && getY() == other.getY() && width == other.width && height == other.height;
    }

    public int hashCode() {
        return Objects.hash(getX(), getY(), width, height);
    }
}
