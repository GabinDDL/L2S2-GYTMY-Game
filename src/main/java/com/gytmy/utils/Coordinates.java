package com.gytmy.utils;

import java.util.Objects;

public class Coordinates {

  private int x;
  private int y;

  public static final int UNINITIALIZED_COORDINATE = -1;

  public Coordinates(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Coordinates() {
    this(UNINITIALIZED_COORDINATE, UNINITIALIZED_COORDINATE);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  /**
   * @return A copy of this vector
   */
  public Coordinates copy() {
    return new Coordinates(x, y);
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  /**
   * @param obj
   * @return true if the two vectors have the same coordinates
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Coordinates other = (Coordinates) obj;

    return this.x == other.x && this.y == other.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

}
