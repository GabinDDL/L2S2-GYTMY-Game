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
        assertEquals("Cannot initialize a labyrinth of size <= 1", exceptionZero.getMessage());

        Exception exceptionOne = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModel1D(1, null));
        assertEquals("Cannot initialize a labyrinth of size <= 1", exceptionOne.getMessage());

        Exception exceptionNegative = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModel1D(-1, null));
        assertEquals("Cannot initialize a labyrinth of size <= 1", exceptionNegative.getMessage());

    }

    @Test
    void testConstructorValidLength() {

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

        LabyrinthModel1D laby = new LabyrinthModel1D(length, null);

        // Do not forget the left and right borders
        boolean[][] arr = new boolean[3][length + 2];

        for (int line = 0; line < arr.length; line++) {
            // The walkable path with the first and last cells being walls
            if (line == 1) {
                Arrays.fill(arr[line], true);
                arr[line][0] = false;
                arr[line][arr.length - 1] = false;
            } else
                Arrays.fill(arr[line], false); // Top and Bottom walls
        }

        assertArrayEquals(laby.getBoard(), arr);
    }

    @Test
    void testIsMoveValid() {
        // Single Cell Labyrinth
        LabyrinthModel1D labyShort = new LabyrinthModel1D(1, null);
        Player1D playerA = new Player1D(0); // Only position available
        assertUpDownIsMoveValidFalse(labyShort, playerA);
        assertFalse(labyShort.isMoveValid(playerA, Direction.LEFT));
        assertFalse(labyShort.isMoveValid(playerA, Direction.RIGHT));

        // Multi-Cell Labyrinth
        LabyrinthModel1D labyLong = new LabyrinthModel1D(20, null);
        // 1st cell
        Player1D playerB = new Player1D(0);
        assertUpDownIsMoveValidFalse(labyShort, playerB);
        assertFalse(labyLong.isMoveValid(playerB, Direction.LEFT));
        assertTrue(labyLong.isMoveValid(playerB, Direction.RIGHT));

        // Last cell
        Player1D playerC = new Player1D(19);
        assertUpDownIsMoveValidFalse(labyShort, playerC);
        assertTrue(labyLong.isMoveValid(playerC, Direction.LEFT));
        assertFalse(labyLong.isMoveValid(playerC, Direction.RIGHT));

        // Mid cells
        Player1D playerD = new Player1D(10);
        assertUpDownIsMoveValidFalse(labyShort, playerD);
        assertTrue(labyLong.isMoveValid(playerD, Direction.LEFT));
        assertTrue(labyLong.isMoveValid(playerD, Direction.RIGHT));
    }

    /**
     * Asserts that the given player cannot move UP or DOWN
     * in the given labyrinth
     * 
     * @param labyrinth
     * @param player
     */
    private void assertUpDownIsMoveValidFalse(LabyrinthModel1D labyrinth, Player player) {
        assertFalse(labyrinth.isMoveValid(player, Direction.UP));
        assertFalse(labyrinth.isMoveValid(player, Direction.DOWN));
    }

    @Test
    void testMovePlayer() {
        // Single Cell Labyrinth
        LabyrinthModel1D labyShort = new LabyrinthModel1D(1, null);

        assertPlayerWillNotMove(0, Direction.UP, labyShort);
        assertPlayerWillNotMove(0, Direction.DOWN, labyShort);
        assertPlayerWillNotMove(0, Direction.LEFT, labyShort);
        assertPlayerWillNotMove(0, Direction.RIGHT, labyShort);

        // Multi-Cell Labyrinth
        LabyrinthModel1D labyLong = new LabyrinthModel1D(20, null);

        // 1st Cell
        assertPlayerWillNotMove(0, Direction.UP, labyLong);
        assertPlayerWillNotMove(0, Direction.DOWN, labyLong);
        assertPlayerWillNotMove(0, Direction.LEFT, labyLong);
        assertPlayerWillMoveCorrectly(0, Direction.RIGHT, labyLong);

        // Any Cell in between
        assertPlayerWillNotMove(8, Direction.UP, labyLong);
        assertPlayerWillNotMove(8, Direction.DOWN, labyLong);
        assertPlayerWillMoveCorrectly(8, Direction.LEFT, labyLong);
        assertPlayerWillMoveCorrectly(8, Direction.RIGHT, labyLong);

        // Last Cell
        assertPlayerWillNotMove(19, Direction.UP, labyLong);
        assertPlayerWillNotMove(19, Direction.DOWN, labyLong);
        assertPlayerWillMoveCorrectly(19, Direction.LEFT, labyLong);
        assertPlayerWillNotMove(19, Direction.RIGHT, labyLong);

    }

    /**
     * Asserts that a player will not move in
     * the given direction in the labyrinth
     * 
     * @param labyrinth
     * @param player
     */
    private void assertPlayerWillNotMove(
            int initPosition, Direction direction,
            LabyrinthModel1D labyrinth) {

        Player1D player = new Player1D(initPosition);
        labyrinth.movePlayer(player, direction);
        int endPosition = player.getCoordinates()[0];
        assertEquals(initPosition, endPosition);
    }

    /**
     * Asserts that a player will move correctly in
     * the given direction in the given labyrinth
     * 
     * @param labyrinth
     * @param player
     */
    private void assertPlayerWillMoveCorrectly(int initPosition,
            Direction direction, LabyrinthModel1D labyrinth) {

        Player1D player = new Player1D(initPosition);
        int endPosition = player.getCoordinates()[0] + direction.getStep();
        labyrinth.movePlayer(player, direction);
        assertEquals(endPosition, player.getCoordinates()[0]);
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
