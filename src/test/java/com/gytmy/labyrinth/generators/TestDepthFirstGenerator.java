package com.gytmy.labyrinth.generators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestDepthFirstGenerator {

    @Test
    public void testConstructorOddNumber() {
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator(2, 3), "The width must be odd");
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator(3, 2), "The height must be odd");
    }

    @Test
    public void testConstructorSize() {
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator(-1, 5),
                "The width must be at least 5");
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator(5, -5),
                "The height must be at least 5");
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator(1, 1),
                "The width must be at least 5");
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator(5, 3),
                "The height must be at least 5");
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
    public void testCorrectSize() {
        assertCorrectSize(5, 5);
        assertCorrectSize(5, 7);
        assertCorrectSize(7, 5);
        assertCorrectSize(5, 101);
        assertCorrectSize(101, 5);
        assertCorrectSize(11, 51);
        assertCorrectSize(51, 11);
        assertCorrectSize(51, 51);
    }

    private void assertCorrectSize(int width, int height) {
        DepthFirstGenerator generator = new DepthFirstGenerator(width, height);
        boolean[][] board = generator.generate();

        assertTrue(board.length == height);
        assertTrue(board[0].length == width);
    }

}
