package com.gytmy.labyrinth.model.score;

public class ScoreCalculatorFactory {

    public enum ScoreType {
        SIMPLE_KEYBOARD
    }

    private ScoreCalculatorFactory() {
    }

    public static ScoreCalculator getScoreCalculator(ScoreType type, ScoreInfo info) {
        switch (type) {
            case SIMPLE_KEYBOARD:
                if (!(info instanceof SimpleKeyboardScoreInfo)) {
                    throw new IllegalArgumentException("Info must be of type PlayerClassicScoreInfo");
                }
                return new SimpleKeyboardScoreCalculator((SimpleKeyboardScoreInfo) info);
            default:
                throw new IllegalArgumentException("Unknown score type");
        }
    }

    public static ScoreCalculator getScoreCalculator(ScoreInfo info) {
        if (info instanceof SimpleKeyboardScoreInfo) {
            return getScoreCalculator(ScoreType.SIMPLE_KEYBOARD, info);
        }
        throw new IllegalArgumentException("Unknown score info type");
    }

}
