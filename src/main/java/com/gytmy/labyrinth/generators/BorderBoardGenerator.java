package com.gytmy.labyrinth.generators;

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

}
