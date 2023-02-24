package com.gytmy.labyrinth.view;

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

    public LabyrinthPanel(LabyrinthModel model) {
        this.model = model;
        this.nbRows = model.getBoard().length;
        this.nbCols = model.getBoard()[0].length;
        setLayout(new GridLayout(nbRows, nbCols));
        prepareCells();
    }

    private void prepareCells() {
        for (int row = 0; row < nbRows; ++row) {
            for (int col = 0; col < nbCols; ++col) {
                initNewCell(col, row);
            }
        }
    }

    private void initNewCell(int col, int row) {
        Coordinates coordinates = new Coordinates(col, row);
        List<Player> players = model.getPlayersAtCoordinates(coordinates);
        Cell cell = new Cell(coordinates, players, this, model);
        add(cell);
        cell.update();
    }

    public void update(Player player, Direction direction) {
        removePlayerFromOldCell(player, direction);
        addPlayerInNewCell(player); // The new cell is updated in the model, so we don't need to update it here
    }

    private void removePlayerFromOldCell(Player player, Direction direction) {
        Cell previousCell = getPlayerPreviousCell(player, direction);
        previousCell.removePlayer(player);
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
        Cell newCell = getPlayerNewCell(player);
        newCell.addPlayer(player);
    }

    private Cell getPlayerNewCell(Player player) {
        Coordinates coordinates = player.getCoordinates();
        return getCell(coordinates);
    }

    // FIXME: Find a way to retrieve the cell once added to LabyrinthPanel's
    // components
    private Cell getCell(Coordinates coordinates) {
        int nthComponent = getNthPosition(coordinates);
        return (Cell) getComponent(nthComponent);
    }

    private int getNthPosition(Coordinates coordinates) {
        return coordinates.getX() + coordinates.getY() * nbCols;
    }
}
