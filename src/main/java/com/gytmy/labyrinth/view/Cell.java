package com.gytmy.labyrinth.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.utils.Coordinates;

public class Cell extends JPanel {

    public static final Color UNINITIALIZED_CELL_COLOR = Color.DARK_GRAY;

    private Coordinates coordinates;
    private List<Player> players;
    private JPanel panel;
    private LabyrinthModel model;

    public Cell(Coordinates coordinates, List<Player> players, JPanel panel, LabyrinthModel model) {
        this.coordinates = coordinates;
        this.players = players;
        this.panel = panel;
        this.model = model;
        setBackground(UNINITIALIZED_CELL_COLOR);
    }

    public void addPlayer(Player player) {
        players.add(player);
        update();
    }

    public void removePlayer(Player player) {
        players.remove(player);
        update();
    }

    public void update() {
        JPanel resultCell;

        if (players.isEmpty()) {
            resultCell = createNonPlayerCell();
        } else {
            resultCell = createPlayersCell(players.size());
        }

        replaceCellInPanel(panel, resultCell);
    }

    private void replaceCellInPanel(JPanel labyrinthPanel, JPanel newCell) {
        int index = labyrinthPanel.getComponentZOrder(this);
        labyrinthPanel.remove(this);
        labyrinthPanel.add(newCell, index);
        labyrinthPanel.revalidate();
        labyrinthPanel.repaint();
    }

    private JPanel createNonPlayerCell() {
        JPanel resultCell = new JPanel();

        if (!colorCellInitialOrExit(resultCell)) {
            colorCellPathOrWall(resultCell);
        }

        return resultCell;
    }

    private boolean colorCellInitialOrExit(JPanel cell) {
        if (model.isInitialCell(coordinates)) {
            cell.setBackground(Color.GREEN);
            return true;

        } else if (model.isExitCell(coordinates)) {
            cell.setBackground(Color.RED);
            return true;
        }

        return false;
    }

    private void colorCellPathOrWall(JPanel cell) {
        if (model.isWall(coordinates)) {
            cell.setBackground(Color.BLACK);

        } else {
            cell.setBackground(Color.WHITE);
        }
    }

    private JPanel createPlayersCell(int nbPlayers) {
        JPanel resultCell;
        switch (nbPlayers) {
            case 1:
            case 2:
            case 3:
                resultCell = create123PlayersCell(nbPlayers);
                break;
            case 4:
                resultCell = create4PlayersCell();
                break;
            case 5:
                resultCell = create5PlayersCell();
                break;
            default:
                throw new IllegalArgumentException("Too many players in the cell");
        }

        return resultCell;
    }

    private JPanel create123PlayersCell(int nbPlayers) {
        JPanel resultCell = new JPanel();
        GridLayout layout = new GridLayout(1, nbPlayers);
        resultCell.setLayout(layout);
        addPlayerPanels(resultCell);
        return resultCell;
    }

    private void addPlayerPanels(JPanel resultCell) {
        for (Player player : players) {
            JPanel playerPanel = new JPanel();
            playerPanel.setBackground(player.getColor());
            resultCell.add(playerPanel);
        }
    }

    private JPanel create4PlayersCell() {
        JPanel resultCell = new JPanel();
        GridLayout layout = new GridLayout(2, 2);
        resultCell.setLayout(layout);
        addPlayerPanels(resultCell);
        return resultCell;
    }

    private JPanel create5PlayersCell() {
        JPanel resultCell = new JPanel();
        GridLayout layout = new GridLayout(4, 4);
        resultCell.setLayout(layout);
        addPlayerPanels5(resultCell);
        return resultCell;
    }

    private void addPlayerPanels5(JPanel resultCell) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Player player = getCornerCenterPlayer(col, row);
                addPlayerPanel(resultCell, player, col, row);
            }
        }
    }

    private Player getCornerCenterPlayer(int col, int row) {
        Player res;
        if (isPlayer1Panel(col, row)) {
            res = players.get(0);
        } else if (isPlayer2Panel(col, row)) {
            res = players.get(1);
        } else if (isPlayer3Panel(col, row)) {
            res = players.get(2);
        } else if (isPlayer4Panel(col, row)) {
            res = players.get(3);
        } else
            res = players.get(4);
        return res;
    }

    private void addPlayerPanel(JPanel resultCell, Player player, int col, int row) {
        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(player.getColor());
        int nthPosition = getNthPositionIn4By4Grid(col, row);
        resultCell.add(playerPanel, nthPosition);
    }

    private int getNthPositionIn4By4Grid(int col, int row) {
        return col + row * 4;
    }

    private static boolean isPlayer1Panel(int col, int row) {
        return (col == 0 && row == 0) ||
                (col == 1 && row == 0) ||
                (col == 0 && row == 1);
    }

    private static boolean isPlayer2Panel(int col, int row) {
        return (col == 2 && row == 0) ||
                (col == 3 && row == 0) ||
                (col == 3 && row == 1);
    }

    private static boolean isPlayer3Panel(int col, int row) {
        return (col == 0 && row == 2) ||
                (col == 0 && row == 3) ||
                (col == 1 && row == 3);
    }

    private static boolean isPlayer4Panel(int col, int row) {
        return (col == 2 && row == 3) ||
                (col == 3 && row == 3) ||
                (col == 3 && row == 2);
    }

    private static boolean isPlayer5Panel(int col, int row) {
        return (col == 1 && row == 1) ||
                (col == 2 && row == 1) ||
                (col == 1 && row == 2) ||
                (col == 2 && row == 2);
    }

}
