package com.gytmy.labyrinth.generators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import com.gytmy.TestingUtils;
import com.gytmy.utils.Vector2;

public class TestDepthFirstGenerator {

    @Test
    public void testConstructorOddNumber() {
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator().generate(2, 3),
                "The width must be odd");
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator().generate(3, 2),
                "The height must be odd");
    }

    @Test
    public void testConstructorInvalidSize() {
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator().generate(-1, 5),
                "The width must be at least 5");
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator().generate(5, -5),
                "The height must be at least 5");
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator().generate(1, -1),
                "The width must be at least 5");
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator().generate(5, -3),
                "The height must be at least 5");
    }

    @Test
    public void testConstructorInvalidStart() {
        String expectedErrorMessage = "The start cell must be inside the labyrinth";
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator().generate(5, 5, new Vector2(0, 0)),
                expectedErrorMessage);
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator().generate(5, 5, new Vector2(4, 4)),
                expectedErrorMessage);
        TestingUtils.assertArgumentExceptionMessage(() -> new DepthFirstGenerator().generate(5, 5, new Vector2(5, 5)),
                expectedErrorMessage);
    }

    @Test
    public void testConstructorValid() {
        new DepthFirstGenerator().generate(5, 5, new Vector2(1, 1));
        new DepthFirstGenerator().generate(5, 5, new Vector2(2, 2));
        new DepthFirstGenerator().generate(5, 5, new Vector2(3, 3));
    }

    @Test
    public void testNonEmptyGeneration() {
        DepthFirstGenerator generator = new DepthFirstGenerator();
        boolean[][] board = generator.generate(5, 5);

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
        DepthFirstGenerator generator = new DepthFirstGenerator();
        boolean[][] board = generator.generate(width, height);

        assertTrue(board.length == height);
        assertTrue(board[0].length == width);
    }

}
