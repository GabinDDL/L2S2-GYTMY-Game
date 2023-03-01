package com.gytmy.labyrinth.model.score;

public class SimpleKeyboardScoreCalculator implements ScoreCalculator {
    private SimpleKeyboardScoreInfo info;
    private int bestTime;

    private static final int MAX_SCORE = 1000;

    private static final double MOVEMENTS_FACTOR = 2.5;
    private static final double TIME_FACTOR = 2.5;

    private static final double CASE_PENALTY = 2.;
    private static final double TIME_PENALTY = 1.;

    private static final int FIRST_CHANGING_POINT = 45;
    private static final double IDEAL_MOVEMENT_TIME_UPPER = 0.15;
    private static final double IDEAL_MOVEMENT_TIME_LOWER = 0.1;

    public SimpleKeyboardScoreCalculator(SimpleKeyboardScoreInfo info) {
        if (info == null) {
            throw new IllegalArgumentException("Info must not be null");
        }
        this.info = info;
        int minMovement = info.getMinMovements();
        this.bestTime = computeBestTime(minMovement);
    }

    private int computeBestTime(int minMovement) {
        if (minMovement <= FIRST_CHANGING_POINT) {
            return (int) (IDEAL_MOVEMENT_TIME_LOWER * minMovement);
        } else {
            return (int) (IDEAL_MOVEMENT_TIME_UPPER * minMovement);
        }
    }

    @Override
    public int getScore() {
        int score = MAX_SCORE;
        int movements = info.getMovements();
        int timePassed = info.getTimePassed();
        int minMovements = info.getMinMovements();
        // TODO: remove debug
        System.out.println("--------------------");
        System.out.println("Best time: " + bestTime);
        System.out.println("Movements: " + movements);
        System.out.println("Time passed: " + timePassed);
        System.out.println("Min movements: " + minMovements);

        double timePenalty = computeTimePenalty(timePassed);
        double casePenalty = computeCasePenalty(movements, minMovements);

        System.out.println("Time penalty: " + timePenalty);
        System.out.println("Case penalty: " + casePenalty);

        double totalPenalty = (TIME_PENALTY * timePenalty + CASE_PENALTY * casePenalty) / (TIME_PENALTY + CASE_PENALTY);

        score -= totalPenalty * MAX_SCORE;
        return score;
    }

    private double computeTimePenalty(int timePassed) {
        if (timePassed <= bestTime) {
            return 0;
        }
        if (timePassed > TIME_FACTOR * bestTime) {
            return 1;
        }
        return (timePassed - bestTime) / (3. * bestTime);
    }

    private double computeCasePenalty(int movements, int minMovements) {
        if (movements <= minMovements) {
            return 0;
        }
        if (movements > MOVEMENTS_FACTOR * minMovements) {
            return 1;
        }
        return (movements - minMovements) / (MOVEMENTS_FACTOR * minMovements);
    }

    public void updateInfo(ScoreInfo info) {
        if (!(info instanceof SimpleKeyboardScoreInfo)) {
            throw new IllegalArgumentException("Info must be of type PlayerClassicScoreInfo");
        }
        this.info = (SimpleKeyboardScoreInfo) info;
    }
}
