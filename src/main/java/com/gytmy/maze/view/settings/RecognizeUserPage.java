package com.gytmy.maze.view.settings;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gytmy.maze.view.game.Cell;

public class RecognizeUserPage extends JPanel {

    private JPanel playerPanel;
    private JLabel playerName;
    private JLabel recordStatus;

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;

    private static RecognizeUserPage instance = null;

    public static RecognizeUserPage getInstance() {
        if (instance == null) {
            instance = new RecognizeUserPage();
        }

        return instance;
    }

    private RecognizeUserPage() {
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);

        updateGUI();
    }

    private void updateGUI() {
        revalidate();
        repaint();
    }
}
