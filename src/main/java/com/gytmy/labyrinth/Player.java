package com.gytmy.labyrinth;

import com.gytmy.utils.Coordinates;

public interface Player {

    public int getX();

    public int getY();

    public Coordinates getCoordinates();

    public void setX(int x);

    public void setY(int y);

    public void setCoordinates(Coordinates coordinates);

    /**
     * Moves the player in the given direction
     * 
     * @param direction
     */
    public void move(Direction direction);
}
