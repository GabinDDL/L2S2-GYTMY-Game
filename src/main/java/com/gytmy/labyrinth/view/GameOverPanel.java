package com.gytmy.labyrinth.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gytmy.labyrinth.model.LabyrinthModel;

public class GameOverPanel extends JPanel {

    private LabyrinthModel model;
    private JPanel buttonsPanel;
    private JFrame frame;
    // TODO: Add a ScoreBoardPanel attribute

    public GameOverPanel(LabyrinthModel model, JFrame frame) {
        this.model = model;
        this.frame = frame;
        initComponents();
    }

    private void initComponents() {
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        initButtons();
    }

    private void initButtons() {
        buttonsPanel = new JPanel();
        GridLayout buttonsLayout = new GridLayout(1, 3, 5, 5);
        buttonsPanel.setLayout(buttonsLayout);

        initGoToStartMenuButton();
        initPlayAgainButton();
        initQuitButton();

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void initQuitButton() {
        initButton("Quit", e -> frame.dispose());
    }

    private void initPlayAgainButton() {
        initButton("Play again", e -> {
            // TODO: play again methods
            // Reset players
            // Create a new labyrinth model
            // Update the LabyrinthView
        });
    }

    private void initGoToStartMenuButton() {
        initButton("Go to menu", e -> {
            // TODO: go to StartMenu methods
        });
    }

    private void initButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        buttonsPanel.add(button);
    }

}
