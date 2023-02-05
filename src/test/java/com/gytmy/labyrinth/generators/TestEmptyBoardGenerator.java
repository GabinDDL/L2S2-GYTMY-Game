package com.gytmy.labyrinth.generators;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestEmptyBoardGenerator {

    @Test
    public void testGenerate() {
        BoardGenerator generator = new EmptyBoardGenerator();
        boolean[][] board = generator.generate(10, 10);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                assertTrue(board[col][row]);
            }
        }
    }

}
