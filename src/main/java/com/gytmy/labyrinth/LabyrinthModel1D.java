package com.gytmy.labyrinth;

import java.util.Arrays;

/* 
    Class representing the Model of a 1-Dimensional Labyrinth

    An array of dimension 1 containing booleans is used to reprensent it
    because it can be considered the same as a segmentend segment
 */
public class LabyrinthModel1D implements LabyrinthModel {

    private boolean[] board; // 1D Board is represented by a segment
    private Player[] players; // list of players

    /**
     * @param length of the 1D Labyrinth
     * @throws IllegalArgumentException
     */
    public LabyrinthModel1D(int length) throws IllegalArgumentException {

        if (length <= 0) {
            throw new IllegalArgumentException("Cannot initialize a labyrinth of size <= 0");
        }

        initBoard(length);
    }

    /**
     * Initializes the 1-Dimensional board with the given length
     * by filling it with `true` as it is a line without walls
     * 
     * @param length of the labyrinth
     */
    private void initBoard(int length) {
        board = new boolean[length];
        Arrays.fill(board, true);
    }

    @Override
    public boolean[] getBoard() {
        return board;
    }

    // TODO: Adapt the functions by using the walls" booleans instead of using
    // coordinates
    @Override
    public boolean isMoveValid(Player player, Direction direction) {
        switch (direction) {
            case LEFT:
                return player.getCoordinates()[0] > 0;
            case RIGHT:
                return player.getCoordinates()[0] < board.length - 1;
            default:
                return false;
        }
    }

    @Override
    public void movePlayer(Player player, Direction direction) {
        if (!isMoveValid(player, direction))
            return;

        player.move(direction);
    }

    @Override
    public boolean isPlayerAtExit(Player player) {
        int position = player.getCoordinates()[0];
        return position == getBoard().length - 1;
    }

    @Override
    public boolean isGameOver() {
        for (Player player : players) {
            if (!isPlayerAtExit(player))
                return false;
        }

        return true;
    }

}
