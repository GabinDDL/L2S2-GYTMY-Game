package com.gytmy.labyrinth.model;

import java.util.LinkedList;
import java.util.Queue;

import com.gytmy.utils.Coordinates;

/**
 * This class is used to find information abot the cells in the board. It is
 * used to find the entrance and exit cells of the labyrinth. It is also used to
 * find the distance between two cells.
 */
public class LabyrinthCellFinder {

    private boolean[][] board;
    private boolean[][] visited;
    private Queue<Coordinates> queue;

    public LabyrinthCellFinder(boolean[][] board) {
        this.board = board;
    }

    /**
     * Returns the furthest cell from a given cell in the board. It is used to
     * find the exit cell of the labyrinth. It uses a variation of breadth-first
     * search.
     *
     * @param start the starting cell
     * @return the furthest cell from the starting cell
     * @see <a href=
     *      "https://en.wikipedia.org/wiki/Breadth-first_search">Breadth-first
     *      search</a>
     */
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
     * Returns the distance between two cells in the board. It
     * is uses the breadth-first search.
     * 
     * @param start the starting cell
     * @param end   the ending cell
     * @return the distance between the two cells
     * @see <a href=
     *      "https://en.wikipedia.org/wiki/Breadth-first_search">Breadth-first
     *      search</a>
     */
    public int getDistance(Coordinates start, Coordinates end) {
        initializeVariables(start);

        int distance = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Coordinates current = queue.remove();
                if (current.equals(end)) {
                    return distance;
                }
                for (Direction direction : Direction.values()) {
                    handleNeighbor(current, direction);
                }
            }
            distance++;
        }

        return -1;
    }

    /**
     * Returns the closest cell to the top left corner of the board. It is used
     * to find the entrance cell of the labyrinth. The algorithm prioritizes
     * rows.
     *
     * @return the closes cell to the top left corner of the board
     */

    public Coordinates getClosestToTopCell() {
        for (int row = 1; row < board.length - 1; row++) {
            for (int col = 1; col < board[0].length - 1; col++) {
                if (board[row][col]) {
                    return new Coordinates(col, row);
                }
            }
        }
        return null;
    }

}
