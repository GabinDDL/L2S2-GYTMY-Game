package com.gytmy.maze.model.score;

public class SimpleVoiceScoreCalculator implements ScoreCalculator {
    private static final SimpleScoreCalculator.SimpleScoreCalculatorParameters DEFAULT_PARAMETERS = new SimpleScoreCalculator.SimpleScoreCalculatorParameters() {
        @Override
        public double getMovementPenalty() {
            return 2;
        }

        @Override
        public double getTimePenalty() {
            return 1;
        }

        @Override
        public double getMovementsFactor() {
            return 2.5;
        }

        @Override
        public double getTimeFactor() {
            return 3;
        }

        @Override
        public int getFirstChangingPoint() {
            return 45;
        }

        @Override
        public double getIdealMovementTimeUpper() {
            return 8;
        }

        @Override
        public double getIdealMovementTimeLower() {
            return 7;
        }
    };

    private SimpleScoreCalculator calculator;

    public SimpleVoiceScoreCalculator(SimpleScoreInfo info) {
        calculator = new SimpleScoreCalculator(info, DEFAULT_PARAMETERS);

    }

    @Override
    public int getScore() {
        return calculator.getScore();
    }

    @Override
    public void updateInfo(ScoreInfo info) {
        calculator.updateInfo(info);
    }
}