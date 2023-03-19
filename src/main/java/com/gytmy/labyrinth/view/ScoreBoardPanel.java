package com.gytmy.labyrinth.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.player.Player;

public class ScoreBoardPanel extends JPanel {

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    private static final Color FOREGROUND_COLOR = Cell.PATH_COLOR;

    private LabyrinthModel model;

    public ScoreBoardPanel(LabyrinthModel model) {
        this.model = model;

        initComponents();
    }

    private void initComponents() {

        GridLayout layout = new GridLayout(
                model.getNbPlayers() + 1, 2,
                20, 5);

        setLayout(layout);
        setBackground(BACKGROUND_COLOR);

        initHeaders();
        initInformations();
    }

    private void initHeaders() {
        createHeaderLabel("Player");
        createHeaderLabel("Score");
    }

    private void createHeaderLabel(String text) {
        createLabel(text, Font.BOLD, 15);
    }

    private void initInformations() {
        for (Player player : model.getPlayers()) {
            createTextLabel(player.getName());

            String playerScore = String.valueOf(model.getScore(player));
            createTextLabel(playerScore);
        }
    }

    private void createTextLabel(String text) {
        createLabel(text, Font.PLAIN, 15);
    }

    private void createLabel(String text, int fontStyle, int size) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", fontStyle, size));
        label.setBackground(BACKGROUND_COLOR);
        label.setForeground(FOREGROUND_COLOR);
        add(label);
    }
}
