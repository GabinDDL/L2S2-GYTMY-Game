package com.gytmy.labyrinth;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class LabyrinthViewImplementation extends JPanel implements LabyrinthView {
    private LabyrinthModel model;

    LabyrinthViewImplementation(LabyrinthModel model) {
        this.model = model;
    }

    @Override
    public void update() {

    }

    @Override
    public JPanel getLabyrinth() {
        boolean[][] board = model.getBoard();
        int nbColsBoard = board[0].length;
        int nbRowsBoard = board.length;

        GridLayout grid = new GridLayout(nbRowsBoard, nbColsBoard);
        JPanel res = new JPanel(grid);

        for (int row = 0; row < nbRowsBoard; row++) {
            for (int col = 0; col < nbColsBoard; col++) {
                GridLayout cellGrid = new GridLayout(2, 2);
                JPanel cell = new JPanel(cellGrid);

                if (board[row][col])
                    cell.setBackground(Color.WHITE);
                else
                    cell.setBackground(Color.BLACK);

                res.add(cell);
            }
        }

        return res;
    }

}
