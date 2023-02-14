package com.gytmy.labyrinth;

import com.gytmy.utils.Vector2;

public class PlayerImplementation implements Player {
    private Vector2 coordinates;

    public PlayerImplementation(Vector2 coordinates) {
        this.coordinates = coordinates.copy();
    }

    public PlayerImplementation(int x, int y) {
        this.coordinates = new Vector2(x, y);
    }

    @Override
    public int getX() {
        return coordinates.getX();
    }

    @Override
    public int getY() {
        return coordinates.getY();
    }

    @Override
    public Vector2 getCoordinates() {
        return coordinates.copy();
    }

    @Override
    public void setX(int x) {
        coordinates.setX(x);
    }

    @Override
    public void setY(int y) {
        coordinates.setY(y);
    }

    @Override
    public void setCoordinates(Vector2 coordinates) {
        this.coordinates = coordinates.copy();
    }

    @Override
    public void move(Direction direction)
            throws IllegalArgumentException {
        switch (direction) {
            case LEFT:
            case RIGHT:
                int newHorizontalPosition = getX() + direction.getStep();
                setX(newHorizontalPosition);
                break;
            case UP:
            case DOWN:
                int newVerticalPosition = getY() + direction.getStep();
                setY(newVerticalPosition);
                break;
            default:
                throw new IllegalArgumentException("Direction " + direction + " is not supported");
        }
    }

}
