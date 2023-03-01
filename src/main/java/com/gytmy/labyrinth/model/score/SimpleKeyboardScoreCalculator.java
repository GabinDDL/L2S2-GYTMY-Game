package com.gytmy.labyrinth.model.score;

/**
 * This calculator is used to compute the score of a player in a simple keyboard
 * game.
 * 
 * The score take into account the time passed and the number of
 * movements. The idea behind the score is that the player should be able to do
 * the game in the minimum number of movements in the minimum time.
 * 
 * The minimum considers only the distance to the exit cell and it
 * should represent the minimum possible time to do the game. It is the distance
 * multiplied by the ideal time to do a movement. This can be adapted to the way
 * the movement is controlled.
 * 
 * To do so, the score is computed
 * as follow:
 * <ul>
 * <li>First, the score is computed as the maximum score minus a penalty. The
 * penalty is computed as the sum of the
 * time penalty and the movement penalty.</li>
 * 
 * <li>The time penalty is computed as follow:
 * <ul>
 * <li>If the time passed is less or equal than the best time, the time penalty
 * is 0.</li>
 * <li>If the time passed is greater than the best time multiplied by a factor
 * ({@code TIME_FACTOR}), the time penalty is 1.</li>
 * <li>Otherwise, the time penalty is computed as the ratio between the time
 * passed and the best time multiplied by factor {@code TIME_FACTOR}.</li>
 * </ul>
 * </li>
 * 
 * <li>The movement penalty is computed as follow:
 * <ul>
 * <li>If the number of movements is less or equal than the minimum number of
 * movements,
 * the movement penalty is 0.</li>
 * <li>If the number of movements is greater than the minimum number of
 * movements multiplied by a factor ({@code MOVEMENT_PENALTY}), the movement
 * penalty is 1.</li>
 * <li>Otherwise, the movement penalty is computed as the ratio between the
 * number of movements and the minimum number of movements multiplied by
 * {@code MOVEMENT_PENALTY}.</li>
 * </ul>
 * </li>
 * 
 * <li>At the end, the score is computed as the maximum score minus the
 * penalty (between 0 and 1) multiplied by the maximum score. The score is
 * therefore between 0 and 1000.</li>
 * </ul>
 * 
 * 
 */
public class SimpleKeyboardScoreCalculator implements ScoreCalculator {
    private SimpleKeyboardScoreInfo info;
    private int bestTime;

    private static final int MAX_SCORE = 1000;

    // The factor to compute the movement penalty
    private static final double MOVEMENTS_FACTOR = 2.5;
    // The factor to compute the time penalty
    private static final double TIME_FACTOR = 2.5;

    private static final double MOVEMENT_PENALTY = 2.;
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

    /**
     * Compute the best time to do the game. The best time is the minimum
     * number of movements multiplied by the ideal time to do a movement.
     * 
     * The ideal time to do a movement is different depending on the number of
     * movements. If the number of movements is less than a certain value, the ideal
     * time is a certain value. Otherwise, it is another value. This values are
     * defined in the constants {@code IDEAL_MOVEMENT_TIME_LOWER}} and
     * {@code IDEAL_MOVEMENT_TIME_UPPER}.
     * 
     * @param minMovement the minimum number of movements
     * @return the best time to do the game
     */
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
        double movementPenalty = computeMovementPenalty(movements, minMovements);

        System.out.println("Time penalty: " + timePenalty);
        System.out.println("Movement penalty: " + movementPenalty);

        double totalPenalty = (TIME_PENALTY * timePenalty + MOVEMENT_PENALTY * movementPenalty)
                / (TIME_PENALTY + MOVEMENT_PENALTY);

        score -= totalPenalty * MAX_SCORE;
        return score;
    }

    private double computeTimePenalty(int timePassed) {
        double upperBound = TIME_FACTOR * bestTime;
        return computeTimePenalty(timePassed - bestTime, upperBound);
    }

    private double computeMovementPenalty(int movements, int minMovements) {
        double upperBound = MOVEMENTS_FACTOR * minMovements;
        return computeTimePenalty(movements - minMovements, upperBound);
    }

    private double computeTimePenalty(int value, double upperBound) {
        if (value <= 0) {
            return 0;
        }
        if (value > upperBound) {
            return 1;
        }

        return (value - upperBound) / upperBound;
    }

    public void updateInfo(ScoreInfo info) {
        if (!(info instanceof SimpleKeyboardScoreInfo)) {
            throw new IllegalArgumentException("Info must be of type SimpleKeyboardScoreInfo");
        }
        this.info = (SimpleKeyboardScoreInfo) info;
    }
}
