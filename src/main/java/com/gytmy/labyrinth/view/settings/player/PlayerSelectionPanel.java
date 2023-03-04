package com.gytmy.labyrinth.view.settings.player;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.gytmy.labyrinth.model.player.Player;

//TODO: Verify if this class implementation
public class PlayerSelectionPanel extends JPanel {

    private PlayerPanel[] playerPanels;

    public PlayerSelectionPanel() {
        playerPanels = new PlayerPanel[5];
        setLayout(new GridLayout(1, 5));
        for (int i = 0; i < 5; i++) {
            playerPanels[i] = new PlayerPanel();
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

    public boolean arePlayersReady() {
        for (PlayerPanel playerPanel : playerPanels) {
            if (!playerPanel.isReady() && playerPanel.isActivated()) {
                return false;
            }
        }
        return isAtLeastOnePlayerActivated();
    }

    private boolean isAtLeastOnePlayerActivated() {
        for (PlayerPanel playerPanel : playerPanels) {
            if (playerPanel.isActivated()) {
                return true;
            }
        }
        return false;
    }

}
