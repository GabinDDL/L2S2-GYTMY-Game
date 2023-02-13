package com.gytmy.labyrinth.generators;

import com.gytmy.utils.Vector2;

/**
 * Interface for board generators. Used to generate a board for the labyrinth.
 * It is part of a Strategy pattern.
 */
public interface BoardGenerator {

    public boolean[][] generate(int width, int height);

    public boolean[][] generate(int width, int height, Vector2 initialCell);

}
