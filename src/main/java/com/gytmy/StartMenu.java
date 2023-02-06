package com.gytmy;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartMenu extends JPanel {
    private JFrame frame;
    private JLabel askNbPlayers;
    private JTextField nbPlayersField;
    private JPanel textPanel;
    private JPanel buttonPanel;

    StartMenu(JFrame frame) {
        this.frame = frame;
        initMenu();
    }

    private void initMenu() {
        GridLayout grid = new GridLayout(2, 1);
        setLayout(grid);
        initTextField();
        initPlayerSettingsButton();
    }

    private void initTextField() {
        textPanel = new JPanel();
        askNbPlayers = new JLabel("Entrez le nombre de joueurs: ");
        textPanel.add(askNbPlayers);
        nbPlayersField = new JTextField(5);
        textPanel.add(nbPlayersField);
        add(textPanel);
    }

    private void initPlayerSettingsButton() {
        buttonPanel = new JPanel();
        JButton playButton = new JButton("Next");
        buttonPanel.add(playButton);
        playButton.addActionListener(event -> {
            if (isValidInput(nbPlayersField)) {
                int nbPlayers = Integer.valueOf(nbPlayersField.getText().strip());
                frame.setContentPane(new Settings(frame, nbPlayers));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.revalidate();
            }
        });
        add(buttonPanel);
    }

    private boolean isValidInput(JTextField field) {
        String strippedString = field.getText().strip();

        if (strippedString.equals(""))
            return false;

        int value = Integer.valueOf(strippedString);
        return 1 <= value && value <= 4;
    }

}
