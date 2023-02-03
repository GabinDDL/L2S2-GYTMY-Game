package com.gytmy.labyrinth.generators;

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

}
