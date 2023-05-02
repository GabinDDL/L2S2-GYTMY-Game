package com.gytmy.maze.model;

public enum Direction {

    UP(-1),
    DOWN(1),
    LEFT(-1),
    RIGHT(1);

    private int step;

    Direction(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }

    /**
     * @param directionName
     * @return the direction if the name of the parameter is recognized
     */
    public static Direction stringToDirection(String directionName) {
        if (directionName.equals("UP")) {
            return UP;
        }
        if (directionName.equals("DOWN")) {
            return DOWN;
        }
        if (directionName.equals("LEFT")) {
            return LEFT;
        }
        if (directionName.equals("RIGHT")) {
            return RIGHT;
        }
        return null;
    }
}
