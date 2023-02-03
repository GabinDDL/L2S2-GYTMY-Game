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
        boolean[][] result = new boolean[array.length][];
        for (int row = 0; row < array.length; ++row) {
            result[row] = Arrays.copyOf(array[row], array[row].length);
        }

        return result;

    }

}
