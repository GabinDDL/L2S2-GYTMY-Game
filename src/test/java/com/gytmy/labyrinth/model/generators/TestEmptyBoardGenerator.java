package com.gytmy.labyrinth.model.generators;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestEmptyBoardGenerator {

    @Test
    public void testGenerate() {
        BoardGenerator generator = new EmptyBoardGenerator(10, 10);
        boolean[][] board = generator.generate();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                assertTrue(board[col][row]);
            }
        }
    }

}
