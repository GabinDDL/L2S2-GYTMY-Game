package com.gytmy.labyrinth;

import com.gytmy.utils.Vector2;

public interface Player {

    // TODO: Add Names, Colors, boolean Ready to players

    public int getX();

    public int getY();

    public Vector2 getCoordinates();

    public void setX(int x);

    public void setY(int y);

    public void setCoordinates(Vector2 coordinates);

    /**
     * Moves the player in the given direction
     * 
     * @param direction
     */
    public void move(Direction direction);
}
