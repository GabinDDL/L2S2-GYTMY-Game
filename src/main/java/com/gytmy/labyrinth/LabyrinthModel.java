package com.gytmy.labyrinth;

public interface LabyrinthModel {

    /**
     * Gets the board representing the Labyrinth,
     * If the board is 2-Dimensional, it returns a 2D Array of booleans
     * If the board is 1-Dimensional, it returns a 1D Array of booleans
     * 
     * true reprensents a walkable path
     * false represents a wall
     * 
     * @return the Board according to its Dimension
     */
    Object getBoard();

    /**
     * Checks if the given move is valid for the given player
     * depending on its position on the board
     * 
     * The criteria:
     * A move is valid when the future position of the player,
     * after the move, won't be outside of the board
     * and won't be a wall
     * 
     * @param player
     * @param direction of the move
     * @return true if the move is valid according to the criteria;
     *         false otherwise
     */
    boolean isMoveValid(Player player, Direction direction);

    /**
     * Moves the given player to the given direction
     * 
     * @param player
     * @param direction
     */
    void movePlayer(Player player, Direction direction);

    /**
     * Checks if the given player's position is on the exit's position
     * 
     * @param player
     * @return true if the player is at the exit;
     *         false otherwise
     */
    boolean isPlayerAtExit(Player player);

    /**
     * Checks if the game is over
     * 
     * @return true if the game has ended;
     *         false otherwise
     */
    boolean isGameOver();

}
