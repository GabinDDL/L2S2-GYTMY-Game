package com.gytmy.labyrinth.model.generators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import com.gytmy.TestingUtils;
import com.gytmy.utils.Coordinates;

public class TestDepthFirstGenerator {

    @Test
    public void testConstructorInvalidSize() {
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator(1, 5).generate(),
                "The width must be at least 5");
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator(5, -5).generate(),
                "The height must be at least 5");
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator(1, -1).generate(),
                "The width must be at least 5");
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator(5, -3).generate(),
                "The height must be at least 5");
    }

    @Test
    public void testConstructorInvalidStart() {
        String expectedErrorMessage = "The start cell must be inside the labyrinth";
        TestingUtils.assertArgumentExceptionMessage(
                () -> new DepthFirstGenerator(5, 5, new Coordinates(0, 0)).generate(),
                expectedErrorMessage);
        TestingUtils.assertArgumentExceptionMessage(
                () -> new DepthFirstGenerator(5, 5, new Coordinates(4, 4)).generate(),
                expectedErrorMessage);
        TestingUtils.assertArgumentExceptionMessage(
                () -> new DepthFirstGenerator(5, 5, new Coordinates(5, 5)).generate(),
                expectedErrorMessage);
    }

    @Test
    public void testConstructorValid() {
        new DepthFirstGenerator(5, 5, new Coordinates(1, 1)).generate();
        new DepthFirstGenerator(5, 5, new Coordinates(2, 2)).generate();
        new DepthFirstGenerator(5, 5, new Coordinates(3, 3)).generate();

        new DepthFirstGenerator(7, 6, new Coordinates(1, 1)).generate();
        new DepthFirstGenerator(10, 9, new Coordinates(2, 2)).generate();
        new DepthFirstGenerator(15, 20, new Coordinates(3, 3)).generate();
    }

    @Test
    public void testNonEmptyGeneration() {
        DepthFirstGenerator generator = new DepthFirstGenerator(5, 5);
        boolean[][] board = generator.generate();

        assertTrue(board != null);
        assertTrue(board.length > 0);
        assertTrue(board[0].length > 0);
    }

    @Test
    public void testCorrectSizeOdd() {
        assertCorrectSize(5, 5);
        assertCorrectSize(5, 7);
        assertCorrectSize(7, 5);
        assertCorrectSize(5, 101);
        assertCorrectSize(101, 5);
        assertCorrectSize(11, 51);
        assertCorrectSize(51, 11);
        assertCorrectSize(51, 51);
    }

    @Test
    public void testCorrectSizeEven() {
        assertCorrectSize(6, 6);
        assertCorrectSize(6, 8);
        assertCorrectSize(8, 6);
        assertCorrectSize(6, 102);
        assertCorrectSize(102, 6);
        assertCorrectSize(12, 52);
        assertCorrectSize(52, 12);
        assertCorrectSize(52, 52);
    }

    @Test
    public void testCorrectSizeOddAndEven() {
        assertCorrectSize(5, 6);
        assertCorrectSize(6, 5);
        assertCorrectSize(5, 102);
        assertCorrectSize(102, 5);
        assertCorrectSize(11, 52);
        assertCorrectSize(52, 11);
        assertCorrectSize(51, 52);
        assertCorrectSize(52, 51);
    }

    private void assertCorrectSize(int width, int height) {
        DepthFirstGenerator generator = new DepthFirstGenerator(width, height);
        boolean[][] board = generator.generate();

        String errorMessage = "width: " + width + ", height: " + height;

        if (height % 2 == 0) {
            assertTrue(board.length == height + 1, errorMessage);
        } else {
            assertTrue(board.length == height, errorMessage);
        }

        if (width % 2 == 0) {
            assertTrue(board[0].length == width + 1, errorMessage);
        } else {
            assertTrue(board[0].length == width, errorMessage);
        }
    }

}
