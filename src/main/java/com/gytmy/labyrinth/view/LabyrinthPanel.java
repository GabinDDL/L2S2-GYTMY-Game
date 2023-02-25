package com.gytmy.labyrinth.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.utils.Coordinates;

public class LabyrinthPanel extends JPanel {

    private LabyrinthModel model;
    private int nbRows;
    private int nbCols;
    private Cell[][] cells;

    public LabyrinthPanel(LabyrinthModel model) {
        this.model = model;
        this.nbRows = model.getBoard().length;
        this.nbCols = model.getBoard()[0].length;
        this.cells = new Cell[nbRows][nbCols];

        setLayout(new GridLayout(nbRows, nbCols));
        initCells();
        Dimension preferredSize = new Dimension(
                Cell.CELL_SIZE * nbRows,
                Cell.CELL_SIZE * nbCols);
        setPreferredSize(preferredSize);
    }

    private void initCells() {
        for (int row = 0; row < nbRows; ++row) {
            for (int col = 0; col < nbCols; ++col) {
                initNewCell(col, row);
            }
        }
    }

    private void initNewCell(int col, int row) {
        Coordinates coordinates = new Coordinates(col, row);
        List<Player> players = model.getPlayersAtCoordinates(coordinates);
        Cell cell = new Cell(coordinates, players, model);
        add(cell);
        cells[row][col] = cell;
    }

    public void update(Player player, Direction direction) {
        removePlayerFromPreviousCell(player, direction);
        addPlayerInNewCell(player);
    }

    private void removePlayerFromPreviousCell(Player player, Direction direction) {
        Cell playerPreviousCell = getPlayerPreviousCell(player, direction);
        playerPreviousCell.removePlayer(player);
        playerPreviousCell.update();
    }

    private Cell getPlayerPreviousCell(Player player, Direction direction) {
        Coordinates coordinates = getPlayerPreviousCoordinates(player, direction);
        return getCell(coordinates);
    }

    private Coordinates getPlayerPreviousCoordinates(Player player, Direction direction) {
        Coordinates coordinates = player.getCoordinates();
        switch (direction) {
            case UP:
                coordinates = new Coordinates(coordinates.getX(), coordinates.getY() + 1);
                break;
            case RIGHT:
                coordinates = new Coordinates(coordinates.getX() - 1, coordinates.getY());
                break;
            case DOWN:
                coordinates = new Coordinates(coordinates.getX(), coordinates.getY() - 1);
                break;
            case LEFT:
                coordinates = new Coordinates(coordinates.getX() + 1, coordinates.getY());
                break;
        }
        return coordinates;
    }

    private void addPlayerInNewCell(Player player) {
        Cell playerNewCell = getPlayerNewCell(player);
        playerNewCell.addPlayer(player);
        playerNewCell.update();
    }

    private Cell getPlayerNewCell(Player player) {
        Coordinates coordinates = player.getCoordinates();
        return getCell(coordinates);
    }

    private Cell getCell(Coordinates coordinates) {
        return cells[coordinates.getY()][coordinates.getX()];
    }
}
