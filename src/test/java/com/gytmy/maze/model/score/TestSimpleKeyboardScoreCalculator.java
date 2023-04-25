package com.gytmy.maze.model.score;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestSimpleKeyboardScoreCalculator {

    // The idea of these test is to check that the score is normalized between 0 and
    // MAX_SCORE.

    private static final int MAX_SCORE = SimpleKeyboardScoreCalculator.MAX_SCORE;

    @Test
    public void testIsNormalizedSmallTime() {
        SimpleKeyboardScoreInfo scoreInfo = new SimpleKeyboardScoreInfo(500, 500, 1);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedBigTime() {
        SimpleKeyboardScoreInfo scoreInfo = new SimpleKeyboardScoreInfo(500, 500, 1000000);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedSmallMovements() {
        SimpleKeyboardScoreInfo scoreInfo = new SimpleKeyboardScoreInfo(500, 10, 1000000);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedBigMovements() {
        SimpleKeyboardScoreInfo scoreInfo = new SimpleKeyboardScoreInfo(500, 1000000, 1000000);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedSmallTimeAndMovements() {
        SimpleKeyboardScoreInfo scoreInfo = new SimpleKeyboardScoreInfo(500, 10, 1);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedBigTimeAndMovements() {
        SimpleKeyboardScoreInfo scoreInfo = new SimpleKeyboardScoreInfo(500, 1000000, 1000000);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedSmallDeviationInTime() {
        SimpleKeyboardScoreInfo scoreInfo = new SimpleKeyboardScoreInfo(21, 21, 3);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedSmallDeviationInMovements() {
        SimpleKeyboardScoreInfo scoreInfo = new SimpleKeyboardScoreInfo(500, 505, 500);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

}
