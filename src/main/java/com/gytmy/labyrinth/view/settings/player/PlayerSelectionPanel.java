package com.gytmy.labyrinth.view.settings.player;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.gytmy.labyrinth.model.player.Player;

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
            if (playerPanel.getPlayer() == null) {
                return false;
            }
        }
        return true;
    }

    // TODO:Remove this method
    public static void main(String[] args) {

        // AudioFileManager.addUser(new User("Yago", "Iglesias", 12345, "Grep"));
        // AudioFileManager.addUser(new User("Yago1", "Iglesias", 12345, "Grep1"));
        // AudioFileManager.addUser(new User("Yago2", "Iglesias", 12345, "Grep2"));
        // AudioFileManager.addUser(new User("Yago3", "Iglesias", 12345, "Grep3"));
        // AudioFileManager.addUser(new User("Yago4", "Iglesias", 12345, "Grep4"));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 165);
        frame.setLocationRelativeTo(null);

        PlayerPanel playerPanel1 = new PlayerPanel();
        PlayerPanel playerPanel2 = new PlayerPanel();
        PlayerPanel playerPanel3 = new PlayerPanel();
        PlayerPanel playerPanel4 = new PlayerPanel();
        PlayerPanel playerPanel5 = new PlayerPanel();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 5));
        panel.add(playerPanel1);
        panel.add(playerPanel2);
        panel.add(playerPanel3);
        panel.add(playerPanel4);
        panel.add(playerPanel5);
        // Player player = new PlayerImplementation(new Coordinates(1, 1), "Player 1",
        // Color.RED, true);
        // PlayerPanel panel = new PlayerPanel(player);

        // UserSelector panel1 = new PlayerSelectionPanel().new UserSelector();
        // UserSelector panel2 = new PlayerSelectionPanel().new UserSelector();
        // UserSelector panel3 = new PlayerSelectionPanel().new UserSelector();
        // UserSelector panel4 = new PlayerSelectionPanel().new UserSelector();

        // UserSelector[] panels = { panel1, panel2, panel3, panel4 };

        // JPanel panel = new JPanel();
        // panel.setLayout(new GridBagLayout());
        // panel.add(panel1);
        // panel.add(panel2);
        // panel.add(panel3);
        // panel.add(panel4);

        frame.add(panel);
        frame.setVisible(true);

    }

}
