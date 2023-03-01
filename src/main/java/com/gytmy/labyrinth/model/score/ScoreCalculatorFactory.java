package com.gytmy.labyrinth.model.score;

public class ScoreCalculatorFactory {

    private ScoreCalculatorFactory() {
    }

    /**
     * Get the score calculator for the given score type and score info.
     * 
     * @param type the score type
     * @param info the score info
     * @return the score calculator
     */
    public static ScoreCalculator getScoreCalculator(ScoreType type, ScoreInfo info) {
        switch (type) {
            case SIMPLE_KEYBOARD:
                if (!(info instanceof SimpleKeyboardScoreInfo)) {
                    throw new IllegalArgumentException("Info must be of type SimpleKeyboardScoreInfo");
                }
                return new SimpleKeyboardScoreCalculator((SimpleKeyboardScoreInfo) info);
            default:
                throw new IllegalArgumentException("Unknown score type");
        }
    }

    /**
     * Get the score calculator for the given score info.
     * 
     * @param info the score info
     * @return the score calculator
     */
    public static ScoreCalculator getScoreCalculator(ScoreInfo info) {
        if (info instanceof SimpleKeyboardScoreInfo) {
            return getScoreCalculator(ScoreType.SIMPLE_KEYBOARD, info);
        }
        throw new IllegalArgumentException("Unknown score info type");
    }

}
