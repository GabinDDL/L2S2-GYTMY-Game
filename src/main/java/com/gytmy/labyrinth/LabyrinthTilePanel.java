package com.gytmy.labyrinth;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class LabyrinthTilePanel extends JPanel {

  private LabyrinthModel model;
  private boolean[][] board;

  public LabyrinthTilePanel(LabyrinthModel model) {
    this.model = model;
    updateTilePanel();
  }

  public void updateTilePanel() {
    boolean[][] board = model.getBoard();
    int nbRowsBoard = board.length;
    int nbColsBoard = board[0].length;

    GridLayout grid = new GridLayout(nbRowsBoard, nbColsBoard);
    setLayout(grid);
    tileLabyrinth(nbRowsBoard, nbColsBoard);

  }

  private void tileLabyrinth(int nbRowsBoard, int nbColsBoard) {
    for (int row = 0; row < nbRowsBoard; row++) {
      for (int col = 0; col < nbColsBoard; col++) {
        tileFloor(row, col);
      }
    }
  }

  private void tileFloor(int row, int col) {
    GridLayout cellGrid = new GridLayout(2, 2);
    JPanel cell = new JPanel(cellGrid);

    if (board[row][col]) {
      cell.setBackground(Color.WHITE);
    } else {
      cell.setBackground(Color.BLACK);
    }

    add(cell);
  }

}
