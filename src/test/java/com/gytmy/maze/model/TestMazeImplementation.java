package com.gytmy.maze.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import com.gytmy.TestingUtils;
import com.gytmy.maze.model.generators.BoardGenerator;
import com.gytmy.maze.model.generators.BorderBoardGenerator;
import com.gytmy.maze.model.generators.DepthFirstGenerator;
import com.gytmy.maze.model.generators.EmptyBoardGenerator;
import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.model.player.PlayerImplementation;
import com.gytmy.utils.Coordinates;

public class TestMazeImplementation {

    @Test
    public void testConstructorNullBoard() {
        boolean[][] board = null; // Defined to avoid ambiguity
        TestingUtils.assertArgumentExceptionMessage(
                () -> new MazeModelImplementation(getGenerator(board), null, null, null, null),
                "Board cannot be null");

    }

    @Test
    public void testConstructorInvalidBoardSize() {
        TestingUtils.assertArgumentExceptionMessage(
                () -> new MazeModelImplementation(getGenerator(new boolean[2][2]),
                        new Coordinates(0, 0), new Coordinates(0, 0), null, null),
                "Board must have at least 3 rows");

    }

    @Test
    public void testConstructorInvalidInitialCell() {
        TestingUtils.assertArgumentExceptionMessage(
                () -> new MazeModelImplementation(getGenerator(new boolean[3][3]), new Coordinates(0, 0),
                        new Coordinates(0, 0),
                        null, null),
                "Initial cell is a wall");

        TestingUtils.assertArgumentExceptionMessage(

                () -> new MazeModelImplementation(getGenerator(new boolean[3][3]), new Coordinates(0, 3),
                        new Coordinates(0, 0),
                        null, null),
                "Initial cell is outside the board");

        TestingUtils.assertArgumentExceptionMessage(
                () -> new MazeModelImplementation(getGenerator(new boolean[3][3]), new Coordinates(3, 0),
                        new Coordinates(0, 0),
                        null, null),
                "Initial cell is outside the board");
    }

    @Test
    public void testConstructorInvalidExitCell() {

        boolean[][] board = new boolean[3][3];
        board[1][1] = true;
        TestingUtils.assertArgumentExceptionMessage(
                () -> new MazeModelImplementation(getGenerator(board), new Coordinates(1, 1),
                        new Coordinates(0, 0), null, null),
                "Exit cell is a wall");

        TestingUtils.assertArgumentExceptionMessage(

                () -> new MazeModelImplementation(getGenerator(board), new Coordinates(1, 1),
                        new Coordinates(0, 3), null, null),
                "Exit cell is outside the board");

        TestingUtils.assertArgumentExceptionMessage(

                () -> new MazeModelImplementation(getGenerator(board), new Coordinates(1, 1),
                        new Coordinates(3, 0), null, null),
                "Exit cell is outside the board");
    }

    @Test
    public void testConstructorSameInitialAndExitCell() {
        boolean[][] board = new boolean[3][3];
        board[1][1] = true;
        TestingUtils.assertArgumentExceptionMessage(
                () -> new MazeModelImplementation(getGenerator(board), new Coordinates(1, 1),
                        new Coordinates(1, 1), null, null),
                "Initial and exit cells cannot be the same");
    }

    @Test
    public void testConstructorNonEmptyExitCell() {
        MazeModelImplementation maze = new MazeModelImplementation(new BorderBoardGenerator(101, 101),
                new Coordinates(1, 1), null, null, null);
        assertTrue(maze.getExitCell() != null);
    }

    @Test
    public void testConstructorEmptyBoard() {
        assertWasCorrectlyConstructed(new EmptyBoardGenerator(10, 10));
    }

    @Test
    public void testConstructorBorderBoard() {
        assertWasCorrectlyConstructed(new BorderBoardGenerator(10, 10));
    }

    private void assertWasCorrectlyConstructed(BoardGenerator generator) {
        Coordinates initialCell = new Coordinates(1, 1);
        Coordinates exitCell = new Coordinates(3, 1);
        MazeModelImplementation maze = new MazeModelImplementation(
                generator, initialCell, exitCell, null, null);

        assertArrayEquals(maze.getBoard(), generator.generate());
    }

    @Test
    public void testConstructorDepthFirstBoard() {
        Coordinates initialCell = new Coordinates(1, 1);
        Coordinates exitCell = new Coordinates(3, 1);
        MazeModelImplementation maze = new MazeModelImplementation(
                new DepthFirstGenerator(11, 15), initialCell, exitCell, null, null);

        assertEquals(exitCell, maze.getExitCell());
        assertEquals(11, maze.getBoard()[0].length);
        assertEquals(15, maze.getBoard().length);
    }

    @Test
    public void testIsMoveValidValidMovement() {
        boolean[][] board = new boolean[5][5];
        board[1][1] = true;
        Player player = new PlayerImplementation(new Coordinates(2, 2));
        MazeModel maze = createBordered5x5Maze();

        assertTrue(maze.isMoveValid(player, Direction.UP));
        assertTrue(maze.isMoveValid(player, Direction.DOWN));
        assertTrue(maze.isMoveValid(player, Direction.LEFT));
        assertTrue(maze.isMoveValid(player, Direction.RIGHT));
    }

    /**
     * 
     * @return A 5x5 maze with a border of walls and an empty center
     */
    private MazeModel createBordered5x5Maze() {
        boolean[][] board = new boolean[5][5];
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                board[i][j] = true;
            }
        }
        return new MazeModelImplementation(getGenerator(board), new Coordinates(1, 1), new Coordinates(3, 2),
                null, null);
    }

    @Test
    public void testIsMoveValidInvalidMovementMovingToWall() {
        // Board completely full of walls except for the initial and exit cells
        boolean[][] board = new boolean[5][5];
        board[1][1] = true;
        board[2][3] = true;
        MazeModelImplementation maze = new MazeModelImplementation(getGenerator(board),
                new Coordinates(1, 1),
                new Coordinates(3, 2), null, null);

        Player player = new PlayerImplementation(new Coordinates(1, 1));

        assertFalse(maze.isMoveValid(player, Direction.UP));
        assertFalse(maze.isMoveValid(player, Direction.LEFT));
        assertFalse(maze.isMoveValid(player, Direction.DOWN));
        assertFalse(maze.isMoveValid(player, Direction.RIGHT));
    }

    @Test
    public void testIsMoveValidInvalidMovementMovingOutsideTopLeft() {
        MazeModelImplementation maze = createEmptyMaze();

        Player player = new PlayerImplementation(new Coordinates(0, 0));

        assertFalse(maze.isMoveValid(player, Direction.UP));
        assertFalse(maze.isMoveValid(player, Direction.LEFT));
        assertTrue(maze.isMoveValid(player, Direction.DOWN));
        assertTrue(maze.isMoveValid(player, Direction.RIGHT));
    }

    @Test
    public void testIsMoveValidInvalidMovementMovingOutsideBottomRight() {
        MazeModelImplementation maze = createEmptyMaze();

        Player player = new PlayerImplementation(new Coordinates(4, 4));

        assertTrue(maze.isMoveValid(player, Direction.UP));
        assertTrue(maze.isMoveValid(player, Direction.LEFT));
        assertFalse(maze.isMoveValid(player, Direction.DOWN));
        assertFalse(maze.isMoveValid(player, Direction.RIGHT));
    }

    @Test
    public void testIsMoveValidInvalidMovementMovingOutsideTopRight() {
        MazeModelImplementation maze = createEmptyMaze();

        Player player = new PlayerImplementation(new Coordinates(4, 0));

        assertFalse(maze.isMoveValid(player, Direction.UP));
        assertTrue(maze.isMoveValid(player, Direction.LEFT));
        assertTrue(maze.isMoveValid(player, Direction.DOWN));
        assertFalse(maze.isMoveValid(player, Direction.RIGHT));
    }

    @Test
    public void testIsMoveValidInvalidMovementMovingOutsideBottomLeft() {
        MazeModelImplementation maze = createEmptyMaze();

        Player player = new PlayerImplementation(new Coordinates(0, 4));

        assertTrue(maze.isMoveValid(player, Direction.UP));
        assertFalse(maze.isMoveValid(player, Direction.LEFT));
        assertFalse(maze.isMoveValid(player, Direction.DOWN));
        assertTrue(maze.isMoveValid(player, Direction.RIGHT));
    }

    /**
     * 
     * @return A 5x5 maze without walls
     */
    private MazeModelImplementation createEmptyMaze() {
        // Empty board
        boolean[][] board = new boolean[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = true;
            }
        }
        return new MazeModelImplementation(getGenerator(board),
                new Coordinates(1, 1), new Coordinates(3, 2), null, null);
    }

    @Test
    public void testMovePlayerValidMovement() {
        MazeModelImplementation maze = createEmptyMaze();

        Player player = new PlayerImplementation(new Coordinates(1, 1));

        assertTrue(maze.isMoveValid(player, Direction.UP));
        assertTrue(maze.isMoveValid(player, Direction.LEFT));
        assertTrue(maze.isMoveValid(player, Direction.DOWN));
        assertTrue(maze.isMoveValid(player, Direction.RIGHT));

        maze.movePlayer(player, Direction.UP);
        assertEquals(new Coordinates(1, 0), player.getCoordinates());
        maze.movePlayer(player, Direction.LEFT);
        assertEquals(new Coordinates(0, 0), player.getCoordinates());
        maze.movePlayer(player, Direction.DOWN);
        assertEquals(new Coordinates(0, 1), player.getCoordinates());
        maze.movePlayer(player, Direction.RIGHT);
        assertEquals(new Coordinates(1, 1), player.getCoordinates());
    }

    @Test
    public void testMovePlayerInvalidMovement() {
        MazeModelImplementation maze = createMazeWithWalls();

        Player player = new PlayerImplementation(new Coordinates(3, 3));

        assertFalse(maze.isMoveValid(player, Direction.UP));
        assertFalse(maze.isMoveValid(player, Direction.LEFT));
        assertFalse(maze.isMoveValid(player, Direction.DOWN));
        assertFalse(maze.isMoveValid(player, Direction.RIGHT));

        maze.movePlayer(player, Direction.UP);
        assertEquals(new Coordinates(3, 3), player.getCoordinates());
        maze.movePlayer(player, Direction.LEFT);
        assertEquals(new Coordinates(3, 3), player.getCoordinates());
        maze.movePlayer(player, Direction.DOWN);
        assertEquals(new Coordinates(3, 3), player.getCoordinates());
        maze.movePlayer(player, Direction.RIGHT);
        assertEquals(new Coordinates(3, 3), player.getCoordinates());
    }

    /**
     * 
     * @return A 7x7 maze with walls. Only he center and the exit and the
     *         entrance are empty.
     */
    private MazeModelImplementation createMazeWithWalls() {
        boolean[][] board = new boolean[7][7];
        board[3][1] = true;
        board[3][3] = true;
        board[3][5] = true;
        return new MazeModelImplementation(getGenerator(board), new Coordinates(1, 3), new Coordinates(5, 3),
                null, null);
    }

    @Test
    public void testNoPlayersMeansGameOver() {
        MazeModel maze = MazeModelFactory.createMaze(new DepthFirstGenerator(5, 5), null, null,
                null, null);
        assertTrue(maze.isGameOver());
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

        MazeModelImplementation maze = createEmptyMaze(5, players);
        assertTrue(maze.isGameOver());
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

        MazeModelImplementation maze = createEmptyMaze(5, players);
        assertFalse(maze.isGameOver());

        playerC.setCoordinates(new Coordinates(1, 1));
        assertFalse(maze.isGameOver());

        playerB.setCoordinates(new Coordinates(1, 1));
        assertFalse(maze.isGameOver());

        playerA.setCoordinates(new Coordinates(1, 1));
        assertFalse(maze.isGameOver());
    }

    private MazeModelImplementation createEmptyMaze(int n, Player[] players) {
        // Empty board
        boolean[][] board = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = true;
            }
        }
        return new MazeModelImplementation(getGenerator(board),
                new Coordinates(1, 1), new Coordinates(n - 1, 1), players, null);
    }

    @Test
    public void isPlayerAtExit() {
        Player playerA = new PlayerImplementation(1, 1);
        Player playerB = new PlayerImplementation(4, 2);
        Player playerC = new PlayerImplementation(3, 3);
        Player playerD = new PlayerImplementation(4, 1);

        Player[] players = {
                playerA,
                playerB,
                playerC,
                playerD
        };

        MazeModelImplementation maze = createEmptyMaze(5, players);
        assertFalse(maze.isPlayerAtExit(playerA));
        assertFalse(maze.isPlayerAtExit(playerB));
        assertFalse(maze.isPlayerAtExit(playerC));
        assertTrue(maze.isPlayerAtExit(playerD));
    }

    private BoardGenerator getGenerator(boolean[][] board) {
        return () -> board;
    }

}
