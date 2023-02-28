package com.gytmy.labyrinth.model.score;

public class ScoreCalculatorFactory {

    public enum ScoreType {
        CLASSIC
    }

    public static ScoreCalculator getScoreCalculator(ScoreType type, ScoreInfo info) {
        switch (type) {
            case CLASSIC:
                if (!(info instanceof ClassicScoreInfo)) {
                    throw new IllegalArgumentException("Info must be of type PlayerClassicScoreInfo");
                }
                return new ClassicScoreCalculator((ClassicScoreInfo) info);
            default:
                throw new IllegalArgumentException("Unknown score type");
        }
    }

    public static ScoreCalculator getScoreCalculator(ScoreType type) {
        return getScoreCalculator(type, null);
    }

    public static ScoreCalculator getScoreCalculator(ScoreInfo info) {
        if (info instanceof ClassicScoreInfo) {
            return getScoreCalculator(ScoreType.CLASSIC, info);
        }
        throw new IllegalArgumentException("Unknown score info type");
    }

}
