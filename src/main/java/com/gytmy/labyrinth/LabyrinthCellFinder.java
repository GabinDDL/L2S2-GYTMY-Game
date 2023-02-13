package com.gytmy.labyrinth;

import java.util.LinkedList;
import java.util.Queue;

import com.gytmy.utils.Vector2;

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
    private Queue<Vector2> queue;

    public LabyrinthCellFinder(boolean[][] board) {
        this.board = board;
    }

    public Vector2 getFurthestCell(Vector2 start) {

        initializeVariables(start);

        Vector2 furthestCell = null;

        while (!queue.isEmpty()) {
            Vector2 current = queue.remove();
            for (Direction direction : Direction.values()) {
                handleNeighbor(current, direction);
            }
            if (queue.isEmpty()) {
                furthestCell = current;
            }
        }

        return furthestCell;
    }

    private void initializeVariables(Vector2 start) {
        queue = new LinkedList<>();
        visited = new boolean[board.length][board[0].length];

        queue.add(start);
        visited[start.getY()][start.getX()] = true;
    }

    private void handleNeighbor(Vector2 current, Direction direction) {
        Vector2 newPosition = new Vector2(current.getX() + direction.getStep(), current.getY());
        if (direction == Direction.UP || direction == Direction.DOWN) {
            newPosition = new Vector2(current.getX(), current.getY() + direction.getStep());
        }

        if (isPossibleNeighbor(newPosition)) {
            queue.add(newPosition);
            visited[newPosition.getY()][newPosition.getX()] = true;
        }
    }

    private boolean isPossibleNeighbor(Vector2 position) {
        return isInsideBoard(position) && !visited[position.getY()][position.getX()] && !isWall(position);
    }

    private boolean isInsideBoard(Vector2 position) {
        return position.getX() >= 0 && position.getX() < board[0].length && position.getY() >= 0
                && position.getY() < board.length;
    }

    private boolean isWall(Vector2 position) {
        return !board[position.getY()][position.getX()];
    }

    public Vector2 getRandomCell() {
        while (true) {
            Vector2 randomCell = generateRandomCell();
            if (!isWall(randomCell)) {
                return randomCell;
            }
        }
    }

    private Vector2 generateRandomCell() {
        int x = (int) (Math.random() * board[0].length);
        int y = (int) (Math.random() * board.length);
        return new Vector2(x, y);
    }

}
