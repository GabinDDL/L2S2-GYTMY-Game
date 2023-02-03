package com.gytmy.utils;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class TestArrayOperations {

    @Test
    public void testCopyEmpty() {
        boolean[][] array = new boolean[0][0];
        boolean[][] result = ArrayOperations.booleanCopy2D(array);

        assertTrue(Arrays.deepEquals(array, result));
    }

    @Test
    public void testCopy1D() {
        boolean[][] array = new boolean[][] { { true, false, true } };
        boolean[][] result = ArrayOperations.booleanCopy2D(array);

        assertTrue(Arrays.deepEquals(array, result));
    }

    @Test
    public void testCopy2D() {
        boolean[][] array = new boolean[][] { { true, false, true }, { false, true, false } };
        boolean[][] result = ArrayOperations.booleanCopy2D(array);

        assertTrue(Arrays.deepEquals(array, result));
    }

}
