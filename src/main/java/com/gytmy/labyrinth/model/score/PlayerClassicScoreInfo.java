package com.gytmy.labyrinth.model.score;

public class PlayerClassicScoreInfo implements PlayerScoreInfo {

    private int minMovements;
    private int timePassed;
    private int movements;

    public PlayerClassicScoreInfo(int minMovements) {
        this.minMovements = minMovements;
        this.timePassed = 0;
        this.movements = 0;
    }

    public int getMinMovements() {
        return minMovements;
    }

    public void setMinMovements(int minMovements) {
        this.minMovements = minMovements;
    }

    public int getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(int timePassed) {
        this.timePassed = timePassed;
    }

    public int getMovements() {
        return movements;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }

}
