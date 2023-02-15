package com.gytmy.labyrinth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.gytmy.utils.Coordinates;

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
        Coordinates result = finder.getFurthestCell(new Coordinates(1, 3));

        assertEquals(result, new Coordinates(3, 3));
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
        Coordinates result = finder.getFurthestCell(new Coordinates(1, 3));

        assertEquals(result, new Coordinates(8, 1));
    }

}
