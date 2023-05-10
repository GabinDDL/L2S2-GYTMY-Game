package com.gytmy.maze.model.score;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestSimpleScoreCalculator {

    // The idea of these test is to check that the score is normalized between 0 and
    // MAX_SCORE. We use the SimpleKeyboardScoreCalculator because these test do not
    // depend on the parameters of the score calculator.
    private static final int MAX_SCORE = SimpleScoreCalculator.MAX_SCORE;

    @Test
    public void testIsNormalizedSmallTime() {
        SimpleScoreInfo scoreInfo = new SimpleScoreInfo(500, 500, 1);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedBigTime() {
        SimpleScoreInfo scoreInfo = new SimpleScoreInfo(500, 500, 1000000);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedSmallMovements() {
        SimpleScoreInfo scoreInfo = new SimpleScoreInfo(500, 10, 1000000);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedBigMovements() {
        SimpleScoreInfo scoreInfo = new SimpleScoreInfo(500, 1000000, 1000);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedSmallTimeAndMovements() {
        SimpleScoreInfo scoreInfo = new SimpleScoreInfo(500, 10, 1);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedBigTimeAndMovements() {
        SimpleScoreInfo scoreInfo = new SimpleScoreInfo(500, 1000000, 1000000);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedSmallDeviationInTime() {
        SimpleScoreInfo scoreInfo = new SimpleScoreInfo(21, 21, 3);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

    @Test
    public void testIsNormalizedSmallDeviationInMovements() {
        SimpleScoreInfo scoreInfo = new SimpleScoreInfo(500, 505, 500);
        SimpleKeyboardScoreCalculator calculator = new SimpleKeyboardScoreCalculator(scoreInfo);
        int score = calculator.getScore();
        assertTrue(score >= 0);
        assertTrue(score <= MAX_SCORE);
    }

}
