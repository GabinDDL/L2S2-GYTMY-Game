package com.gytmy.labyrinth;

import java.awt.Color;

import com.gytmy.utils.Vector2;

public interface Player {

  public static final Vector2 UNINITIALIZED_VECTOR2 = new Vector2(
      Vector2.UNINITIALIZED_COORDINATE,
      Vector2.UNINITIALIZED_COORDINATE);
  public static final String UNNAMED_PLAYER = "UNNAMED PLAYER";
  public static final Color UNINITIALIZED_COLOR = Color.MAGENTA;

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

  public static void initAllPlayersCoordinates(Vector2 initialCell, Player... players) {

    for (Player player : players) {
      player.setCoordinates(initialCell);
    }

  }

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
