package com.gytmy.labyrinth.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import com.gytmy.utils.Boolean2DArraysOperations;
import com.gytmy.utils.Vector2;

/**
 * A generator that uses a random depth-first search to generate a maze.
 */
public class DepthFirstGenerator implements BoardGenerator {

    Random rand = new Random();
    Stack<Vector2> stack = new Stack<>();
    boolean[][] board;
    boolean[][] visited;
    Vector2 start;
    Vector2 current;
    int width;
    int height;

    public DepthFirstGenerator(int width, int height) {
        this.width = width - 1;
        this.height = height - 1;

        start = generateRandomStart();
    }

    private Vector2 generateRandomStart() {
        int row = rand.nextInt(height - 2) + 1; // 1 to height-1
        int col = rand.nextInt(width - 2) + 1; // 1 to width-1

        return new Vector2(col, row);
    }

    public DepthFirstGenerator(int width, int height, Vector2 start) {
        this.width = width - 1;
        this.height = height - 1;
        this.start = start;
    }

    @Override
    public boolean[][] generate() {

        board = new boolean[height][width];

        board = generateMaze();

        return board;
    }

    private boolean[][] generateMaze() {

        initializeVariables();
        generateBoard();
        return getBorderedBoard();

    }

    private void initializeVariables() {
        board[start.getY()][start.getX()] = true;
        visited = new boolean[height][width];
        visited[start.getY()][start.getX()] = true;
        stack.push(start);
        current = start;
    }

    private void generateBoard() {
        while (!stack.isEmpty()) {
            current = stack.pop();

            Vector2 neighbor = getRandomNotVisitedNeighbor();
            if (neighbor == null) {
                continue;
            }

            updateVariables(neighbor);
        }

    }

    private Vector2 getRandomNotVisitedNeighbor() {
        Vector2[] neighbors = new Vector2[4];
        neighbors[0] = new Vector2(current.getX(), current.getY() - 2);
        neighbors[1] = new Vector2(current.getX() + 2, current.getY());
        neighbors[2] = new Vector2(current.getX(), current.getY() + 2);
        neighbors[3] = new Vector2(current.getX() - 2, current.getY());
        List<Vector2> notVisitedNeighbors = new ArrayList<>();
        for (Vector2 v : neighbors) {
            if (!isOutOfBounds(v) && !isVisited(v)) {
                notVisitedNeighbors.add(v);
            }
        }

        if (notVisitedNeighbors.isEmpty()) {
            return null;
        } else {
            return notVisitedNeighbors.get((int) (Math.random() * notVisitedNeighbors.size()));
        }
    }

    private boolean isOutOfBounds(Vector2 v) {
        return v.getX() < 0 || v.getX() >= width || v.getY() < 0 || v.getY() >= height;
    }

    private boolean isVisited(Vector2 v) {
        return board[v.getY()][v.getX()];
    }

    private void updateVariables(Vector2 neighbor) {
        stack.push(current.copy());
        board[neighbor.getY()][neighbor.getX()] = true;
        board[(neighbor.getY() + current.getY()) / 2][(neighbor.getX() + current.getX()) / 2] = true;
        visited[neighbor.getY()][neighbor.getX()] = true;
        stack.push(neighbor);
    }

    private boolean[][] getBorderedBoard() {
        boolean[][] finalBoard = board;
        if (!Boolean2DArraysOperations.isRowEmpty(finalBoard, 0)) {
            finalBoard = addTopBorder(finalBoard);
        }
        if (!Boolean2DArraysOperations.isRowEmpty(finalBoard, finalBoard.length - 1)) {
            finalBoard = addBottomBorder(finalBoard);
        }
        if (!Boolean2DArraysOperations.isColumnEmpty(finalBoard, 0)) {
            finalBoard = addLeftBorder(finalBoard);
        }
        if (!Boolean2DArraysOperations.isColumnEmpty(finalBoard, finalBoard[0].length - 1)) {
            finalBoard = addRightBorder(finalBoard);
        }
        return finalBoard;
    }

    private boolean[][] addTopBorder(boolean[][] finalBoard) {
        return Boolean2DArraysOperations.addEmptyRow(finalBoard, 0);
    }

    private boolean[][] addBottomBorder(boolean[][] finalBoard) {
        return Boolean2DArraysOperations.addEmptyRow(finalBoard, finalBoard.length);
    }

    private boolean[][] addLeftBorder(boolean[][] finalBoard) {
        return Boolean2DArraysOperations.addEmptyColumn(finalBoard, 0);
    }

    private boolean[][] addRightBorder(boolean[][] finalBoard) {
        return Boolean2DArraysOperations.addEmptyColumn(finalBoard, finalBoard[0].length);
    }

}
