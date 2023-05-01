package com.gytmy.maze.model.generators;

/**
 * Generates an empty board.
 */
public class EmptyBoardGenerator implements BoardGenerator {

    private int width;
    private int height;

    public EmptyBoardGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean[][] generate() {
        boolean[][] board = new boolean[width][height];

        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                board[col][row] = true;
            }
        }

        return board;
    }

}
