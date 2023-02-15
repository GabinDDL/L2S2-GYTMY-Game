package com.gytmy.labyrinth;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.gytmy.utils.Coordinates;

public class LabyrinthPanel extends JPanel {

    private LabyrinthModel model;
    private boolean[][] board;
    private int nbRowsBoard;
    private int nbColsBoard;
    private static final int CELL_SIZE = 24;

    public LabyrinthPanel(LabyrinthModel model) {
        this.model = model;
        board = model.getBoard();
        nbRowsBoard = board.length;
        nbColsBoard = board[0].length;

        initLabyrinthPanel();
        setPreferredSize(new Dimension(nbColsBoard * CELL_SIZE, nbRowsBoard * CELL_SIZE));
    }

    public void initLabyrinthPanel() {
        GridLayout grid = new GridLayout(nbRowsBoard, nbColsBoard);
        setLayout(grid);
        updateAllLabyrinthCells();
    }

    public void updateLabyrinthPanel(Player player, Direction directionFromOldCoordinates) {
        // TODO:Handle updates of cells displaying players
        /*
         * Clear icon from departure cell
         * If no icons are left delete gridbaglayout
         * 
         * Create new icon in destination cell
         * Create 3*3 gridbaglayout in cell to handle coordinates in cell
         */
    }

    private void updateAllLabyrinthCells() {
        for (int row = 0; row < nbRowsBoard; row++) {
            for (int col = 0; col < nbColsBoard; col++) {
                updateCell(row, col);
            }
        }
    }

    private void updateCell(int row, int col) {
        GridLayout cellGrid = new GridLayout(2, 2);
        JPanel cell = new JPanel(cellGrid);
        cell.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        Coordinates cellToUpdate = new Coordinates(col, row);
        Coordinates initialCell = model.getInitialCell();
        Coordinates exitCell = model.getExitCell();

        if (cellToUpdate.equals(initialCell)) {
            cell.setBackground(Color.GREEN);
        } else if (cellToUpdate.equals(exitCell)) {
            cell.setBackground(Color.RED);
        } else {
            if (board[row][col]) {
                cell.setBackground(Color.WHITE);
            } else {
                cell.setBackground(Color.BLACK);
            }
        }

        add(cell);
    }

}
