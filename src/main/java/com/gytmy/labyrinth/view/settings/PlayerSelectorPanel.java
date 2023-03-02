package com.gytmy.labyrinth.view.settings;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.view.Cell;

public class PlayerSelectorPanel extends JPanel {

    public PlayerSelectorPanel() {
        super();
    }

    public static class PlayerPanel extends JPanel {

        private static final Color DEFAULT_BACKGROUND = Cell.WALL_COLOR;
        private static final String ADD_PLAYER_IMAGE_PATH = "src/resources/images/settings_menu/add_player.png";

        private Player player;

        public PlayerPanel() {
            initEmpty();
        }

        public PlayerPanel(Player player) {
            this.player = player;
            init();
        }

        private void initEmpty() {
            setBackground(DEFAULT_BACKGROUND);
            ImageIcon icon = new ImageIcon(ADD_PLAYER_IMAGE_PATH);
            JLabel label = new JLabel(icon);
            setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
        }

        private void init() {

        }

    }

    public class PlayerSelector extends JPanel {
        private PlayerSelector() {
            init();
        }

        public void init() {
            // TODO
        }
    }

    // TODO: Remove this
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        PlayerPanel panel = new PlayerPanel();
        frame.add(panel);

        frame.setVisible(true);

    }

}
