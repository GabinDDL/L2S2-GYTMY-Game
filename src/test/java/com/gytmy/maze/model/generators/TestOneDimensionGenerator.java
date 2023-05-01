package com.gytmy.maze.model.generators;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestOneDimensionGenerator {

    @Test
    public void testConstructorInvalidLength() {
        String expectedErrorMessage = "Cannot initialize a maze of size <= 1";

        TestingUtils.assertArgumentExceptionMessage(
                () -> new OneDimensionBoardGenerator(0).generate(), expectedErrorMessage);

        TestingUtils.assertArgumentExceptionMessage(

                () -> new OneDimensionBoardGenerator(1).generate(), expectedErrorMessage);

        TestingUtils.assertArgumentExceptionMessage(

                () -> new OneDimensionBoardGenerator(-1).generate(), expectedErrorMessage);

    }

    @Test
    public void testConstructorValidLength() {

        assertValidBoard(2);
        assertValidBoard(50);
        assertValidBoard(100);

    }

    /**
     * Asserts if the board's content is valid
     * according to the definition
     * 
     * @param length must be >= 1
     */
    private void assertValidBoard(int length) {

        OneDimensionBoardGenerator maze = new OneDimensionBoardGenerator(length);

        // Do not forget the left and right borders
        boolean[][] array = new boolean[3][length + 2];

        for (int row = 0; row < array.length; ++row) {
            // The walkable path with the first cell being a wall
            if (row == 1) {
                Arrays.fill(array[row], true);
                array[row][0] = false;
                array[row][length + 1] = false;
            } else
                Arrays.fill(array[row], false); // Top and Bottom walls
        }

        assertArrayEquals(maze.generate(), array);
    }

}
