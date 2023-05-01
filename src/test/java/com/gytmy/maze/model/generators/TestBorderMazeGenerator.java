package com.gytmy.maze.model.generators;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

public class TestBorderMazeGenerator {

    @Test
    public void testGenerate() {
        BoardGenerator generator = new BorderBoardGenerator(10, 10);
        boolean[][] board = generator.generate();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (row == 0 || row == 9 || col == 0 || col == 9) {
                    assertFalse(board[col][row]);
                } else {
                    assertTrue(board[col][row]);
                }
            }
        }
    }

}
