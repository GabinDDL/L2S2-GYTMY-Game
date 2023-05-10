package com.gytmy.maze.view.settings;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gytmy.maze.view.game.Cell;
import com.gytmy.sound.User;

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

        initPlayerPanel();
        initRecordStatus();

        updateGUI();
    }

    private void initPlayerPanel() {
        // TODO: Add colored square {Size : 2x2}
        // TODO: Add in the center of square playerName label
    }

    private void initRecordStatus() {
        // TODO: Same principle as in-game mic indicator
    }

    public void recognizeUser(User user) {
        // TODO: Set playerPanel color to user color
        // TODO: Set playerName to user name
    }

    private void updateGUI() {
        revalidate();
        repaint();
    }
}
