package com.gytmy.labyrinth;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class TestLabyrinthModel1D {

    @Test
    void testConstructorInvalidLength() {

        // Test labyrinth invalid length exceptions

        Exception exceptionZero = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModel1D(0, null));
        assertEquals("Cannot initialize a labyrinth of size <= 0", exceptionZero.getMessage());

        Exception exceptionNegative = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModel1D(-1, null));
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

        LabyrinthModel1D laby = new LabyrinthModel1D(length, null);

        boolean[][] arr = new boolean[3][length];

        for (int line = 0; line < arr.length; line++) {
            // The borders
            if (line != 0 && line != arr.length - 1) {
                Arrays.fill(arr[line], true);
            } else
                Arrays.fill(arr[line], false); // The walkable path
        }

        assertArrayEquals(laby.getBoard(), arr);
    }

    @Test
    void testIsPlayerAtExit() {

        assertIsPlayerAtExit(1);
        assertIsPlayerAtExit(50);
        assertIsPlayerAtExit(100);

    }

    /**
     * Asserts if the function {@link LabyrinthModel1D#isPlayerAtExit(Player)}
     * works well for a labyrinth of the given length according to
     * the definition
     * 
     * @param labyrinthLength must be >= 1
     */
    private void assertIsPlayerAtExit(int labyrinthLength) {
        LabyrinthModel1D laby = new LabyrinthModel1D(labyrinthLength, null);

        for (int position = 0; position < labyrinthLength; position++) {
            Player p = new Player1D(position);

            assertEquals(position == labyrinthLength - 1,
                    laby.isPlayerAtExit(p));

        }
    }

    @Test
    void testIsGameOver() {

        LabyrinthModel1D labyNoPlayers = new LabyrinthModel1D(5, null);
        assertFalse(labyNoPlayers.isGameOver());

        Player a = new Player1D(4);
        Player b = new Player1D(4);
        Player c = new Player1D(4);

        Player[] arrPlayersA = { a, b, c };
        LabyrinthModel1D labyPlayersA = new LabyrinthModel1D(5, arrPlayersA);
        assertTrue(labyPlayersA.isGameOver());

        Player d = new Player1D(2);
        Player e = new Player1D(0);
        Player f = new Player1D(4);

        Player[] arrPlayersB = { d, e, f };
        LabyrinthModel1D labyPlayersB = new LabyrinthModel1D(5, arrPlayersB);
        assertFalse(labyPlayersB.isGameOver());
    }

}
