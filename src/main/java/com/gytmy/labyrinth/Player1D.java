package com.gytmy.labyrinth;

public class Player1D implements Player {
    int position;

    public Player1D(int position) {
        this.position = position;
    }

    public Player1D() {
        this(0);
    }

    @Override
    public int[] getCoordinates() {
        return new int[] { position };
    }

    @Override
    public void move(Direction direction) {
        switch (direction) {
            case LEFT:
                position--;
                break;
            case RIGHT:
                position++;
                break;
            default:
                throw new IllegalArgumentException("Direction " + direction + " is not supported");
        }
    }

}
