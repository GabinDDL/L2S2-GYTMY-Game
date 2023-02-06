package com.gytmy;

import com.gytmy.labyrinth.generators.DepthFirstGenerator;
import com.gytmy.utils.Boolean2DArraysOperations;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        boolean[][] board = new DepthFirstGenerator(41, 21).generate();

        Boolean2DArraysOperations.printBoolean2DArray(board);
    }

}
