package com.gytmy.labyrinth;

public class PlayerImplementation implements Player {
    private int x, y;

    public PlayerImplementation(int[] coordinates) {
        this(coordinates[0], coordinates[1]);
    }

    public PlayerImplementation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int[] getCoordinates() {
        return new int[] { x, y };
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setCoordinates(int[] coordinates) {
        this.x = coordinates[0];
        this.y = coordinates[1];
    }

    @Override
    public void move(Direction direction)
            throws IllegalArgumentException {
        switch (direction) {
            case LEFT:
            case RIGHT:
                int newHorizontalPosition = x + direction.getStep();
                setX(newHorizontalPosition);
                break;
            case UP:
            case DOWN:
                int newVerticalPosition = y + direction.getStep();
                setY(newVerticalPosition);
                break;
            default:
                throw new IllegalArgumentException("Direction " + direction + " is not supported");
        }
    }

}
