package com.gytmy.labyrinth.model.score;

import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.player.Player;

public class ClassicScoreInfo implements ScoreInfo {

    private int minMovements;
    private int timePassed;
    private int movements;

    public ClassicScoreInfo(int minMovements) {
        this(minMovements, 0, 0);
    }

    public ClassicScoreInfo(LabyrinthModel model, Player player) {
        this(model.getMinimumPathLength(), player);
    }

    public ClassicScoreInfo(int minMovements, Player player) {
        this(minMovements, player.getNumberOfMovements(), player.getTimePassedInSeconds());
    }

    public ClassicScoreInfo(int minMovements, int movements, int timePassed) {
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
