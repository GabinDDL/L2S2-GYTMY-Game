package com.gytmy.labyrinth;

import java.util.Arrays;

import com.gytmy.utils.Coordinates;

/* 
    Class representing the Model of a 1-Dimensional Labyrinth

    The Labyrinth is associated with an array of players.

    A Labyrinth of dimension 1 is represented as a segment
    without obstacles by using a 2-dimensional array of booleans,
    of height 3 and length (lengthPath + 1)
    
    true represents a walkable path
    false represents a wall

    Remarks: 
    We implement it as a 2D Array of booleans of height 3 where 
    the first and last row are filled with false to represent
    the top and bottom borders (which are walls) and 
    the middle row is filled with true except in the first cell
    to represent a walkable path
    
    The entrance is board[1][1]
    The exit is exitCell
 */
public class LabyrinthModel1D extends LabyrinthModelImplementation {

  /**
   * @param lengthPath of the 1D Labyrinth
   * @throws IllegalArgumentException
   */
  public LabyrinthModel1D(int lengthPath, Player[] players)
      throws IllegalArgumentException {

    super(initBoard(lengthPath), new Coordinates(1, 1), new Coordinates(lengthPath, 1), players);

  }

  /**
   * Initializes the 1-Dimensional labyrinth of the given length
   * 
   * @param length of the labyrinth
   */
  private static boolean[][] initBoard(int length) {

    if (length <= 1) {
      throw new IllegalArgumentException(
          "Cannot initialize a labyrinth of size <= 1");
    }
    // Do not forget the left and right borders
    boolean[][] board = new boolean[3][length + 1];

    for (int row = 0; row < board.length; ++row) {
      // The walkable path with the first cell being a wall
      if (row == 1) {
        Arrays.fill(board[row], true);
        board[row][0] = false;
      } else
        Arrays.fill(board[row], false); // Top and Bottom walls
    }

    return board;
  }

}
