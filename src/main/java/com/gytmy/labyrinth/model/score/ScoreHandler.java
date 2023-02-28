package com.gytmy.labyrinth.model.score;

public class ScoreHandler {
    private int minMovements;
    private int timePassed;
    private int movements;
    private int bestTime;

    private static final int MAX_SCORE = 1000;
    private static final int MOVEMENTS_PENALTY = 20;
    private static final int TIME_PENALTY = 10;
    private static final double IDEAL_MOVEMENT_TIME = 0.2;

    public ScoreHandler(int minMovements) {
        this.minMovements = minMovements;
        this.timePassed = 0;
        this.movements = 0;
        this.bestTime = (int) (minMovements * IDEAL_MOVEMENT_TIME);
    }

    public void updateTimePassed(int timePassed) {
        this.timePassed = timePassed;
    }

    public void incrementMovements() {
        this.movements++;
    }

    public int calculateScore() {
        int score = MAX_SCORE;
        if (movements > minMovements) {
            score -= (movements - minMovements) * MOVEMENTS_PENALTY;
        }
        if (timePassed > bestTime) {
            score -= (timePassed - bestTime) * TIME_PENALTY;
        }
        score = Math.max(score, 0);
        return score;
    }
}
