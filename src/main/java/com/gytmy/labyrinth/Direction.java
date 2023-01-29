package com.gytmy.labyrinth;

public enum Direction {

    UP(1),
    DOWN(-1),
    LEFT(-1),
    RIGHT(1);

    private int step;

    Direction(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }

}
