package com.gytmy.labyrinth;

import java.util.Arrays;

/* 
    Class representing the Model of a 1-Dimensional Labyrinth

    The Labyrinth is associated with an array of players.

    A Labyrinth of dimension 1 is reprensented as a segment
    without obstacles by using a 2-dimensional array of booleans,
    of height 3 and length length.
     
    true reprensents a walkable path
    false represents a wall
   
    Remark: 
    We implement it as a 2D Array of booleans of height 3
    where the first and last line are filled with false 
    to represent the borders (which are walls) and 
    the middle line is filled with true to represent a walkable path
 */
public class LabyrinthModel1D extends LabyrinthModelImplementation {

    private boolean[][] board;
    private Player[] players; // array of players

    /**
     * @param length of the 1D Labyrinth
     * @throws IllegalArgumentException
     */
    public LabyrinthModel1D(int length, Player[] players)
            throws IllegalArgumentException {

        if (length <= 0) {
            throw new IllegalArgumentException(
                    "Cannot initialize a labyrinth of size <= 0");
        }

        this.players = players;
        initBoard(length);
    }

    /**
     * Initializes the 1-Dimensional labyrinth of the given length
     * 
     * @param length of the labyrinth
     */
    private void initBoard(int length) {
        board = new boolean[3][length];

        for (int line = 0; line < board.length; line++) {
            // The borders
            if (line != 0 && line != board.length - 1) {
                Arrays.fill(board[line], true);
            } else
                Arrays.fill(board[line], false); // The walkable path
        }
    }

    @Override
    public boolean[][] getBoard() {
        if (board == null) {
            return null;
        }

        boolean[][] result = new boolean[board.length][];
        for (int i = 0; i < board.length; i++) {
            result[i] = Arrays.copyOf(board[i], board[i].length);
        }

        return result;
    }

    /**
     * Checks if the given player will end up outside of the labyrinth
     * if he makes the move with the given direction
     * 
     * Here the board is represented as a horizontal segment
     * so it is possible to move horizontally
     * but moving vertically isn't because there are borders which are walls
     * 
     * @param player
     * @param direction
     * @return true if
     */
    private boolean isGoingOutside(Player player, Direction direction) {
        switch (direction) {
            case LEFT:
                return player.getCoordinates()[0] <= 0;
            case RIGHT:
                return player.getCoordinates()[0] >= board.length - 1;
            default:
                return false;
        }
    }

    /**
     * Checks if the given player will end up in a wall
     * if he makes the move with the given direction
     * 
     * Here the board is represented as a horizontal segment
     * so it is possible to move horizontally
     * but moving vertically isn't because there are borders which are walls
     * 
     * @param player
     * @param direction
     * @return true
     */
    private boolean isGoingIntoWall(Player player, Direction direction) {
        int newPosition = player.getCoordinates()[0] + direction.getStep();

        switch (direction) {
            case LEFT:
            case RIGHT:
                return board[1][newPosition];
            default:
                return true;
        }
    }

    @Override
    public boolean isMoveValid(Player player, Direction direction) {
        return !isGoingOutside(player, direction) &&
                !isGoingIntoWall(player, direction);
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
        return position == getBoard()[1].length - 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gytmy.labyrinth.LabyrinthModel#isGameOver()
     * 
     * Here, the game is considered over when
     * all the players have made it to the exit
     */
    @Override
    public boolean isGameOver() {
        if (players == null) {
            return false;
        }

        for (Player player : players) {
            if (!isPlayerAtExit(player))
                return false;
        }

        return true;
    }

}
