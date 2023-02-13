package com.gytmy.labyrinth;

import java.awt.Color;

import com.gytmy.utils.Vector2;

public interface Player {

  public int getX();

  public int getY();

  public Vector2 getCoordinates();

  public String getName();

  public Color getColor();

  public boolean isReady();

  public static boolean areAllPlayersReady(Player... players) {
    for (Player player : players) {
      if (player == null || !player.isReady())
        return false;
    }
    return true;
  }

  public void setX(int x);

  public void setY(int y);

  public void setCoordinates(Vector2 coordinates);

  public void setName(String name);

  public void setColor(Color color);

  public void setReady(boolean ready);

  /**
   * Moves the player in the given direction
   * 
   * @param direction
   */
  public void move(Direction direction);
}
