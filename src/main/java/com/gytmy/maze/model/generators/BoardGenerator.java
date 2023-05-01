package com.gytmy.maze.model.generators;

/**
 * Interface for board generators. Used to generate a board for the maze.
 * It is part of a Strategy pattern.
 */
public interface BoardGenerator {

    /**
     * Generates a board for the maze.
     * 
     * @return a 2-dimensional array of booleans representing the board.
     */
    public boolean[][] generate();
}
