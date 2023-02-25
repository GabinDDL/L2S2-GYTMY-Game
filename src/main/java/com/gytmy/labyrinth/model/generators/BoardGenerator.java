package com.gytmy.labyrinth.model.generators;

import com.gytmy.utils.Coordinates;

/**
 * Interface for board generators. Used to generate a board for the labyrinth.
 * It is part of a Strategy pattern.
 */
public interface BoardGenerator {

    /**
     * Generates a board for the labyrinth.
     * 
     * @return a 2-dimensional array of booleans representing the board.
     */
    public boolean[][] generate();
}
