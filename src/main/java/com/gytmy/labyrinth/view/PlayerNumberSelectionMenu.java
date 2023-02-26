package com.gytmy.labyrinth.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gytmy.utils.input.UserInputFieldNumberInBounds;

public class PlayerNumberSelectionMenu extends JPanel {
    private JFrame frame;
    private UserInputFieldNumberInBounds nbPlayersField;

    public PlayerNumberSelectionMenu(JFrame frame) {
        this.frame = frame;
        initMenu();
    }

    private void initMenu() {
        setLayout(new BorderLayout());
        initTextField();
        initPlayerSettingsButton();
    }

    private void initTextField() {
        JPanel textPanel = new JPanel(new GridLayout(1, 1));
        JLabel askNbPlayers = new JLabel("Enter the number of players: ");
        textPanel.add(askNbPlayers);
        nbPlayersField = new UserInputFieldNumberInBounds(1, 5);
        textPanel.add(nbPlayersField.getTextField());
        add(textPanel, BorderLayout.CENTER);
    }

    private void initPlayerSettingsButton() {
        JButton playButton = new JButton("Next");
        playButton.addActionListener(event -> {
            if (nbPlayersField.isValidInput()) {
                int nbPlayers = nbPlayersField.getValue();
                frame.setContentPane(new SettingsMenu(frame, nbPlayers));
                GameFrameToolbox.frameUpdate(frame, "SettingsMenu");
            }
        });
        add(playButton, BorderLayout.SOUTH);
    }

}
