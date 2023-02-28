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

    public ClassicScoreInfo(LabyrinthModel model, Player player, int timePassed) {
        this(model.getMinimumPathLength(), player, timePassed);
    }

    public ClassicScoreInfo(int minMovements, Player player, int timePassed) {
        this(minMovements, player.getNumberOfMovements(), timePassed);
    }

    public ClassicScoreInfo(int minMovements, int timePassed, int movements) {
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
