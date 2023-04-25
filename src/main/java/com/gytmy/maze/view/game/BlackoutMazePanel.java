package com.gytmy.maze.view.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.gytmy.maze.model.player.Player;
import com.gytmy.utils.Coordinates;

public class BlackoutMazePanel extends JPanel {

    private int nbRows;
    private int nbCols;
    private BlackoutCell[][] cells;

    private static final Color[] TRAIL_COLORS = {
            Color.decode("#c5f1fa"),
            Color.decode("#b5f2ff"),
            Color.decode("#8edced"),
            Color.decode("#6ddef7")
    };

    public BlackoutMazePanel(MazePanel mazePanel) {
        super();
        setBackground(MazeBlackoutView.BLACKOUT_COLOR);
        setPreferredSize(mazePanel.getPreferredSize());
        setBackground(Cell.WALL_COLOR);

        Dimension mazeDimension = mazePanel.getMazeSize();
        this.nbRows = mazeDimension.height;
        this.nbCols = mazeDimension.width;

        setLayout(new GridLayout(nbRows, nbCols));

        this.cells = new BlackoutCell[nbRows][nbCols];

        initCells();

    }

    public void initCells() {
        for (int row = 0; row < nbRows; ++row) {
            for (int col = 0; col < nbCols; ++col) {
                this.cells[row][col] = new BlackoutCell();
                add(this.cells[row][col]);
            }
        }
    }

    public void update(Player player) {
        Coordinates playerCoordinates = player.getCoordinates();
        BlackoutCell cell = cells[playerCoordinates.getY()][playerCoordinates.getX()];
        cell.visit(player);
        updateCells();

        revalidate();
        repaint();
    }

    private void updateCells() {
        for (int row = 0; row < nbRows; ++row) {
            for (int col = 0; col < nbCols; ++col) {
                cells[row][col].update();
            }
        }
    }

    private class BlackoutCell extends JPanel {

        private int lastVisited = -1;

        public BlackoutCell() {
            super();
            setBackground(MazeBlackoutView.BLACKOUT_COLOR);
        }

        public void update() {
            if (lastVisited < 0) {
                setBackground(MazeBlackoutView.BLACKOUT_COLOR);
                return;
            } else if (lastVisited < TRAIL_COLORS.length) {
                setBackground(TRAIL_COLORS[lastVisited]);
            }
            lastVisited--;
        }

        public void visit(Player player) {
            setBackground(player.getColor());
            lastVisited = TRAIL_COLORS.length;
        }

    }

}