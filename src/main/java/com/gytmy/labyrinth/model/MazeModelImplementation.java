package com.gytmy.labyrinth.model;

import java.util.ArrayList;
import java.util.List;

import com.gytmy.labyrinth.model.generators.BoardGenerator;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.score.ScoreCalculator;
import com.gytmy.labyrinth.model.score.ScoreCalculatorFactory;
import com.gytmy.labyrinth.model.score.ScoreInfo;
import com.gytmy.labyrinth.model.score.ScoreType;
import com.gytmy.labyrinth.model.score.SimpleKeyboardScoreInfo;
import com.gytmy.utils.Boolean2DArraysOperations;
import com.gytmy.utils.CellFinder;
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
public class MazeModelImplementation implements MazeModel {

    private boolean[][] board;
    private Coordinates initialCell;
    private Coordinates exitCell;

    private int minimumPathLength;

    private Player[] players;

    private ScoreType scoreType;

    public MazeModelImplementation(BoardGenerator generator, Coordinates initialCell, Coordinates exitCell,
            Player[] players, ScoreType scoreType) {
        this.board = generator.generate();
        handleNullArguments();
        handleInvalidBoardSize();
        this.initialCell = determineInitialCell(initialCell);
        this.exitCell = determineExitCell(exitCell);
        this.players = players;
        this.scoreType = scoreType;
        this.minimumPathLength = calculateMinimumPathLength();
    }

    /**
     * Checks if the arguments are null. If they are, it throws an
     * IllegalArgumentException.
     * 
     * @param board
     */
    private void handleNullArguments() {
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
    private void handleInvalidBoardSize() {
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
            CellFinder finder = new CellFinder(board);
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
            CellFinder finder = new CellFinder(board);
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

    private int calculateMinimumPathLength() {
        CellFinder finder = new CellFinder(board);
        return finder.getDistance(initialCell, exitCell);
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
    public Player[] getPlayers() {
        return players;
    }

    @Override
    public int getNbPlayers() {
        return players.length;
    }

    public List<Player> getPlayersAtCoordinates(Coordinates coordinates) {
        List<Player> res = new ArrayList<>();
        for (Player player : players) {
            if (player != null &&
                    coordinates.equals(player.getCoordinates())) {
                res.add(player);
            }
        }

        return res;
    }

    @Override
    public boolean isInitialCell(Coordinates coordinates) {
        return coordinates.equals(initialCell);
    }

    @Override
    public boolean isExitCell(Coordinates coordinates) {
        return coordinates.equals(exitCell);
    }

    @Override
    public boolean movePlayer(Player player, Direction direction) {
        if (!isMoveValid(player, direction)) {
            return false;
        }

        player.move(direction);
        return true;
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
        int newCol = player.getX();
        int newRow = player.getY();
        switch (direction) {
            case UP:
            case DOWN:
                newRow = player.getY() + direction.getStep();
                break;
            case LEFT:
            case RIGHT:
                newCol = player.getX() + direction.getStep();
                break;
            default:
                throw new IllegalArgumentException("Direction " + direction + " is not supported");
        }
        return isWall(newCol, newRow);
    }

    /**
     * @param x
     * @param y
     * @return true if there is a wall at the given coordinates;
     *         false otherwise
     */
    public boolean isWall(int x, int y) {
        return !board[y][x];
    }

    public boolean isWall(Coordinates coordinates) {
        return isWall(coordinates.getX(), coordinates.getY());
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

    @Override
    public int getMinimumPathLength() {
        return minimumPathLength;
    }

    @Override
    public int getScore(Player player) {
        ScoreCalculator calculator = getScoreCalculator(player);
        return calculator.getScore();
    }

    @Override
    public ScoreCalculator getScoreCalculator(Player player) {
        return getScoreCalculator(scoreType, player);
    }

    @Override
    public ScoreCalculator getScoreCalculator(ScoreType type, Player player) {
        ScoreInfo info;

        switch (type) {
            case SIMPLE_KEYBOARD:
                info = new SimpleKeyboardScoreInfo(this, player);
                break;
            default:
                throw new IllegalArgumentException("Score type " + type + " is not supported");
        }

        return ScoreCalculatorFactory.getScoreCalculator(info);
    }

}
