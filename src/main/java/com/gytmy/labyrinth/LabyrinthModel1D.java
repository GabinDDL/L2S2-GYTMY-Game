package com.gytmy.labyrinth;

import java.util.Arrays;

/* 
    TODO: 1D IS 2D IN REALITY
    Class representing the Model of a 1-Dimensional Labyrinth

    An array of dimension 1 containing booleans is used to reprensent it
    because it can be considered the same as a segmentend horizontal segment
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

    /**
     * Checks if the given player will end up outside of the labyrinth
     * if he makes the move with the given direction
     * 
     * @param player
     * @param direction
     * @return true i
     */
    private boolean isGoingOutside(Player player, Direction direction) {
        switch (direction) {
            case LEFT:
                return player.getCoordinates()[0] > 0;
            case RIGHT:
                return player.getCoordinates()[0] < board.length - 1;
            default:
                return false;
        }
    }

    /**
     * Checks if the given player will end up in a wall
     * if he makes the move with the given direction
     * 
     * Here the board is represented as a horizontal segment
     * so the only moves available are LEFT and RIGHT
     * 
     * @param player
     * @param direction
     * @return true
     */
    private boolean isGoingIntoWall(Player player, Direction direction) {
        int position = player.getCoordinates()[0];

        switch (direction) {
            case LEFT:
            case RIGHT:
                return board[position + direction.getStep()];
            default:
                return true;
        }
    }

    // TODO: Adapt the functions by using the walls" booleans instead of using
    // coordinates
    @Override
    public boolean isMoveValid(Player player, Direction direction) {
        if (isGoingOutside(player, direction))
            return false;

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
