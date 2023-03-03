package com.gytmy.labyrinth.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.gytmy.labyrinth.model.LabyrinthModel;

public class GameOverPanel extends JPanel {

    private LabyrinthModel model;
    private JPanel buttonsPanel;
    // TODO: Add a ScoreBoardPanel attribute

    public GameOverPanel(LabyrinthModel model) {
        this.model = model;
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

        initGoToMenuButton();
        initPlayAgainButton();
        initQuitButton();

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void initQuitButton() {
        initButton("Quit", e -> System.exit(0));
    }

    private void initPlayAgainButton() {
        initButton("Play again", e -> {
            // TODO: play again methods
        });
    }

    private void initGoToMenuButton() {
        initButton("Go to menu", e -> {
            // TODO: go to menu methods
        });
    }

    private void initButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        buttonsPanel.add(button);
    }

}
