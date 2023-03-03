package com.gytmy.labyrinth.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gytmy.sound.AudioRecorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class RecordPage extends JPanel {

    private JFrame frame;

    private JLabel statusRecordLabel;

    private JButton cancelButton;
    private JButton saveButton;
    private JButton recordButton;
    private JButton stopButton;

    private TimerPanel timerPanel;

    RecordPage(JFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        setBackground(Cell.WALL_COLOR);
        GridBagConstraints constraints = new GridBagConstraints();

        initTimerPanel(constraints);
        initStatusRecordLabel(constraints);
        initCancelButton(constraints);
        initSaveButton(constraints);
        initRecordButton(constraints);
        initStopButton(constraints);

    }

    private void initTimerPanel(GridBagConstraints constraints) {
        timerPanel = new TimerPanel(AudioRecorder.getTotalDurationInSeconds() / 1000);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(timerPanel, constraints);
    }

    private void initStatusRecordLabel(GridBagConstraints constraints) {
        statusRecordLabel = new JLabel("Status: " + "∅");
        statusRecordLabel.setForeground(Cell.PATH_COLOR);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(statusRecordLabel, constraints);
    }

    private void initRecordButton(GridBagConstraints constraints) {
        recordButton = new JButton("○ Record");
        recordButton.setBackground(Cell.PATH_COLOR);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.75;
        constraints.weighty = 0.2;
        add(recordButton, constraints);
    }

    private void initStopButton(GridBagConstraints constraints) {
        stopButton = new JButton(" □ Stop ");
        stopButton.setBackground(Cell.PATH_COLOR);
        stopButton.setEnabled(false);
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.weightx = 0.75;
        constraints.weighty = 0.2;
        add(stopButton, constraints);
    }

    private void initSaveButton(GridBagConstraints constraints) {
        saveButton = new JButton(" Save ");
        saveButton.setBackground(Cell.EXIT_CELL_COLOR);
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.2;
        add(saveButton, constraints);
    }

    private void initCancelButton(GridBagConstraints constraints) {
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Cell.INITIAL_CELL_COLOR);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.2;
        add(cancelButton, constraints);
    }
}
