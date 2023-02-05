package com.gytmy.utils;

import java.util.Arrays;

public class ArrayOperations {

    private ArrayOperations() {
    }

    /**
     * Copies a 2D array
     * 
     * @param array
     * @return
     */
    public static boolean[][] booleanCopy2D(boolean[][] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        boolean[][] result = new boolean[array.length][];
        for (int row = 0; row < array.length; ++row) {
            result[row] = Arrays.copyOf(array[row], array[row].length);
        }

        return result;
    }

    public static void printBoolean2DArray(boolean[][] array) {
        for (int row = 0; row < array.length; ++row) {
            for (int col = 0; col < array[row].length; ++col) {
                System.out.print(array[row][col] ? "⬜" : "⬛");
            }
            System.out.println();
        }
    }

}
