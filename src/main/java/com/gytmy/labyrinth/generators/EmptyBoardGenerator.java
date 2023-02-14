package com.gytmy.labyrinth.generators;

import com.gytmy.utils.Vector2;

/**
 * Generates an empty board.
 */
public class EmptyBoardGenerator implements BoardGenerator {

    @Override
    public boolean[][] generate(int width, int height) {
        boolean[][] board = new boolean[width][height];

        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
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
