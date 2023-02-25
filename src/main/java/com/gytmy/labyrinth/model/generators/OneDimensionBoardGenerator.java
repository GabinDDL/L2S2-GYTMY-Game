package com.gytmy.labyrinth.model.generators;

import java.util.Arrays;

/**
 * A Labyrinth of dimension 1 is represented as a segment
 * without obstacles by using a 2-dimensional array of booleans,
 * of height 3 and length (lengthPath + 2).
 */
public class OneDimensionBoardGenerator implements BoardGenerator {

    private int length;

    public OneDimensionBoardGenerator(int length) {
        this.length = length;
    }

    @Override
    public boolean[][] generate() {

        if (length <= 1) {
            throw new IllegalArgumentException(
                    "Cannot initialize a labyrinth of size <= 1");
        }
        // Do not forget the left and right borders
        boolean[][] board = new boolean[3][length + 2];

        for (int row = 0; row < board.length; ++row) {
            // The walkable path with the first cell being a wall
            if (row == 1) {
                Arrays.fill(board[row], true);
                board[row][0] = false;
                board[row][length + 1] = false;
            } else {
                Arrays.fill(board[row], false); // Top and Bottom walls
            }
        }

        return board;
    }

}
