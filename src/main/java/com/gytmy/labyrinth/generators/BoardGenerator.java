package com.gytmy.labyrinth.generators;

/**
 * Interface for board generators. Used to generate a board for the labyrinth.
 * It is part of a Strategy pattern.
 */
public interface BoardGenerator {

    public boolean[][] generate(int width, int height);

}
