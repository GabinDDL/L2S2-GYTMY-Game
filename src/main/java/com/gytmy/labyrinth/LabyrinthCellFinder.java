package com.gytmy.labyrinth;

import java.util.LinkedList;
import java.util.Queue;

import com.gytmy.utils.Coordinates;

/**
 * This class is used to find the furthest cell from a given cell in a
 * labyrinth.
 * It is used to find the exit cell of the labyrinth.
 *
 * The algorithm used is a breadth-first search.
 * 
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Breadth-first_search">Breadth-first
 *      search</a>
 */
public class LabyrinthCellFinder {

  private boolean[][] board;
  private boolean[][] visited;
  private Queue<Coordinates> queue;

  public LabyrinthCellFinder(boolean[][] board) {
    this.board = board;
  }

  public Coordinates getFurthestCell(Coordinates start) {

    initializeVariables(start);

    Coordinates furthestCell = null;

    while (!queue.isEmpty()) {
      Coordinates current = queue.remove();
      for (Direction direction : Direction.values()) {
        handleNeighbor(current, direction);
      }
      if (queue.isEmpty()) {
        furthestCell = current;
      }
    }

    return furthestCell;
  }

  private void initializeVariables(Coordinates start) {
    queue = new LinkedList<>();
    visited = new boolean[board.length][board[0].length];

    queue.add(start);
    visited[start.getY()][start.getX()] = true;
  }

  private void handleNeighbor(Coordinates current, Direction direction) {
    Coordinates newPosition = new Coordinates(current.getX() + direction.getStep(), current.getY());
    if (direction == Direction.UP || direction == Direction.DOWN) {
      newPosition = new Coordinates(current.getX(), current.getY() + direction.getStep());
    }

    if (isPossibleNeighbor(newPosition)) {
      queue.add(newPosition);
      visited[newPosition.getY()][newPosition.getX()] = true;
    }
  }

  private boolean isPossibleNeighbor(Coordinates position) {
    return isInsideBoard(position) && !visited[position.getY()][position.getX()] && !isWall(position);
  }

  private boolean isInsideBoard(Coordinates position) {
    return position.getX() >= 0 && position.getX() < board[0].length && position.getY() >= 0
        && position.getY() < board.length;
  }

  private boolean isWall(Coordinates position) {
    return !board[position.getY()][position.getX()];
  }

  /**
   * Returns the closest cell to the top left corner of the board. It is used
   * to find the entrance cell of the labyrinth. The algorithm prioritizes
   * rows.
   *
   * @return the closes cell to the top left corner of the board
   */

  public Coordinates getClosestToTopCell() {
    for (int y = 1; y < board.length - 1; y++) {
      for (int x = 1; x < board[0].length - 1; x++) {
        if (board[y][x]) {
          return new Coordinates(x, y);
        }
      }
    }
    return null;
  }

}
