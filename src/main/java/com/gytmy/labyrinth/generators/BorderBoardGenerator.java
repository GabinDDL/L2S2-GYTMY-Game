package com.gytmy.labyrinth.generators;

import com.gytmy.utils.Vector2;

/**
 * Generates a board with a one cell border.
 */
public class BorderBoardGenerator implements BoardGenerator {

    @Override
    public boolean[][] generate(int width, int height) {
        boolean[][] board = new boolean[width][height];

        for (int row = 1; row < width - 1; row++) {
            for (int col = 1; col < height - 1; col++) {
                board[col][row] = true;
            }
        }

        return board;
    }

    @Override
    public boolean[][] generate(int width, int height, Vector2 initialCell) {
        return generate(width, height);
    }

}
