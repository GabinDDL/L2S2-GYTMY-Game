package com.gytmy.labyrinth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

import com.gytmy.utils.Vector2;

public class TestLabyrinthImplementation {

    @Test
    public void testConstructorNullBoard() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModelImplementation(null, null, null, null));
        assertEquals("Board cannot be null", exception.getMessage());
    }

    @Test
    public void testConstructorNullInitialCell() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModelImplementation(new boolean[3][3], null, null, null));
        assertEquals("Initial cell cannot be null", exception.getMessage());
    }

    @Test
    public void testConstructorNullExitCell() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModelImplementation(new boolean[3][3], new Vector2(0, 0), null, null));
        assertEquals("Exit cell cannot be null", exception.getMessage());
    }

    @Test
    public void testConstructorSmallBoard() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModelImplementation(new boolean[2][2], new Vector2(0, 0), new Vector2(0, 0), null));
        assertEquals("Board must have at least 3 rows", exception.getMessage());
    }

    @Test
    public void testConstructorInvalidInitialCell() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModelImplementation(new boolean[3][3], new Vector2(0, 0), new Vector2(0, 0), null));
        assertEquals("Initial cell is a wall", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModelImplementation(new boolean[3][3], new Vector2(0, 3), new Vector2(0, 0), null));
        assertEquals("Initial cell is outside the board", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModelImplementation(new boolean[3][3], new Vector2(3, 0), new Vector2(0, 0), null));
        assertEquals("Initial cell is outside the board", exception.getMessage());
    }

    @Test
    public void testConstructorInvalidExitCell() {

        boolean[][] board = new boolean[3][3];
        board[1][1] = true;
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModelImplementation(board, new Vector2(1, 1), new Vector2(0, 0), null));
        assertEquals("Exit cell is a wall", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModelImplementation(board, new Vector2(1, 1), new Vector2(0, 3), null));
        assertEquals("Exit cell is outside the board", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModelImplementation(board, new Vector2(1, 1), new Vector2(3, 0), null));
        assertEquals("Exit cell is outside the board", exception.getMessage());
    }

    @Test
    public void testConstructorSameInitialAndExitCell() {
        boolean[][] board = new boolean[3][3];
        board[1][1] = true;
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new LabyrinthModelImplementation(board, new Vector2(1, 1), new Vector2(1, 1), null));
        assertEquals("Initial and exit cells cannot be the same", exception.getMessage());
    }

    @Test
    public void testIsMoveValidValidMovement() {
        boolean[][] board = new boolean[5][5];
        board[1][1] = true;
        Player player = new PlayerImplementation(new Vector2(2, 2));
        LabyrinthModel labyrinth = createBordered5x5Labyrinth();

        assertTrue(labyrinth.isMoveValid(player, Direction.UP));
        assertTrue(labyrinth.isMoveValid(player, Direction.DOWN));
        assertTrue(labyrinth.isMoveValid(player, Direction.LEFT));
        assertTrue(labyrinth.isMoveValid(player, Direction.RIGHT));
    }

    /**
     * 
     * @return A 5x5 labyrinth with a border of walls and an empty center
     */
    private LabyrinthModel createBordered5x5Labyrinth() {
        boolean[][] board = new boolean[5][5];
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                board[i][j] = true;
            }
        }
        return new LabyrinthModelImplementation(board, new Vector2(1, 1), new Vector2(3, 2), null);
    }

    @Test
    public void testIsMoveValidInvalidMovementMovingToWall() {
        // Board completely full of walls except for the initial and exit cells
        boolean[][] board = new boolean[5][5];
        board[1][1] = true;
        board[2][3] = true;
        LabyrinthModelImplementation labyrinth = new LabyrinthModelImplementation(board, new Vector2(1, 1),
                new Vector2(3, 2), null);

        Player player = new PlayerImplementation(new Vector2(1, 1));

        assertFalse(labyrinth.isMoveValid(player, Direction.UP));
        assertFalse(labyrinth.isMoveValid(player, Direction.LEFT));
        assertFalse(labyrinth.isMoveValid(player, Direction.DOWN));
        assertFalse(labyrinth.isMoveValid(player, Direction.RIGHT));
    }

    @Test
    public void testIsMoveValidInvalidMovementMovingOutsideTopLeft() {
        LabyrinthModelImplementation labyrinth = createEmptyLabyrinth();

        Player player = new PlayerImplementation(new Vector2(0, 0));

        assertFalse(labyrinth.isMoveValid(player, Direction.UP));
        assertFalse(labyrinth.isMoveValid(player, Direction.LEFT));
        assertTrue(labyrinth.isMoveValid(player, Direction.DOWN));
        assertTrue(labyrinth.isMoveValid(player, Direction.RIGHT));
    }

    @Test
    public void testIsMoveValidInvalidMovementMovingOutsideBottomRight() {
        LabyrinthModelImplementation labyrinth = createEmptyLabyrinth();

        Player player = new PlayerImplementation(new Vector2(4, 4));

        assertTrue(labyrinth.isMoveValid(player, Direction.UP));
        assertTrue(labyrinth.isMoveValid(player, Direction.LEFT));
        assertFalse(labyrinth.isMoveValid(player, Direction.DOWN));
        assertFalse(labyrinth.isMoveValid(player, Direction.RIGHT));
    }

    @Test
    public void testIsMoveValidInvalidMovementMovingOutsideTopRight() {
        LabyrinthModelImplementation labyrinth = createEmptyLabyrinth();

        Player player = new PlayerImplementation(new Vector2(4, 0));

        assertFalse(labyrinth.isMoveValid(player, Direction.UP));
        assertTrue(labyrinth.isMoveValid(player, Direction.LEFT));
        assertTrue(labyrinth.isMoveValid(player, Direction.DOWN));
        assertFalse(labyrinth.isMoveValid(player, Direction.RIGHT));
    }

    @Test
    public void testIsMoveValidInvalidMovementMovingOutsideBottomLeft() {
        LabyrinthModelImplementation labyrinth = createEmptyLabyrinth();

        Player player = new PlayerImplementation(new Vector2(0, 4));

        assertTrue(labyrinth.isMoveValid(player, Direction.UP));
        assertFalse(labyrinth.isMoveValid(player, Direction.LEFT));
        assertFalse(labyrinth.isMoveValid(player, Direction.DOWN));
        assertTrue(labyrinth.isMoveValid(player, Direction.RIGHT));
    }

    /**
     * 
     * @return A 5x5 labyrinth without walls
     */
    private LabyrinthModelImplementation createEmptyLabyrinth() {
        // Empty board
        boolean[][] board = new boolean[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = true;
            }
        }
        return new LabyrinthModelImplementation(board,
                new Vector2(1, 1), new Vector2(3, 2), null);
    }

    @Test
    public void testMovePlayerValidMovement() {
        LabyrinthModelImplementation labyrinth = createEmptyLabyrinth();

        Player player = new PlayerImplementation(new Vector2(1, 1));

        assertTrue(labyrinth.isMoveValid(player, Direction.UP));
        assertTrue(labyrinth.isMoveValid(player, Direction.LEFT));
        assertTrue(labyrinth.isMoveValid(player, Direction.DOWN));
        assertTrue(labyrinth.isMoveValid(player, Direction.RIGHT));

        labyrinth.movePlayer(player, Direction.UP);
        assertEquals(new Vector2(1, 0), player.getCoordinates());
        labyrinth.movePlayer(player, Direction.LEFT);
        assertEquals(new Vector2(0, 0), player.getCoordinates());
        labyrinth.movePlayer(player, Direction.DOWN);
        assertEquals(new Vector2(0, 1), player.getCoordinates());
        labyrinth.movePlayer(player, Direction.RIGHT);
        assertEquals(new Vector2(1, 1), player.getCoordinates());
    }

    @Test
    public void testMovePlayerInvalidMovement() {
        LabyrinthModelImplementation labyrinth = createLabyrinthWithWalls();

        Player player = new PlayerImplementation(new Vector2(3, 3));

        assertFalse(labyrinth.isMoveValid(player, Direction.UP));
        assertFalse(labyrinth.isMoveValid(player, Direction.LEFT));
        assertFalse(labyrinth.isMoveValid(player, Direction.DOWN));
        assertFalse(labyrinth.isMoveValid(player, Direction.RIGHT));

        labyrinth.movePlayer(player, Direction.UP);
        assertEquals(new Vector2(3, 3), player.getCoordinates());
        labyrinth.movePlayer(player, Direction.LEFT);
        assertEquals(new Vector2(3, 3), player.getCoordinates());
        labyrinth.movePlayer(player, Direction.DOWN);
        assertEquals(new Vector2(3, 3), player.getCoordinates());
        labyrinth.movePlayer(player, Direction.RIGHT);
        assertEquals(new Vector2(3, 3), player.getCoordinates());
    }

    /**
     * 
     * @return A 7x7 labyrinth with walls. Only he center and the exit and the
     *         entrance are empty.
     */
    private LabyrinthModelImplementation createLabyrinthWithWalls() {
        boolean[][] board = new boolean[7][7];
        board[3][1] = true;
        board[3][3] = true;
        board[3][5] = true;
        return new LabyrinthModelImplementation(board, new Vector2(1, 3), new Vector2(5, 3), null);
    }

    @Test
    public void testNoPlayersMeansGameOver() {
        LabyrinthModel1D labyrinth = new LabyrinthModel1D(5, null);
        assertTrue(labyrinth.isGameOver());
    }

    @Test
    public void testAllPlayersAtExitMeansGameOver() {
        Player playerA = new PlayerImplementation(4, 1);
        Player playerB = new PlayerImplementation(4, 1);
        Player playerC = new PlayerImplementation(4, 1);
        Player playerD = new PlayerImplementation(4, 1);

        Player[] players = {
                playerA,
                playerB,
                playerC,
                playerD
        };

        LabyrinthModelImplementation labyrinth = createEmptyLabyrinth(5, players);
        assertTrue(labyrinth.isGameOver());
    }

    @Test
    public void testNotAllPlayersAtExitMeansNoGameOver() {

        Player playerA = new PlayerImplementation(4, 1);
        Player playerB = new PlayerImplementation(4, 1);
        Player playerC = new PlayerImplementation(4, 1);
        Player playerD = new PlayerImplementation(1, 1);

        Player[] players = {
                playerA,
                playerB,
                playerC,
                playerD
        };

        LabyrinthModelImplementation labyrinth = createEmptyLabyrinth(5, players);
        assertFalse(labyrinth.isGameOver());

        playerC.setCoordinates(new Vector2(1, 1));
        assertFalse(labyrinth.isGameOver());

        playerB.setCoordinates(new Vector2(1, 1));
        assertFalse(labyrinth.isGameOver());

        playerA.setCoordinates(new Vector2(1, 1));
        assertFalse(labyrinth.isGameOver());
    }

    private LabyrinthModelImplementation createEmptyLabyrinth(int n, Player[] players) {
        // Empty board
        boolean[][] board = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = true;
            }
        }
        return new LabyrinthModelImplementation(board,
                new Vector2(1, 1), new Vector2(n - 1, 1), players);
    }

}
