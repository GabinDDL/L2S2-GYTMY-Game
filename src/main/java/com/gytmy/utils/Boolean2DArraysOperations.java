package com.gytmy.utils;

import java.util.Arrays;

public class Boolean2DArraysOperations {

    private Boolean2DArraysOperations() {
    }

    /**
     * Copies a 2D boolean array.
     * 
     * @param array
     * @return
     */
    public static boolean[][] booleanCopy2D(boolean[][] array) {
        handleNullArray(array);
        boolean[][] result = new boolean[array.length][];
        for (int row = 0; row < array.length; ++row) {
            result[row] = Arrays.copyOf(array[row], array[row].length);
        }

        return result;
    }

    /**
     * Prints a 2D boolean array.
     * 
     * @param array
     */
    public static void printBoolean2DArray(boolean[][] array) {
        handleNullArray(array);
        for (int row = 0; row < array.length; ++row) {
            for (int col = 0; col < array[row].length; ++col) {
                System.out.print(array[row][col] ? "⬜" : "⬛");
            }
            System.out.println();
        }
    }

    /**
     * Checks if a column is empty.
     * 
     * @param array
     * @param index
     * @return
     */
    public static boolean isColumnEmpty(boolean[][] array, int index) {
        handleNullArray(array);
        for (int row = 0; row < array.length; ++row) {
            if (array[row][index]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a row is empty.
     * 
     * @param array
     * @param index
     * @return
     */
    public static boolean isRowEmpty(boolean[][] array, int index) {
        handleNullArray(array);
        for (int col = 0; col < array[index].length; ++col) {
            if (array[index][col]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return a copy of a 2D boolean array with an extra empty (false) a column at
     * the given index.
     * 
     * @param array
     * @param index
     * @return
     */
    public static boolean[][] addEmptyColumn(boolean[][] array, int index) {
        handleNullArray(array);
        boolean[][] newArray = new boolean[array.length][array[0].length + 1];
        for (int row = 0; row < array.length; ++row) {
            for (int col = 0; col < array[row].length; ++col) {
                if (col < index) {
                    newArray[row][col] = array[row][col];
                } else {
                    newArray[row][col + 1] = array[row][col];
                }
            }
        }
        return newArray;
    }

    /**
     * Return a copy of a 2D boolean array with an extra empty (false) a row at the
     * given index.
     * 
     * @param array
     * @param index
     * @return
     */
    public static boolean[][] addEmptyRow(boolean[][] array, int index) {
        handleNullArray(array);
        boolean[][] newArray = new boolean[array.length + 1][array[0].length];
        for (int row = 0; row < array.length; ++row) {
            for (int col = 0; col < array[row].length; ++col) {
                if (row < index) {
                    newArray[row][col] = array[row][col];
                } else {
                    newArray[row + 1][col] = array[row][col];
                }
            }
        }
        return newArray;
    }

    private static void handleNullArray(boolean[][] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
    }

}
