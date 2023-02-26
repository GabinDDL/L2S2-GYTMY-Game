package com.gytmy.labyrinth.model.generators;

/**
 * Generates a board with a one cell border.
 */
public class BorderBoardGenerator implements BoardGenerator {

    private int width;
    private int height;

    public BorderBoardGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean[][] generate() {
        boolean[][] board = new boolean[width][height];

        for (int row = 1; row < width - 1; row++) {
            for (int col = 1; col < height - 1; col++) {
                board[col][row] = true;
            }
        }

        return board;
    }

}
