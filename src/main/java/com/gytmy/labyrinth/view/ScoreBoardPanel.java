package com.gytmy.labyrinth.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
                10, 5);

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
        Map<Player, Integer> sortedPlayerScoresMap = new ScoreMap();

        for (Entry<Player, Integer> entry : sortedPlayerScoresMap.entrySet()) {
            String playerName = entry.getKey().getName();
            createTextLabel(playerName);

            String playerScore = String.valueOf(entry.getValue());
            createTextLabel(playerScore);
        }
    }

    private class ScoreMap extends LinkedHashMap<Player, Integer> {
        public ScoreMap() {
            super();

            for (Player player : model.getPlayers()) {
                put(player, model.getScore(player));
            }

            this.sortByScoreDescendingOrder();
        }

        private void sortByScoreDescendingOrder() {
            List<Entry<Player, Integer>> list = new ArrayList<>(entrySet());
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue())); // Descending order

            clear();
            for (Entry<Player, Integer> entry : list) {
                put(entry.getKey(), entry.getValue());
            }
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
