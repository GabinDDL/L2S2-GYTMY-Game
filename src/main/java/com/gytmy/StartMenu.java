package com.gytmy;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartMenu extends JPanel {
    private JFrame frame;
    private JLabel nbPlayersLabel;
    private JTextField nbPlayersField;
    private JPanel topPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();

    StartMenu(JFrame frame) {
        this.frame = frame;
        initMenu();
    }

    private void initMenu() {
        initTextField();
        initPlayButton();

        GridLayout grid = new GridLayout(2, 1);
        setLayout(grid);
        add(topPanel);
        add(bottomPanel);
    }

    private void initTextField() {
        nbPlayersLabel = new JLabel("Entrez le nombre de joueurs: ");
        topPanel.add(nbPlayersLabel);
        nbPlayersField = new JTextField("");
        topPanel.add(nbPlayersField);
    }

    private void initPlayButton() {
        JButton playButton = new JButton("Play");
        bottomPanel.add(playButton);
        playButton.addActionListener(event -> {
            if (isValidInput(nbPlayersLabel)) {
                frame.setContentPane(this);
                frame.revalidate();
            }
        });
    }

    private boolean isValidInput(JLabel label) {
        String strippedString = label.getText().strip();
        int value = Integer.valueOf(strippedString);
        return 1 <= value && value <= 4;
    }
}
