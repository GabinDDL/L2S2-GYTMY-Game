package com.gytmy;

import com.gytmy.labyrinth.generators.DepthFirstGenerator;
import com.gytmy.utils.ArrayOperations;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        boolean[][] board = new DepthFirstGenerator().generate(41, 20);
        ArrayOperations.printBoolean2DArray(board);
    }

}
