package com.gytmy.labyrinth.model.score;

public class ClassicScoreCalculator implements ScoreCalculator {
    private ClassicScoreInfo info;
    private int bestTime;

    private static final int MAX_SCORE = 1000;
    private static final int MOVEMENTS_PENALTY = 20;
    private static final int TIME_PENALTY = 10;
    private static final int FIRST_CHANGING_POINT = 45;
    private static final double IDEAL_MOVEMENT_TIME_UPPER = 0.15;
    private static final double IDEAL_MOVEMENT_TIME_LOWER = 0.1;

    public ClassicScoreCalculator(ClassicScoreInfo info) {
        if (info == null) {
            throw new IllegalArgumentException("Info must not be null");
        }
        this.info = info;
        int minMovement = info.getMinMovements();
        if (minMovement <= FIRST_CHANGING_POINT) {
            this.bestTime = (int) (IDEAL_MOVEMENT_TIME_LOWER * minMovement);
        } else {
            this.bestTime = (int) (IDEAL_MOVEMENT_TIME_UPPER * minMovement);
        }
    }

    @Override
    public int getScore() {
        int score = MAX_SCORE;
        int movements = info.getMovements();
        int timePassed = info.getTimePassed();
        int minMovements = info.getMinMovements();
        // TODO: remove debug
        System.out.println("Best time: " + bestTime);
        System.out.println("Movements: " + movements);
        System.out.println("Time passed: " + timePassed);
        System.out.println("Min movements: " + minMovements);
        if (movements > minMovements) {
            score -= (movements - minMovements) * MOVEMENTS_PENALTY;
        }
        if (timePassed > bestTime) {
            score -= (timePassed - bestTime) * TIME_PENALTY;
        }
        score = Math.max(score, 0);
        return score;
    }

    public void updateInfo(ScoreInfo info) {
        if (!(info instanceof ClassicScoreInfo)) {
            throw new IllegalArgumentException("Info must be of type PlayerClassicScoreInfo");
        }
        this.info = (ClassicScoreInfo) info;
    }
}
