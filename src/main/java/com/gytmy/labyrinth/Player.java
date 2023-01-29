package com.gytmy.labyrinth;

public interface Player {

    public int getX();

    public int getY();

    public int[] getCoordinates();

    public void setX(int x);

    public void setY(int y);

    public void setCoordinates(int[] coordinates);

    public void move(Direction direction);
}
