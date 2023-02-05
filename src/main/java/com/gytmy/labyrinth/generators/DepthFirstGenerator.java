package com.gytmy.labyrinth.generators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.gytmy.utils.Vector2;
import com.gytmy.utils.ArrayOperations;

/**
 * A generator that uses a random depth-first search to generate a maze.
 */
public class DepthFirstGenerator implements BoardGenerator {

    Set<Vector2> visited = new HashSet<>();
    Stack<Vector2> stack = new Stack<>();
    Vector2 currentPosition = new Vector2(0, 0);
    int width;
    int height;

    @Override
    public boolean[][] generate(int width, int height) {
        boolean[][] board = new boolean[width][height];

        this.width = width;
        this.height = height;

        // Start at a random position
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        // Mark the starting position as a cell
        currentPosition = new Vector2(x, y);
        stack.add(currentPosition.copy());

        while (!stack.isEmpty()) {
            ArrayOperations.printBoolean2DArray(board);
            currentPosition = stack.pop();

            List<Vector2> neighbors = getNonVisitedNeighbors();
            if (neighbors.isEmpty()) {
                continue;
            }

            board[currentPosition.getY()][currentPosition.getX()] = true;
            stack.push(currentPosition.copy());

            int randomNeighbor = (int) (Math.random() * neighbors.size());
            Vector2 next = neighbors.get(randomNeighbor);

            board[next.getY()][next.getX()] = true;
            visited.add(next);
            stack.push(next);
        }

        return board;
    }

    private List<Vector2> getNonVisitedNeighbors() {
        List<Vector2> neighbors = new ArrayList<>();
        if (currentPosition.getX() > 0
                && !visited.contains(new Vector2(currentPosition.getX() - 1, currentPosition.getY()))) {
            neighbors.add(new Vector2(currentPosition.getX() - 1, currentPosition.getY()));
        }
        if (currentPosition.getX() < width - 1
                && !visited.contains(new Vector2(currentPosition.getX() + 1, currentPosition.getY()))) {
            neighbors.add(new Vector2(currentPosition.getX() + 1, currentPosition.getY()));
        }
        if (currentPosition.getY() > 0
                && !visited.contains(new Vector2(currentPosition.getX(), currentPosition.getY() - 1))) {
            neighbors.add(new Vector2(currentPosition.getX(), currentPosition.getY() - 1));
        }
        if (currentPosition.getY() < height - 1
                && !visited.contains(new Vector2(currentPosition.getX(), currentPosition.getY() + 1))) {
            neighbors.add(new Vector2(currentPosition.getX(), currentPosition.getY() + 1));
        }
        return neighbors;
    }

}
