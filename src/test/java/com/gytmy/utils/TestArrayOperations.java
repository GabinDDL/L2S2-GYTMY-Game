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
        assertCorrectExceptionMessage(() -> ArrayOperations.booleanCopy2D(null));
    }

    private void assertCorrectExceptionMessage(Runnable runner) {
        Exception exceptionZero = assertThrows(IllegalArgumentException.class,
                () -> runner.run());
        assertEquals("Array cannot be null", exceptionZero.getMessage());
    }

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

    @Test
    public void testPrintNull() {
        assertCorrectExceptionMessage(() -> ArrayOperations.printBoolean2DArray(null));
    }

    @Test
    public void testIsColumnEmptyNull() {
        assertCorrectExceptionMessage(() -> ArrayOperations.isColumnEmpty(null, 0));
    }

    @Test
    public void testIsColumnEmpty() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        assertTrue(ArrayOperations.isColumnEmpty(array, 0));
        assertFalse(ArrayOperations.isColumnEmpty(array, 1));
        assertFalse(ArrayOperations.isColumnEmpty(array, 2));
        assertFalse(ArrayOperations.isColumnEmpty(array, 3));
        assertFalse(ArrayOperations.isColumnEmpty(array, 4));
    }

    @Test
    public void testIsRowEmptyNull() {
        assertCorrectExceptionMessage(() -> ArrayOperations.isRowEmpty(null, 0));
    }

    @Test
    public void testIsRowEmpty() {
        boolean[][] array = new boolean[][] { { false, false, false, false, false }, { true, false, true, false, true },
                { false, false, false, false, true }, { true, true, true, true, true } };

        assertTrue(ArrayOperations.isRowEmpty(array, 0));
        assertFalse(ArrayOperations.isRowEmpty(array, 1));
        assertFalse(ArrayOperations.isRowEmpty(array, 2));
        assertFalse(ArrayOperations.isRowEmpty(array, 3));
    }

    @Test
    public void testAddEmptyColumnNull() {
        assertCorrectExceptionMessage(() -> ArrayOperations.addEmptyColumn(null, 0));
    }

    @Test
    public void testAddEmptyColumnIndex0() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = ArrayOperations.addEmptyColumn(array, 0);
        boolean[][] expected = new boolean[][] { { false, false, false, true, false, false },
                { false, false, false, true, true, true }, { false, false, false, true, false, true },
                { false, false, true, true, true, true } };

        assertTrue(Arrays.deepEquals(expected, result));
    }

    @Test
    public void testAddEmptyColumnIndex1() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = ArrayOperations.addEmptyColumn(array, 1);
        boolean[][] expected = new boolean[][] { { false, false, false, true, false, false },
                { false, false, false, true, true, true },
                { false, false, false, true, false, true }, { false, false, true, true, true, true } };

        assertTrue(Arrays.deepEquals(expected, result));
    }

    @Test
    public void testAddEmptyColumnIndexLength() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = ArrayOperations.addEmptyColumn(array, array[0].length);
        boolean[][] expected = new boolean[][] { { false, false, true, false, false, false },
                { false, false, true, true, true, false },
                { false, false, true, false, true, false }, { false, true, true, true, true, false } };
        assertTrue(Arrays.deepEquals(expected, result));
    }

    @Test
    public void testAddEmptyRowNull() {
        assertCorrectExceptionMessage(() -> ArrayOperations.addEmptyRow(null, 0));
    }

    @Test
    public void testAddEmptyRowIndex0() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = ArrayOperations.addEmptyRow(array, 0);
        boolean[][] expected = new boolean[][] { { false, false, false, false, false },
                { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        assertTrue(Arrays.deepEquals(expected, result));
    }

    @Test
    public void testAddEmptyRowIndex1() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = ArrayOperations.addEmptyRow(array, 1);
        boolean[][] expected = new boolean[][] { { false, false, true, false, false },
                { false, false, false, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        assertTrue(Arrays.deepEquals(expected, result));
    }

    @Test
    public void testAddEmptyRowIndexLength() {
        boolean[][] array = new boolean[][] { { false, false, true, false, false }, { false, false, true, true, true },
                { false, false, true, false, true }, { false, true, true, true, true } };

        boolean[][] result = ArrayOperations.addEmptyRow(array, array.length);
        boolean[][] expected = new boolean[][] { { false, false, true, false, false },
                { false, false, true, true, true }, { false, false, true, false, true },
                { false, true, true, true, true }, { false, false, false, false, false } };

        assertTrue(Arrays.deepEquals(expected, result));
    }

}
