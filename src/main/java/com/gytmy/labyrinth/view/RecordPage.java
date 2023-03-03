package com.gytmy.labyrinth.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.AudioRecorder;
import com.gytmy.sound.AudioToFile;
import com.gytmy.sound.User;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class RecordPage extends JPanel {

    private JFrame frame;

    private User userRecording;
    private String wordToRecord;
    private static final String statusRecord = "[%s] --> %s";
    private String pausedStatusRecord;
    private String recordingStatusRecord;

    private JLabel statusRecordLabel;
    private JLabel totalRecordedAudioLabel;

    private JButton cancelButton;
    private JButton saveButton;
    private JButton recordButton;
    private JButton stopButton;

    private TimerPanel timerPanel;

    private int totalOfAudioWhenRecordStart;
    private int totalRecordedAudio = 0;

    public RecordPage(JFrame frame, User userRecording, String wordToRecord) {
        this.frame = frame;
        this.userRecording = userRecording;
        this.wordToRecord = wordToRecord;

        pausedStatusRecord = String.format(statusRecord, wordToRecord, "∅ Stopped.");
        recordingStatusRecord = String.format(statusRecord, wordToRecord, "● Recording...");

        totalOfAudioWhenRecordStart = AudioFileManager.numberOfRecordings(userRecording.getFirstName(), wordToRecord);

        setLayout(new GridBagLayout());
        setBackground(Cell.WALL_COLOR);
        GridBagConstraints constraints = new GridBagConstraints();

        initTimerPanel(constraints);
        initStatusRecordLabel(constraints);
        initTotalRecordedAudioLabel(constraints);
        initCancelButton(constraints);
        initSaveButton(constraints);
        initRecordButton(constraints);
        initStopButton(constraints);

    }

    private void initTimerPanel(GridBagConstraints constraints) {
        timerPanel = new TimerPanel(AudioRecorder.getTotalDurationInSeconds());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(timerPanel, constraints);
    }

    private void initStatusRecordLabel(GridBagConstraints constraints) {
        statusRecordLabel = new JLabel(pausedStatusRecord);
        statusRecordLabel.setForeground(Cell.PATH_COLOR);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(statusRecordLabel, constraints);
    }

    private void initTotalRecordedAudioLabel(GridBagConstraints constraints) {
        totalRecordedAudioLabel = new JLabel("Total recorded: " + totalRecordedAudio);
        totalRecordedAudioLabel.setForeground(Cell.PATH_COLOR);
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(totalRecordedAudioLabel, constraints);
    }

    private void initRecordButton(GridBagConstraints constraints) {
        recordButton = new JButton("○ Record");
        recordButton.setBackground(Cell.PATH_COLOR);
        recordButton.addActionListener(e -> startRecord());
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.75;
        constraints.weighty = 0.2;
        add(recordButton, constraints);
    }

    private void startRecord() {
        totalRecordedAudioLabel.setText("Total recorded: " + ++totalRecordedAudio);
        stopButton.setEnabled(true);
        recordButton.setEnabled(false);

        statusRecordLabel.setText(recordingStatusRecord);
        AudioToFile.record(userRecording, wordToRecord.toString());
        timerPanel.start();

        new Thread() {
            @Override
            public void run() {
                while (!timerPanel.isCounting()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopRecord();
            }
        }.start();
    }

    private void initStopButton(GridBagConstraints constraints) {
        stopButton = new JButton(" □ Stop ");
        stopButton.setBackground(Cell.PATH_COLOR);
        stopButton.setEnabled(false);
        stopButton.addActionListener(e -> stopRecord());
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.weightx = 0.75;
        constraints.weighty = 0.2;
        add(stopButton, constraints);
    }

    protected void stopRecord() {
        stopButton.setEnabled(false);
        recordButton.setEnabled(true);

        AudioToFile.stop();
        statusRecordLabel.setText(pausedStatusRecord);
        timerPanel.stop();

        saveButton.setEnabled(true);
        this.remove(timerPanel);
        initTimerPanel(new GridBagConstraints());
        this.revalidate();
    }

    private void initSaveButton(GridBagConstraints constraints) {
        saveButton = new JButton(" Save ");
        saveButton.setBackground(Cell.EXIT_CELL_COLOR);
        saveButton.setEnabled(false);
        saveButton.addActionListener(e -> goBackToAudioMenu());
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.2;
        add(saveButton, constraints);
    }

    private void initCancelButton(GridBagConstraints constraints) {
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Cell.INITIAL_CELL_COLOR);
        cancelButton.addActionListener(e -> sureToDelete());
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.2;
        add(cancelButton, constraints);
    }

    private void sureToDelete() {
        if (totalRecordedAudio == 0) {
            goBackToAudioMenu();
            return;
        }

        int sureToDelete = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete all the recordings you just recorded?",
                "Sure to delete?", JOptionPane.YES_NO_OPTION);

        if (sureToDelete == JOptionPane.YES_OPTION) {
            cancel();
        }
    }

    private void cancel() {
        for (int i = 1; i <= totalRecordedAudio; i++) {
            AudioFileManager.deleteRecording(userRecording.getFirstName(), wordToRecord,
                    totalOfAudioWhenRecordStart + i);
        }
        goBackToAudioMenu();
    }

    private void goBackToAudioMenu() {
        frame.setContentPane(new AudioMenu(frame));
        frame.revalidate();
        // TO DO : change the title of the frame with GameFrameToolBox.GAME_TITLE when
        // merged
        frame.setTitle("Be AMazed" + "\t( AudioSettings )");
    }
}
