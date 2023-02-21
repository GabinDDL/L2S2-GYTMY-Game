package com.gytmy.labyrinth.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.utils.Coordinates;

public class LabyrinthPanel extends JPanel {

    private LabyrinthModel model;
    private boolean[][] board;
    private int nbRowsBoard;
    private int nbColsBoard;
    private static final int CELL_SIZE = 24;
    private static final int CELL_DIVISION = 3;

    public LabyrinthPanel(LabyrinthModel model) {
        this.model = model;
        board = model.getBoard();
        nbRowsBoard = board.length;
        nbColsBoard = board[0].length;

        GridLayout grid = new GridLayout(nbRowsBoard, nbColsBoard);
        setLayout(grid);

        initLabyrinthPanel();
        setPreferredSize(new Dimension(nbColsBoard * CELL_SIZE, nbRowsBoard * CELL_SIZE));
    }

    private void initLabyrinthPanel() {
        initCells();
        initPlayersRepresentation();
    }

    private void initCells() {
        for (int row = 0; row < nbRowsBoard; row++) {
            for (int col = 0; col < nbColsBoard; col++) {
                initCell(col, row);
            }
        }
    }

    private void initCell(int col, int row) {
        GridBagLayout cellGrid = new GridBagLayout();
        JPanel cell = new JPanel(cellGrid);

        cell.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));

        initCellBackground(cell, col, row);

        add(cell);
    }

    private void initCellBackground(JPanel cell, int col, int row) {

        Coordinates cellToUpdate = new Coordinates(col, row);
        Coordinates initialCell = model.getInitialCell();
        Coordinates exitCell = model.getExitCell();

        if (cellToUpdate.equals(initialCell)) {
            cell.setBackground(Color.GREEN);
        } else if (cellToUpdate.equals(exitCell)) {
            cell.setBackground(Color.RED);
        } else {
            if (!model.isWall(col, row)) {
                cell.setBackground(Color.WHITE);
            } else {
                cell.setBackground(Color.BLACK);
            }
        }
    }

    private void initPlayersRepresentation() {
        Player[] players = model.getPlayers();
        for (Player player : players) {
            initPlayerRepresentation(player);
        }
    }

    private void initPlayerRepresentation(Player player) {
        JPanel cell = getCell(player.getCoordinates());
        JPanel circle = new CirclePanel(player.getColor());

        GridBagConstraints cellConstraints = new GridBagConstraints();
        cellConstraints.gridx = (player.getId() * 2) % CELL_DIVISION;
        cellConstraints.gridy = (player.getId() * 2) / CELL_DIVISION;

        cell.add(circle, cellConstraints);
    }

    private JPanel getCell(Coordinates coordinates) {
        int componentID = coordinates.getX() + nbRowsBoard * coordinates.getY();
        return (JPanel) getComponent(componentID);
    }

    public void updateLabyrinthPanel(Player player, Direction directionMoved) {
        clearPlayerFromOriginalCell(player, directionMoved);
        addPlayerInDestinationCell(player);
    }

    private void clearPlayerFromOriginalCell(Player player, Direction directionMoved) {
        Coordinates originalCoordinates = getPlayerOriginalCoordinates(player, directionMoved);
        JPanel originalCell = getCell(originalCoordinates);
        int xGrid = (player.getId() * 2) % CELL_DIVISION;
        int yGrid = (player.getId() * 2) / CELL_DIVISION;
        CirclePanel circle = (CirclePanel) originalCell.getComponentAt(xGrid, yGrid);
        originalCell.remove(circle);
    }

    private void addPlayerInDestinationCell(Player player) {
        Coordinates destinationCoordinates = player.getCoordinates();
        JPanel destinationCell = getCell(destinationCoordinates);
        GridBagConstraints cellConstraints = new GridBagConstraints();
        cellConstraints.gridx = (player.getId() * 2) % CELL_DIVISION;
        cellConstraints.gridy = (player.getId() * 2) / CELL_DIVISION;
        CirclePanel circle = new CirclePanel(player.getColor());
        destinationCell.add(circle, cellConstraints);
    }

    private Coordinates getPlayerOriginalCoordinates(Player player, Direction directionMoved) {
        Coordinates originalCoordinates = player.getCoordinates();

        switch (directionMoved) {
            case UP:
                originalCoordinates.setY(originalCoordinates.getY() + 1);
                break;
            case DOWN:
                originalCoordinates.setY(originalCoordinates.getY() - 1);
                break;
            case LEFT:
                originalCoordinates.setX(originalCoordinates.getX() + 1);
                break;
            case RIGHT:
                originalCoordinates.setX(originalCoordinates.getX() - 1);
                break;
            default:
                break;
        }

        return originalCoordinates;
    }

}
