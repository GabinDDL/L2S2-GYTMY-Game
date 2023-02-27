package com.gytmy.labyrinth.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TimerPanel extends JPanel implements ActionListener {
    private JLabel timerLabel;
    private Timer timer;

    private int counter = 0;
    private int countdown = 3;

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    private static final Color COUNTDOWN_COLOR = Color.decode("#EE8695");
    private static final Color FOREGROUND_COLOR = Cell.PATH_COLOR;

    private static final Font FONT = new Font("Arial", Font.BOLD, 20);

    public TimerPanel() {
        timerLabel = new JLabel("[ 00:03 ]");
        timerLabel.setFont(FONT);
        timerLabel.setForeground(COUNTDOWN_COLOR);
        setBackground(BACKGROUND_COLOR);
        add(timerLabel);

        timer = new Timer(1000, event -> {
            if (countdown > 0) {
                countdown--;
                timerLabel.setText(getStringTime(countdown));

            } else {
                timer.stop();
                timerLabel.setForeground(FOREGROUND_COLOR);
                timerLabel.setText(getStringTime(counter));
                timer = new Timer(1000, this);
                timer.start();
            }
        });

    }

    public void actionPerformed(ActionEvent e) {
        counter++;
        timerLabel.setText(getStringTime(counter));

    }

    private String getStringTime(int counter) {
        int minutes = (counter % 3600) / 60;
        int seconds = counter % 60;
        return String.format("[ %02d:%02d ]", minutes, seconds);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public int getCounterInSeconds() {
        return counter;
    }
}
