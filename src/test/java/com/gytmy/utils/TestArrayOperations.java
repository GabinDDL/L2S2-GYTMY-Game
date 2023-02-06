package com.gytmy.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.Test;

public class TestArrayOperations {

    @Test
    public void testCopyNull() {
        assertCorrectExceptionMessage(() -> Boolean2DArraysOperations.booleanCopy2D(null));
    }

    private void assertCorrectExceptionMessage(Runnable runner) {
        Exception exceptionZero = assertThrows(IllegalArgumentException.class,
                () -> runner.run());
        assertEquals("Array cannot be null", exceptionZero.getMessage());
    }

    @Test
    public void testCopyEmpty() {
        boolean[][] array = new boolean[0][0];
        boolean[][] result = Boolean2DArraysOperations.booleanCopy2D(array);

        assertTrue(Arrays.deepEquals(array, result));
    }

    @Test
    public void testCopy1D() {
        boolean[][] array = new boolean[][] { { true, false, true } };
        boolean[][] result = Boolean2DArraysOperations.booleanCopy2D(array);

        assertTrue(Arrays.deepEquals(array, result));
    }

    @Test
    public void testCopy2D() {
        boolean[][] array = new boolean[][] { { true, false, true }, { false, true, false } };
        boolean[][] result = Boolean2DArraysOperations.booleanCopy2D(array);

        assertTrue(Arrays.deepEquals(array, result));
    }

    @Test
    public void testPrintNull() {
        assertCorrectExceptionMessage(() -> Boolean2DArraysOperations.printBoolean2DArray(null));
    }

    @Test
    public void testIsColumnEmptyNull() {
        assertCorrectExceptionMessage(() -> Boolean2DArraysOperations.isColumnEmpty(null, 0));
    }

    @Test
    public void testIsColumnEmpty() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        assertTrue(Boolean2DArraysOperations.isColumnEmpty(array, 0));
        assertFalse(Boolean2DArraysOperations.isColumnEmpty(array, 1));
        assertFalse(Boolean2DArraysOperations.isColumnEmpty(array, 2));
        assertFalse(Boolean2DArraysOperations.isColumnEmpty(array, 3));
        assertFalse(Boolean2DArraysOperations.isColumnEmpty(array, 4));
    }

    @Test
    public void testIsRowEmptyNull() {
        assertCorrectExceptionMessage(() -> Boolean2DArraysOperations.isRowEmpty(null, 0));
    }

    @Test
    public void testIsRowEmpty() {
        boolean[][] array = new boolean[][] { { false, false, false, false, false }, { true, false, true, false, true },
                { false, false, false, false, true }, { true, true, true, true, true } };

        assertTrue(Boolean2DArraysOperations.isRowEmpty(array, 0));
        assertFalse(Boolean2DArraysOperations.isRowEmpty(array, 1));
        assertFalse(Boolean2DArraysOperations.isRowEmpty(array, 2));
        assertFalse(Boolean2DArraysOperations.isRowEmpty(array, 3));
    }

    @Test
    public void testAddEmptyColumnNull() {
        assertCorrectExceptionMessage(() -> Boolean2DArraysOperations.addEmptyColumn(null, 0));
    }

    @Test
    public void testAddEmptyColumnIndex0() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = Boolean2DArraysOperations.addEmptyColumn(array, 0);
        boolean[][] expected = new boolean[][] { { false, false, false, true, false, false },
                { false, false, false, true, true, true }, { false, false, false, true, false, true },
                { false, false, true, true, true, true } };

        assertTrue(Arrays.deepEquals(expected, result));
    }

    @Test
    public void testAddEmptyColumnIndex1() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = Boolean2DArraysOperations.addEmptyColumn(array, 1);
        boolean[][] expected = new boolean[][] { { false, false, false, true, false, false },
                { false, false, false, true, true, true },
                { false, false, false, true, false, true }, { false, false, true, true, true, true } };

        assertTrue(Arrays.deepEquals(expected, result));
    }

    @Test
    public void testAddEmptyColumnIndexLength() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = Boolean2DArraysOperations.addEmptyColumn(array, array[0].length);
        boolean[][] expected = new boolean[][] { { false, false, true, false, false, false },
                { false, false, true, true, true, false },
                { false, false, true, false, true, false }, { false, true, true, true, true, false } };
        assertTrue(Arrays.deepEquals(expected, result));
    }

    @Test
    public void testAddEmptyRowNull() {
        assertCorrectExceptionMessage(() -> Boolean2DArraysOperations.addEmptyRow(null, 0));
    }

    @Test
    public void testAddEmptyRowIndex0() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = Boolean2DArraysOperations.addEmptyRow(array, 0);
        boolean[][] expected = new boolean[][] { { false, false, false, false, false },
                { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        assertTrue(Arrays.deepEquals(expected, result));
    }

    @Test
    public void testAddEmptyRowIndex1() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = Boolean2DArraysOperations.addEmptyRow(array, 1);
        boolean[][] expected = new boolean[][] { { false, false, true, false, false },
                { false, false, false, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        assertTrue(Arrays.deepEquals(expected, result));
    }

    @Test
    public void testAddEmptyRowIndexLength() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = Boolean2DArraysOperations.addEmptyRow(array, array.length);
        boolean[][] expected = new boolean[][] { { false, false, true, false, false },
                { false, false, true, true, true }, { false, false, true, false, true },
                { false, true, true, true, true }, { false, false, false, false, false } };

        assertTrue(Arrays.deepEquals(expected, result));
    }

}
