package com.gytmy.maze.model.score;

/**
 * This calculator is used to compute the score of a player using a simple
 * method.
 * 
 * The score takes into account the time passed and the number of movements. The
 * idea behind the score is that the player should be able to do the game in the
 * minimum number of movements in the minimum time.
 * 
 * The minimum considers only the distance to the exit cell and it
 * should represent the minimum possible time to do the game. It is the distance
 * multiplied by the ideal time to do a movement. This can be adapted to the way
 * the movement is controlled.
 * 
 * To do so, the score is computed as follow:
 * <ul>
 * <li>First, the score is computed as the maximum score minus a penalty. The
 * penalty is computed as the sum of the time penalty and the movement
 * penalty.</li>
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
 * <li>If the number of movements is less or equals than the minimum number of
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
public class SimpleScoreCalculator implements ScoreCalculator {
    public static final int MAX_SCORE = 1000;

    private SimpleScoreInfo info;
    private int bestTime;

    // The factor to compute the movement penalty
    private double movementsFactor;
    // The factor to compute the time penalty
    private double timeFactor;

    private double movementPenalty;
    private double timePenalty;

    private int firstChangingPoint;
    private double idealMovementTimeUpper;
    private double idealMovementTimeLower;

    public SimpleScoreCalculator(SimpleScoreInfo info, SimpleScoreCalculatorParameters parameters) {
        if (info == null) {
            throw new IllegalArgumentException("Info must not be null");
        }
        this.info = info;
        int minMovement = info.getMinMovements();
        this.bestTime = computeBestTime(minMovement);
        getParameters(parameters);
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
        if (minMovement <= firstChangingPoint) {
            return (int) (idealMovementTimeLower * minMovement);
        } else {
            return (int) (idealMovementTimeUpper * minMovement);
        }
    }

    private void getParameters(SimpleScoreCalculatorParameters parameters) {
        this.movementsFactor = parameters.getMovementsFactor();
        this.timeFactor = parameters.getTimeFactor();
        this.movementPenalty = parameters.getMovementPenalty();
        this.timePenalty = parameters.getTimePenalty();
        this.firstChangingPoint = parameters.getFirstChangingPoint();
        this.idealMovementTimeUpper = parameters.getIdealMovementTimeUpper();
        this.idealMovementTimeLower = parameters.getIdealMovementTimeLower();
    }

    @Override
    public int getScore() {
        int score = MAX_SCORE;
        int movements = info.getMovements();
        int timePassed = info.getTimePassed();
        int minMovements = info.getMinMovements();

        double scoreTimePenalty = computeTimePenalty(timePassed);
        double scoreMovementPenalty = computeMovementPenalty(movements, minMovements);

        double totalPenalty = (timePenalty * scoreTimePenalty + movementPenalty * scoreMovementPenalty)
                / (timePenalty + movementPenalty);

        score -= totalPenalty * MAX_SCORE;
        return score;
    }

    private double computeTimePenalty(int timePassed) {
        double upperBound = timeFactor * bestTime;
        return computeTimePenalty(timePassed, bestTime, upperBound);
    }

    private double computeMovementPenalty(int movements, int minMovements) {
        double upperBound = movementsFactor * minMovements;
        return computeTimePenalty(movements, minMovements, upperBound);
    }

    private double computeTimePenalty(int value, double lowerBound, double upperBound) {
        if (value <= lowerBound) {
            return 0;
        }
        if (value >= upperBound) {
            return 1;
        }

        return (value - lowerBound) / (upperBound - lowerBound);
    }

    @Override
    public void updateInfo(ScoreInfo info) {
        if (!(info instanceof SimpleScoreInfo)) {
            throw new IllegalArgumentException("Info must be of type SimpleKeyboardScoreInfo");
        }
        this.info = (SimpleScoreInfo) info;
    }

    public static interface SimpleScoreCalculatorParameters {
        public double getMovementsFactor();

        public double getTimeFactor();

        public double getMovementPenalty();

        public double getTimePenalty();

        public int getFirstChangingPoint();

        public double getIdealMovementTimeUpper();

        public double getIdealMovementTimeLower();
    }
}
