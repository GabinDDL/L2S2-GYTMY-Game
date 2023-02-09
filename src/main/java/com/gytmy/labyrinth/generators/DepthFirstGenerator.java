package com.gytmy.labyrinth.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.gytmy.utils.Vector2;

/**
 * A generator that uses a random depth-first search to generate a maze.
 */
public class DepthFirstGenerator implements BoardGenerator {

    boolean[][] board;
    int[][] maze;
    int width;
    int height;

    @Override
    public boolean[][] generate(int width, int height) {
        this.width = width;
        this.height = height;

        int[][] maze = generateMaze();

        boolean[][] board = new boolean[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = maze[i][j] == 0;
            }
        }

        return board;
    }

    public int[][] generateMaze() {

        maze = new int[height][width];
        // Initialize
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                maze[i][j] = 1;

        Random rand = new Random();
        // r for row、c for column
        // Generate random r
        int r = rand.nextInt(height);
        while (r % 2 == 0) {
            r = rand.nextInt(height);
        }
        // Generate random c
        int c = rand.nextInt(width);
        while (c % 2 == 0) {
            c = rand.nextInt(width);
        }
        // Starting cell
        maze[r][c] = 0;

        // Allocate the maze with recursive method
        recursion(r, c);

        return maze;
    }

    public void recursion(int r, int c) {
        // 4 random directions
        Integer[] randDirs = generateRandomDirections();
        // Examine each direction
        for (int i = 0; i < randDirs.length; i++) {

            switch (randDirs[i]) {
                case 1: // Up
                    // Whether 2 cells up is out or not
                    if (r - 2 <= 0)
                        continue;
                    if (maze[r - 2][c] != 0) {
                        maze[r - 2][c] = 0;
                        maze[r - 1][c] = 0;
                        recursion(r - 2, c);
                    }
                    break;
                case 2: // Right
                    // Whether 2 cells to the right is out or not
                    if (c + 2 >= width - 1)
                        continue;
                    if (maze[r][c + 2] != 0) {
                        maze[r][c + 2] = 0;
                        maze[r][c + 1] = 0;
                        recursion(r, c + 2);
                    }
                    break;
                case 3: // Down
                    // Whether 2 cells down is out or not
                    if (r + 2 >= height - 1)
                        continue;
                    if (maze[r + 2][c] != 0) {
                        maze[r + 2][c] = 0;
                        maze[r + 1][c] = 0;
                        recursion(r + 2, c);
                    }
                    break;
                case 4: // Left
                    // Whether 2 cells to the left is out or not
                    if (c - 2 <= 0)
                        continue;
                    if (maze[r][c - 2] != 0) {
                        maze[r][c - 2] = 0;
                        maze[r][c - 1] = 0;
                        recursion(r, c - 2);
                    }
                    break;
            }
        }

    }

    /**
     * Generate an array with random directions 1-4
     * 
     * @return Array containing 4 directions in random order
     */
    public Integer[] generateRandomDirections() {
        ArrayList<Integer> randoms = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            randoms.add(i + 1);
        Collections.shuffle(randoms);

        return randoms.toArray(new Integer[4]);
    }
}
