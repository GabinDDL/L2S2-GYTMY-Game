package com.gytmy.maze.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gytmy.maze.view.game.Cell;

public class WaitingMenu extends JPanel {

    private static final String WAITING_GIF_PATH = "file:src/resources/images/settings_menu/loading.gif";

    private static JLabel queueLabel;
    private ImageIcon gifIcon;

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;

    public WaitingMenu() {
        super(new BorderLayout());

        this.setBackground(BACKGROUND_COLOR);
        this.setPreferredSize(MenuFrameHandler.DEFAULT_DIMENSION);
        this.setSize(this.getPreferredSize());
        this.revalidate();
        this.setVisible(true);

        initComponents();
    }

    private void initComponents() {

        initGif();

        initLabel();
    }

    private void initGif() {
        gifIcon = null;
        try {
            gifIcon = new ImageIcon(new URL(WAITING_GIF_PATH));
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
    }

    private void initLabel() {
        queueLabel = new JLabel("Recreating models of all users...");
        queueLabel.setHorizontalAlignment(JLabel.CENTER);
        queueLabel.setVerticalAlignment(JLabel.CENTER);
        queueLabel.setFont(getFont().deriveFont(20f));
        queueLabel.setForeground(Cell.PATH_COLOR);
        queueLabel.setIcon(gifIcon);

        this.add(queueLabel, BorderLayout.CENTER);
    }
}
