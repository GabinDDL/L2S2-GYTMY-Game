package com.gytmy.maze.model.player;

import java.awt.Color;

import com.gytmy.maze.model.Direction;
import com.gytmy.utils.Coordinates;

public interface Player {

    public static final String UNNAMED_PLAYER = "UNNAMED PLAYER";
    public static final Color UNINITIALIZED_COLOR = Color.MAGENTA;

    public int getX();

    public int getY();

    public Coordinates getCoordinates();

    public String getName();

    public Color getColor();

    public int getNumberOfMovements();

    public int getTimePassedInSeconds();

    public void setX(int x);

    public void setY(int y);

    public void setCoordinates(Coordinates coordinates);

    public static void initAllPlayersCoordinates(Coordinates initialCell, Player... players) {

        for (Player player : players) {
            player.setCoordinates(initialCell);
        }

    }

    public void setName(String name);

    public void setColor(Color color);

    public void setTimePassedInSeconds(int timePassedInSeconds);

    /**
     * Moves the player in the given direction
     * 
     * @param direction
     */
    public void move(Direction direction);

}
