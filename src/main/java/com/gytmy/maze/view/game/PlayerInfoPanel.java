package com.gytmy.maze.view.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.view.TimerPanel;

public class PlayerInfoPanel extends JPanel {

    private JLabel nameLabel;
    private JPanel colorPanel;

    public static final Dimension PLAYER_INFO_PANEL_DIMENSION = new Dimension(50, 50);
    public static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;

    public static final Font FONT = TimerPanel.FONT;

    public PlayerInfoPanel(Player player) {
        setLayout(new GridLayout(1, 2));
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(PLAYER_INFO_PANEL_DIMENSION);

        initComponents(player);
    }

    private void initComponents(Player player) {
        nameLabel = new JLabel(player.getName(), SwingConstants.CENTER);
        nameLabel.setForeground(Cell.PATH_COLOR);
        nameLabel.setFont(FONT);
        add(nameLabel);

        colorPanel = new JPanel();
        colorPanel.setBackground(player.getColor());
        add(colorPanel);
    }

    public void update(Player player) {
        nameLabel.setText(player.getName());
        colorPanel.setBackground(player.getColor());

        revalidate();
        repaint();
    }
}
