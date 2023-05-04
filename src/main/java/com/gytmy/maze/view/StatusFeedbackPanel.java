package com.gytmy.maze.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gytmy.maze.view.game.GameplayStatus;

public class StatusFeedbackPanel extends JPanel {

    private JLabel statusFeedbackLabel;
    private final static float FONT_SIZE = 15f;
    private final static int TOP_PADDING_SIZE = 10;
    private final static int BOTTOM_PADDING_SIZE = 10;

    public StatusFeedbackPanel(int width) {
        setLayout(new BorderLayout());

        statusFeedbackLabel = new JLabel();
        statusFeedbackLabel.setFont(statusFeedbackLabel.getFont().deriveFont(FONT_SIZE));
        statusFeedbackLabel.setBorder(new EmptyBorder(TOP_PADDING_SIZE, 0, BOTTOM_PADDING_SIZE, 0));
        statusFeedbackLabel.setHorizontalAlignment(JLabel.CENTER);

        add(statusFeedbackLabel, BorderLayout.CENTER);
    }

    public void updateStatus(GameplayStatus status) {

        Color backgroundColor = status.getBackgroundColor();
        String text = status.toString();
        Color foregroundColor = status.getTextColor();

        setBackground(backgroundColor);
        statusFeedbackLabel.setText(text);
        statusFeedbackLabel.setForeground(foregroundColor);
    }
}
