package com.gytmy.labyrinth;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class TestLabyrinthModel1D {

    @Test
    void testConstructorInvalidLength() {

        // Test labyrinth invalid length exceptions

        Exception exceptionZero = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModel1D(0));
        assertEquals("Cannot initialize a labyrinth of size <= 0", exceptionZero.getMessage());

        Exception exceptionNegative = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModel1D(-1));
        assertEquals("Cannot initialize a labyrinth of size <= 0", exceptionNegative.getMessage());

    }

    @Test
    void testConstructorValidLength() {

        assertValidBoard(1);
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
        LabyrinthModel1D laby = new LabyrinthModel1D(length);
        boolean[] arr = new boolean[length];
        Arrays.fill(arr, true);
        assertArrayEquals(laby.getBoard(), arr);
    }
}
