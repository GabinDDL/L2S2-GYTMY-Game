package com.gytmy.labyrinth;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class LabyrinthPanel extends JPanel {

  private boolean[][] board;
  private int nbRowsBoard;
  private int nbColsBoard;
  private static final int CELL_SIZE = 20;

  public LabyrinthPanel(LabyrinthModel model) {
    board = model.getBoard();
    nbRowsBoard = board.length;
    nbColsBoard = board[0].length;

    updateTilePanel();
    setPreferredSize(new Dimension(nbColsBoard * CELL_SIZE, nbRowsBoard * CELL_SIZE));
  }

  public void updateTilePanel() {
    GridLayout grid = new GridLayout(nbRowsBoard, nbColsBoard);
    setLayout(grid);
    tileLabyrinth();
  }

  private void tileLabyrinth() {
    for (int row = 0; row < nbRowsBoard; row++) {
      for (int col = 0; col < nbColsBoard; col++) {
        tileFloor(row, col);
      }
    }
  }

  private void tileFloor(int row, int col) {
    GridLayout cellGrid = new GridLayout(2, 2);
    JPanel cell = new JPanel(cellGrid);
    cell.setSize(CELL_SIZE, CELL_SIZE);
    if (board[row][col]) {
      cell.setBackground(Color.WHITE);
    } else {
      cell.setBackground(Color.BLACK);
    }
    add(cell);
  }

}
