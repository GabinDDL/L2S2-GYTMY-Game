package com.gytmy;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class StartMenu extends JPanel {
    private JFrame frame;
    private JLabel askNbPlayers;
    private JTextField nbPlayersField;
    private JPanel textPanel;

    StartMenu(JFrame frame) {
        this.frame = frame;
        initMenu();
    }

    private void initMenu() {
        // BorderLayout layout = new BorderLayout();
        // layout.setVgap(20);
        // setLayout(layout);
        setLayout(new BorderLayout());
        initTextField();
        initPlayerSettingsButton();
    }

    private void initTextField() {
        textPanel = new JPanel(new GridLayout(1, 1));
        askNbPlayers = new JLabel("Enter the number of players: ");
        textPanel.add(askNbPlayers);
        nbPlayersField = new JTextField(5);
        textPanel.add(nbPlayersField);
        add(textPanel, BorderLayout.CENTER);
    }

    private void initPlayerSettingsButton() {
        JButton playButton = new JButton("Next");
        playButton.addActionListener(event -> {
            if (isValidInput(nbPlayersField)) {
                int nbPlayers = Integer.valueOf(nbPlayersField.getText().strip());
                frame.setContentPane(new Settings(frame, nbPlayers));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.revalidate();
            }
        });
        add(playButton, BorderLayout.SOUTH);
    }

    private boolean isValidInput(JTextField field) {
        String strippedString = field.getText().strip();

        if (strippedString.equals(""))
            return false;

        int value = Integer.valueOf(strippedString);
        return 1 <= value && value <= 4;
    }

}
