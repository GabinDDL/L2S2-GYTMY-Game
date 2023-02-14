package com.gytmy.labyrinth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.gytmy.utils.Vector2;

public class TestLabyrinthCellFinder {

    @Test
    public void testGetFurthestCell5x5() {
        boolean[][] board = new boolean[][] {
                { false, false, false, false, false },
                { false, true, true, true, false },
                { false, true, false, true, false },
                { false, true, false, true, false },
                { false, false, false, false, false }
        };

        LabyrinthCellFinder finder = new LabyrinthCellFinder(board);
        Vector2 result = finder.getFurthestCell(new Vector2(1, 3));

        assertEquals(result, new Vector2(3, 3));
    }

    @Test
    public void testGetFurthestCell10x10() {
        boolean[][] board = new boolean[][] {
                { false, false, false, false, false, false, false, false, false, false },
                { false, true, true, true, true, true, true, false, true, false },
                { false, true, false, false, false, false, false, false, true, false },
                { false, true, false, true, true, true, true, true, true, false },
                { false, true, false, true, false, false, true, false, true, false },
                { false, true, false, true, false, false, true, false, true, false },
                { false, true, false, true, false, false, true, false, true, false },
                { false, true, false, true, false, false, true, false, true, false },
                { false, true, true, true, true, true, true, true, true, false },
                { false, false, false, false, false, false, false, false, false, false }
        };

        LabyrinthCellFinder finder = new LabyrinthCellFinder(board);
        Vector2 result = finder.getFurthestCell(new Vector2(1, 3));

        assertEquals(result, new Vector2(8, 1));
    }

    @Test
    public void testGetClosestToTopCell() {

        boolean[][] board = new boolean[][] {
                { false, false, false, false, false },
                { false, true, true, true, false },
                { false, true, true, true, false },
                { false, true, false, true, false },
                { false, false, false, false, false }
        };
        assertCorrectClosestToTopCell(board, new Vector2(1, 1));

        board[1] = new boolean[] { false, false, false, false, false };
        assertCorrectClosestToTopCell(board, new Vector2(1, 2));

        board[2][1] = false;
        assertCorrectClosestToTopCell(board, new Vector2(2, 2));
    }

    private void assertCorrectClosestToTopCell(boolean[][] board, Vector2 expected) {
        LabyrinthCellFinder finder = new LabyrinthCellFinder(board);
        Vector2 result = finder.getClosestToTopCell();
        assertEquals(expected, result);
    }

}
