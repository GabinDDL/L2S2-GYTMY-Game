package com.gytmy;

import com.gytmy.labyrinth.generators.DepthFirstGenerator;
import com.gytmy.utils.ArrayOperations;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        boolean[][] board = new DepthFirstGenerator(41, 20).generate();
        ArrayOperations.printBoolean2DArray(board);
    }

}
