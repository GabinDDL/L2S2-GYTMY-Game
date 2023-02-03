package com.gytmy.labyrinth;

import com.gytmy.labyrinth.generators.BoardGenerator;
import com.gytmy.utils.ArrayOperations;
import com.gytmy.utils.Vector2;

public class LabyrinthModelImplementation implements LabyrinthModel {

    protected boolean[][] board;
    protected Vector2 initialCell;
    protected Vector2 exitCell;
    protected Player[] players;

    public LabyrinthModelImplementation(boolean[][] board, Vector2 initialCell, Vector2 exitCell, Player[] players) {
        handleNullArguments(board, initialCell, exitCell);
        handleSmallBoard(board);
        this.board = ArrayOperations.booleanCopy2D(board);

        handleInvalidCells(initialCell, exitCell);
        this.initialCell = initialCell;
        this.exitCell = exitCell;

        this.players = players;
    }

    public LabyrinthModelImplementation(BoardGenerator generator, Vector2 size, Vector2 initialCell, Vector2 exitCell,
            Player[] players) {
        this(generator.generate(size.getX(), size.getY()), initialCell, exitCell, players);
    }

    private void handleNullArguments(boolean[][] board, Vector2 initialCell, Vector2 exitCell) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null");
        }
        if (initialCell == null) {
            throw new IllegalArgumentException("Initial cell cannot be null");
        }
        if (exitCell == null) {
            throw new IllegalArgumentException("Exit cell cannot be null");
        }
    }

    private void handleSmallBoard(boolean[][] board) {
        if (board.length < 3) {
            throw new IllegalArgumentException("Board must have at least 3 rows");
        }
        if (board[0].length < 3) {
            throw new IllegalArgumentException("Board must have at least 3 columns");
        }
    }

    private void handleInvalidCells(Vector2 initialCell, Vector2 exitCell) {
        if (isOutsideBounds(initialCell)) {
            throw new IllegalArgumentException("Initial cell is outside the board");
        }

        if (isOutsideBounds(exitCell)) {
            throw new IllegalArgumentException("Exit cell is outside the board");
        }

        if (!board[(int) initialCell.getY()][(int) initialCell.getX()]) {
            throw new IllegalArgumentException("Initial cell is a wall");
        }

        if (!board[(int) exitCell.getY()][(int) exitCell.getX()]) {
            throw new IllegalArgumentException("Exit cell is a wall");
        }

        if (initialCell.equals(exitCell)) {
            throw new IllegalArgumentException("Initial and exit cells cannot be the same");
        }
    }

    private boolean isOutsideBounds(Vector2 cell) {
        return cell.getX() < 0 || cell.getX() >= board[0].length ||
                cell.getY() < 0 || cell.getY() >= board.length;
    }

    @Override
    public boolean[][] getBoard() {
        if (board == null) {
            return new boolean[0][0];
        }
        return ArrayOperations.booleanCopy2D(board);
    }

    public Vector2 getExitCell() {
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
