package com.gytmy.maze.model.score;

import com.gytmy.maze.model.MazeModel;
import com.gytmy.maze.model.player.Player;

public class SimpleKeyboardScoreInfo implements ScoreInfo {

    private int minMovements;
    private int timePassed;
    private int movements;

    public SimpleKeyboardScoreInfo(int minMovements) {
        this(minMovements, 0, 0);
    }

    public SimpleKeyboardScoreInfo(MazeModel model, Player player) {
        this(model.getMinimumPathLength(), player);
    }

    public SimpleKeyboardScoreInfo(int minMovements, Player player) {
        this(minMovements, player.getNumberOfMovements(), player.getTimePassedInSeconds());
    }

    public SimpleKeyboardScoreInfo(int minMovements, int movements, int timePassed) {
        this.minMovements = minMovements;
        this.timePassed = timePassed;
        this.movements = movements;
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
