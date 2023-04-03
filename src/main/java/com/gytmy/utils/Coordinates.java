package com.gytmy.utils;

import java.util.Objects;

public class Coordinates {

    public static final int UNINITIALIZED_COORDINATE = -1;
    public static final Coordinates UNINITIALIZED_COORDINATES = new Coordinates(
            Coordinates.UNINITIALIZED_COORDINATE,
            Coordinates.UNINITIALIZED_COORDINATE);

    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
        this(UNINITIALIZED_COORDINATE, UNINITIALIZED_COORDINATE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return A copy of these coordinates
     */
    public Coordinates copy() {
        return new Coordinates(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinates other = (Coordinates) obj;

        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
