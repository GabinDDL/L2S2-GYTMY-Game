package com.gytmy.labyrinth;

import java.util.Arrays;

public class LabyrinthModel1D implements InterfaceLabyrinthModel {

    private boolean board[]; // 1D Board is represented by a segment
    private int start; // The index of the entrance
    private int end; // The index of the exit

    public LabyrinthModel1D(boolean[] board, int start, int end) {
        this.board = board;
        this.start = start;
        this.end = end;
    }

    public LabyrinthModel1D(int length) {
        initBoard(length);
        start = 0;
        end = length - 1;
    }

    /**
     * Initializes the 1-Dimensional board with the given length
     * by filling with `true` as it is a line without walls
     * 
     * @param length of the labyrinth
     */
    public void initBoard(int length) {
        board = new boolean[length];
        Arrays.fill(board, true);
    }

    @Override
    public boolean[] getBoard() {
        return board;
    }

    @Override
    public boolean isMoveValid(Player player, Direction direction) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void movePlayer(Player player, Direction direction) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isPlayerAtExit(Player player) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isGameOver() {
        // TODO Auto-generated method stub
        return false;
    }

}
