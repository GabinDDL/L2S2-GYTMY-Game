package com.gytmy.labyrinth.view.settings.player;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.gytmy.labyrinth.model.player.Player;

/**
 * This class is used to display the player selection panel.
 * It contains 5 {@link PlayerPanel} and allows to select the players.
 */
public class PlayerSelectionPanel extends JPanel {

    public static final int MAX_OF_PLAYERS = 5;
    private PlayerPanel[] playerPanels;

    public PlayerSelectionPanel() {
        setLayout(new GridLayout(1, MAX_OF_PLAYERS));
        playerPanels = new PlayerPanel[MAX_OF_PLAYERS];

        for (int i = 0; i < playerPanels.length; i++) {
            playerPanels[i] = new PlayerPanel(i);
            add(playerPanels[i]);
        }

        revalidate();
        repaint();
    }

    public Player[] getSelectedPlayers() {
        List<Player> players = new ArrayList<>();
        for (PlayerPanel playerPanel : playerPanels) {
            Player player = playerPanel.getPlayer();
            if (player != null) {
                players.add(player);
            }
        }
        return players.toArray(new Player[0]);
    }

    /**
     * Returns {@code true} if all activated players are ready.
     */
    public boolean arePlayersReady() {
        for (PlayerPanel playerPanel : playerPanels) {
            if (!playerPanel.isReady() && playerPanel.isActivated()) {
                return false;
            }
        }
        return isAtLeastOnePlayerActivated();
    }

    /**
     * Returns {@code true} if at least one player is activated.
     */
    private boolean isAtLeastOnePlayerActivated() {
        for (PlayerPanel playerPanel : playerPanels) {
            if (playerPanel.isActivated()) {
                return true;
            }
        }
        return false;
    }

}
