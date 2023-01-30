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
        boolean[][] arr = new boolean[3][length + 1];

        for (int line = 0; line < arr.length; line++) {
            // The walkable path with the first and last cells being walls
            if (line == 1) {
                Arrays.fill(arr[line], true);
                arr[line][0] = false;
            } else
                Arrays.fill(arr[line], false); // Top and Bottom walls
        }

        assertArrayEquals(laby.getBoard(), arr);
    }

    @Test
    void testIsMoveValid() {
        // TODO: Split into multiple sub-tests
        // Multi-Cell Labyrinth
        LabyrinthModel1D labyLong = new LabyrinthModel1D(20, null);
        // 1st cell
        PlayerImplementation playerA = new PlayerImplementation(1);
        assertUpDownIsMoveValidFalse(labyLong, playerA);
        assertFalse(labyLong.isMoveValid(playerA, Direction.LEFT));
        assertTrue(labyLong.isMoveValid(playerA, Direction.RIGHT));

        // Last cell
        PlayerImplementation playerB = new PlayerImplementation(20);
        assertUpDownIsMoveValidFalse(labyLong, playerB);
        assertTrue(labyLong.isMoveValid(playerB, Direction.LEFT));
        assertFalse(labyLong.isMoveValid(playerB, Direction.RIGHT));

        // Mid cells
        PlayerImplementation playerC = new PlayerImplementation(10);
        assertUpDownIsMoveValidFalse(labyLong, playerC);
        assertTrue(labyLong.isMoveValid(playerC, Direction.LEFT));
        assertTrue(labyLong.isMoveValid(playerC, Direction.RIGHT));
    }

    /**
     * Asserts that the given player cannot move UP or DOWN
     * in the given labyrinth
     * 
     * @param labyrinth
     * @param player
     */
    private void assertUpDownIsMoveValidFalse(LabyrinthModel1D labyrinth, Player player) {
        assertEquals(false, labyrinth.isMoveValid(player, Direction.UP));
        assertEquals(false, labyrinth.isMoveValid(player, Direction.DOWN));
    }

    @Test
    void testMovePlayer() {
        // TODO: Split into multiple sub-tests
        // Single Cell Labyrinth
        LabyrinthModel1D labyShort = new LabyrinthModel1D(2, null);

        assertPlayerWillMoveCorrectly(1, Direction.RIGHT, labyShort);
        assertPlayerWillMoveCorrectly(2, Direction.LEFT, labyShort);
        assertPlayerWillNotMove(1, Direction.LEFT, labyShort);
        assertPlayerWillNotMove(2, Direction.RIGHT, labyShort);

        // Multi-Cell Labyrinth
        LabyrinthModel1D labyLong = new LabyrinthModel1D(20, null);

        // 1st Cell
        assertPlayerWillNotMove(1, Direction.UP, labyLong);
        assertPlayerWillNotMove(1, Direction.DOWN, labyLong);
        assertPlayerWillNotMove(1, Direction.LEFT, labyLong);
        assertPlayerWillMoveCorrectly(1, Direction.RIGHT, labyLong);

        // Any Cell in between
        assertPlayerWillNotMove(8, Direction.UP, labyLong);
        assertPlayerWillNotMove(8, Direction.DOWN, labyLong);
        assertPlayerWillMoveCorrectly(8, Direction.LEFT, labyLong);
        assertPlayerWillMoveCorrectly(8, Direction.RIGHT, labyLong);

        // Last Cell
        assertPlayerWillNotMove(20, Direction.UP, labyLong);
        assertPlayerWillNotMove(20, Direction.DOWN, labyLong);
        assertPlayerWillMoveCorrectly(20, Direction.LEFT, labyLong);
        assertPlayerWillNotMove(20, Direction.RIGHT, labyLong);

    }

    /**
     * @param initPosition
     * @param direction
     * @param labyrinth
     */
    private void assertPlayerWillNotMove(
            int initPosition, Direction direction,
            LabyrinthModel1D labyrinth) {

        PlayerImplementation player = new PlayerImplementation(initPosition);
        labyrinth.movePlayer(player, direction);
        int endPosition = player.getX();
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

        PlayerImplementation player = new PlayerImplementation(initPosition);
        int endPosition = player.getX() + direction.getStep();
        labyrinth.movePlayer(player, direction);
        assertEquals(endPosition, player.getX());
    }

    @Test
    void testIsPlayerAtExit() {

        assertIsPlayerAtExit(2);
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
            Player p = new PlayerImplementation(position);

            assertEquals(position == laby.getBoard()[1].length - 1,
                    laby.isPlayerAtExit(p));

        }
    }

    @Test
    void testIsGameOver() {
        // TODO: Split into multiple sub-tests
        LabyrinthModel1D labyNoPlayers = new LabyrinthModel1D(5, null);
        assertFalse(labyNoPlayers.isGameOver());

        Player a = new PlayerImplementation(5);
        Player b = new PlayerImplementation(5);
        Player c = new PlayerImplementation(5);

        Player[] arrPlayersA = { a, b, c };
        LabyrinthModel1D labyPlayersA = new LabyrinthModel1D(5, arrPlayersA);
        assertTrue(labyPlayersA.isGameOver());

        Player d = new PlayerImplementation(5);
        Player e = new PlayerImplementation(0);
        Player f = new PlayerImplementation(3);

        Player[] arrPlayersB = { d, e, f };
        LabyrinthModel1D labyPlayersB = new LabyrinthModel1D(5, arrPlayersB);
        assertFalse(labyPlayersB.isGameOver());
    }

}
