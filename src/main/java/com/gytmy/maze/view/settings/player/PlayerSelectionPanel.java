package com.gytmy.maze.view.settings.player;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.gytmy.maze.model.player.Player;
import com.gytmy.sound.User;

/**
 * This class is used to display the player selection panel.
 * It contains 5 {@link PlayerPanel} and allows to select the players.
 * It is a Singleton.
 */
public class PlayerSelectionPanel extends JPanel {

    private int numberOfPlayers;
    private PlayerPanel[] playerPanels;

    public static final int MAX_OF_PLAYERS = 5;

    private static PlayerSelectionPanel instance = null;

    public static PlayerSelectionPanel getInstance() {
        if (instance == null) {
            instance = new PlayerSelectionPanel();
        }
        return instance;
    }

    private PlayerSelectionPanel() {
        initPlayerPanels();
        changeNumberOfPlayers(MAX_OF_PLAYERS);
    }

    private void initPlayerPanels() {
        playerPanels = new PlayerPanel[MAX_OF_PLAYERS];

        for (int i = 0; i < playerPanels.length; i++) {
            playerPanels[i] = new PlayerPanel(i);
        }
    }

    public void changeNumberOfPlayers(int numberOfPlayers) {
        removeAll();

        this.numberOfPlayers = numberOfPlayers;

        setLayout(new GridLayout(1, numberOfPlayers));
        for (int i = 0; i < numberOfPlayers; i++) {
            add(playerPanels[i]);
        }

        revalidate();
        repaint();
    }

    public Player[] getSelectedPlayers() {
        List<Player> players = new ArrayList<>();
        for (int playerPosition = 0; playerPosition < numberOfPlayers; playerPosition++) {
            Player player = playerPanels[playerPosition].getPlayer();
            if (player != null) {
                players.add(player);
            }
        }

        return players.toArray(new Player[0]);
    }

    public List<User> getSelectedUsers() {
        List<User> users = new ArrayList<>();
        for (int playerPosition = 0; playerPosition < numberOfPlayers; playerPosition++) {
            User user = playerPanels[playerPosition].getSelectedUser();
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    public String[] getFirstNameUsers() {
        List<String> lastNames = new ArrayList<>();
        for (int playerPosition = 0; playerPosition < numberOfPlayers; playerPosition++) {
            String lastName = playerPanels[playerPosition].getFirstNameUser();
            if (lastName != null) {
                lastNames.add(lastName);
            }
        }
        return lastNames.toArray(new String[0]);

    }

    /**
     * Returns {@code true} if all activated players are ready.
     */
    public boolean arePlayersReady() {

        for (int playerPosition = 0; playerPosition < numberOfPlayers; playerPosition++) {
            PlayerPanel playerPanel = playerPanels[playerPosition];
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
        for (int playerPosition = 0; playerPosition < numberOfPlayers; playerPosition++) {
            if (playerPanels[playerPosition].isActivated()) {
                return true;
            }
        }
        return false;
    }

    public void setPlayersToUnready() {
        for (PlayerPanel playerPanel : playerPanels) {
            playerPanel.setPlayersToUnready();
        }
    }

    public User getSelectedUser(Player player) {
        for (PlayerPanel playerPanel : playerPanels) {
            if (playerPanel == null || playerPanel.getPlayer() == null) {
                continue;
            }
            if (playerPanel.getPlayer().equals(player)) {
                return playerPanel.getSelectedUser();
            }
        }
        return null;
    }

    public void remove(Player player) {
        for (PlayerPanel playerPanel : playerPanels) {
            if (playerPanel == null || playerPanel.getPlayer() == null) {
                continue;
            }
            if (playerPanel.getPlayer().equals(player)) {
                playerPanel.suppressPlayer();
            }
        }
    }

    public void updateUsers() {
        UserSelector.AvailableUsers.getInstance().updateUsers();
    }

}
