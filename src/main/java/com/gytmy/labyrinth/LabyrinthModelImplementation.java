package com.gytmy.labyrinth;

import com.gytmy.labyrinth.generators.BoardGenerator;
import com.gytmy.labyrinth.generators.DepthFirstGenerator;
import com.gytmy.utils.Boolean2DArraysOperations;
import com.gytmy.utils.Coordinates;

/**
 * Implementation of the LabyrinthModel interface. It is used to represent the
 * labyrinth.
 * 
 * In order to create a labyrinth, you can either pass a board to the
 * constructor or use a BoardGenerator to generate a board. The board is
 * represented as a 2D array of booleans. A true value means that the cell is
 * not a wall, a false value means that the cell is a wall.
 * 
 * The default strategy to generate the labyrinth is DepthFirstGenerator.
 * 
 * The initial cell is the cell where the players start. The exit cell is the
 * cell where the players must reach in order to win the game.
 * 
 * The players are represented as an array of Player objects.
 * 
 */
public class LabyrinthModelImplementation implements LabyrinthModel {

  protected boolean[][] board;
  protected Coordinates initialCell;
  protected Coordinates exitCell;

  protected Player[] players;

  public LabyrinthModelImplementation(BoardGenerator generator, int width, int height, Coordinates initialCell,
      Coordinates exitCell,
      Player[] players) {
    this(generator.generate(width, height, initialCell), initialCell, exitCell, players);
  }

  public LabyrinthModelImplementation(BoardGenerator generator, int width, int height, Coordinates initialCell,
      Player[] players) {
    this(generator.generate(width, height, initialCell), initialCell, null, players);
  }

  public LabyrinthModelImplementation(BoardGenerator generator, int width, int height, Player[] players) {
    this(generator.generate(width, height), null, null, players);
  }

  public LabyrinthModelImplementation(int width, int height, Player[] players) {
    this(new DepthFirstGenerator(), width, height, players);
  }

  public LabyrinthModelImplementation(boolean[][] board, Coordinates initialCell, Coordinates exitCell,
      Player[] players) {
    handleNullArguments(board);
    handleInvalidBoardSize(board);
    this.board = Boolean2DArraysOperations.copy(board);
    this.initialCell = determineInitialCell(initialCell);
    this.exitCell = determineExitCell(exitCell);
    this.players = players;
  }

  /**
   * Checks if the arguments are null. If they are, it throws an
   * IllegalArgumentException.
   * 
   * @param board
   */
  private void handleNullArguments(boolean[][] board) {
    if (board == null) {
      throw new IllegalArgumentException("Board cannot be null");
    }
  }

  /**
   * Checks if the board is too small. If it is, it throws an
   * IllegalArgumentException.
   * 
   * @param board
   */
  private void handleInvalidBoardSize(boolean[][] board) {
    if (board.length < 3) {
      throw new IllegalArgumentException("Board must have at least 3 rows");
    }
    if (board[0].length < 3) {
      throw new IllegalArgumentException("Board must have at least 3 columns");
    }
  }

  /**
   * Determines the initial cell. If the initial cell is null, it gets a
   * random non-wall cell. If the initial cell is outside the board or is a
   * wall, it throws an IllegalArgumentException.
   * 
   * @param initialCell
   * @return the initial cell
   */
  private Coordinates determineInitialCell(Coordinates initialCell) {
    if (initialCell == null) {
      // Get a random non-wall cell
      LabyrinthCellFinder finder = new LabyrinthCellFinder(board);
      initialCell = finder.getClosestToTopCell();
    }
    handleInvalidStartCell(initialCell);
    return initialCell;
  }

  /**
   * Checks if the initial and exit cells are valid. If they are not, it throws
   * an IllegalArgumentException.
   * 
   * @param initialCell
   */
  private void handleInvalidStartCell(Coordinates initialCell) {
    if (isOutsideBounds(initialCell)) {
      throw new IllegalArgumentException("Initial cell is outside the board");
    }

    if (!board[initialCell.getY()][initialCell.getX()]) {
      throw new IllegalArgumentException("Initial cell is a wall");
    }

    if (initialCell.equals(exitCell)) {
      throw new IllegalArgumentException("Initial and exit cells cannot be the same");
    }
  }

  /**
   * Determines the exit cell. If it is null, it will be set to the furthest
   * cell from the initial cell.
   * 
   * @param exitCell\
   * @return the exit cell
   */
  private Coordinates determineExitCell(Coordinates exitCell) {
    if (exitCell == null) {
      LabyrinthCellFinder finder = new LabyrinthCellFinder(board);
      exitCell = finder.getFurthestCell(initialCell);
    }
    handleInvalidExitCell(exitCell);
    return exitCell;
  }

  /**
   * Checks if the exit cell is valid. If it is not, it throws an
   * IllegalArgumentException.
   * 
   * @param exitCell
   */
  private void handleInvalidExitCell(Coordinates exitCell) {
    if (isOutsideBounds(exitCell)) {
      throw new IllegalArgumentException("Exit cell is outside the board");
    }

    if (!board[exitCell.getY()][exitCell.getX()]) {
      throw new IllegalArgumentException("Exit cell is a wall");
    }
    if (exitCell.equals(initialCell)) {
      throw new IllegalArgumentException("Initial and exit cells cannot be the same");
    }
  }

  private boolean isOutsideBounds(Coordinates cell) {
    return cell.getX() < 0 || cell.getX() >= board[0].length ||
        cell.getY() < 0 || cell.getY() >= board.length;
  }

  @Override
  public boolean[][] getBoard() {
    if (board == null) {
      return new boolean[0][0];
    }
    return Boolean2DArraysOperations.copy(board);
  }

  @Override
  public Coordinates getInitialCell() {
    return initialCell;
  }

  @Override
  public Coordinates getExitCell() {
    return exitCell;
  }

  @Override
  public boolean isMoveValid(Player player, Direction direction) {
    return !isGoingOutside(player, direction) &&
        !isGoingIntoWall(player, direction);
  }

  /**
   * Checks if the given player will end up outside of the labyrinth
   * if he makes the move with the given direction
   * 
   * Here the board is represented as a horizontal segment
   * so it is possible to move horizontally
   * but moving vertically isn't because there are borders which are walls
   * 
   * @param player
   * @param direction
   * @return true if the move will move the player outside;
   *         otherwise false
   */
  private boolean isGoingOutside(Player player, Direction direction) {
    switch (direction) {
      case UP:
        return player.getY() <= 0;
      case DOWN:
        return player.getY() >= board.length - 1;
      case LEFT:
        return player.getX() <= 0;
      case RIGHT:
        return player.getX() >= board[1].length - 1;
      default:
        return false;
    }
  }

  /**
   * Checks if the given player will end up in a wall
   * if he makes the move with the given direction
   * 
   * Here the board is represented as a horizontal segment
   * so it is possible to move horizontally
   * but moving vertically isn't because there are borders which are walls
   * 
   * @param player
   * @param direction
   * @return true if newPosition is a wall;
   *         false otherwise
   */
  private boolean isGoingIntoWall(Player player, Direction direction)
      throws IllegalArgumentException {
    int newX = player.getX();
    int newY = player.getY();
    switch (direction) {
      case UP:
      case DOWN:
        newY = player.getY() + direction.getStep();
        break;
      case LEFT:
      case RIGHT:
        newX = player.getX() + direction.getStep();
        break;
      default:
        throw new IllegalArgumentException("Direction " + direction + " is not supported");
    }
    return isWall(newX, newY);
  }

  /**
   * @param x
   * @param y
   * @return true if there is a wall at the given coordinates;
   *         false otherwise
   */
  private boolean isWall(int x, int y) {
    return !board[y][x];
  }

  @Override
  public void movePlayer(Player player, Direction direction) {
    if (isMoveValid(player, direction)) {
      player.move(direction);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gytmy.labyrinth.LabyrinthModel#isGameOver()
   * 
   * Here, the game is considered over when
   * all the players have made it to the exit
   * 
   * May be modified to fit the definition of a game
   */
  @Override
  public boolean isGameOver() {
    if (players == null || players.length == 0) {
      return true;
    }

    for (Player player : players) {
      if (!isPlayerAtExit(player))
        return false;
    }

    return true;
  }

  @Override
  public boolean isPlayerAtExit(Player player) {
    return player.getCoordinates().equals(exitCell);
  }

}
