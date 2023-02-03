package com.gytmy.labyrinth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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
}
