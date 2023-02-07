package com.gytmy.labyrinth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.gytmy.utils.Vector2;

public class TestLabyrinthCellFinder {

    @Test
    public void testgetFurthestCell5x5() {
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
    public void testgetFurthestCell10x10() {
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

}
