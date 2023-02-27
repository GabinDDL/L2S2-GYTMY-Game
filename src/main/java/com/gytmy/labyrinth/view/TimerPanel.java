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
    // Flag to know if the timer is counting or not, it is used to avoid
    // starting the timer twice or starting it before the countdown is over.
    private boolean isCounting = false;

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    private static final Color COUNTDOWN_COLOR = Cell.EXIT_CELL_COLOR;
    private static final Color FOREGROUND_COLOR = Cell.PATH_COLOR;

    private static final String FORMAT = "[ %02d:%02d ]";

    public TimerPanel() {
        setBackground(BACKGROUND_COLOR);

        timerLabel = new JLabel(getStringTime(countdown));
        Font font = new Font("Arial", Font.BOLD, 20);
        timerLabel.setFont(font);
        timerLabel.setForeground(COUNTDOWN_COLOR);
        add(timerLabel);

        // Timer for the countdown
        timer = new Timer(1000, event -> {
            if (countdown > 0) {
                countdown--;
                timerLabel.setText(getStringTime(countdown));

            } else {
                // If the countdown is over, the timer is reset
                // and the timer for the game starts
                timer.stop();

                timerLabel.setForeground(FOREGROUND_COLOR);
                timerLabel.setText(getStringTime(counter));
                timer = new Timer(1000, this);
                isCounting = true;
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
        return String.format(FORMAT, minutes, seconds);
    }

    public void start() {
        if (isCounting) {
            return;
        }
        if (countdown <= 0) {
            isCounting = true;
        }
        timer.start();
    }

    public void stop() {
        isCounting = false;
        timer.stop();
    }

    public boolean isCounting() {
        return isCounting;
    }

    public int getCounterInSeconds() {
        return counter;
    }
}
