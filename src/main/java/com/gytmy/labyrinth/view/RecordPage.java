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
    private AudioMenu audioMenu;

    private User userRecording;
    private String wordToRecord;
    private static final String statusRecord = "[%s] --> %s";
    private String pausedStatusRecord;
    private String recordingStatusRecord;

    private JLabel statusRecordLabel;
    private JLabel totalRecordedAudioLabel;

    private JButton goBackButton;
    private JButton recordButton;
    private JButton stopButton;
    private JButton discardButton;
    private JButton discardAllButton;

    private TimerPanel timerPanel;

    private int totalOfAudioWhenRecordStart;
    private int totalRecordedAudio = 0;

    public RecordPage(JFrame frame, AudioMenu audioMenu, User userRecording, String wordToRecord) {
        this.frame = frame;
        this.audioMenu = audioMenu;
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
        initRecordButton(constraints);
        initStopButton(constraints);
        initDiscardButton(constraints);
        initDiscardAllButton(constraints);
        initGoBackButton(constraints);
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
        constraints.gridy = 1;
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
        discardButton.setEnabled(true);
        discardAllButton.setEnabled(true);

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

        this.remove(timerPanel);
        initTimerPanel(new GridBagConstraints());
        this.revalidate();
    }

    private void initDiscardButton(GridBagConstraints constraints) {
        discardButton = new JButton("Discard");
        discardButton.setBackground(Cell.INITIAL_CELL_COLOR);
        discardButton.addActionListener(e -> discard());
        discardButton.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.2;
        add(discardButton, constraints);
    }

    private void discard() {
        if (totalRecordedAudio == 0) {
            return;
        }

        if (AudioRecorder.isRecording()) {
            stopRecord();
        }

        AudioFileManager.deleteRecording(userRecording.getFirstName(), wordToRecord,
                totalOfAudioWhenRecordStart + (totalRecordedAudio--));

        totalRecordedAudioLabel.setText("Total recorded: " + totalRecordedAudio);

        if (totalRecordedAudio == 0) {
            discardAllButton.setEnabled(false);
            discardButton.setEnabled(false);
        }
    }

    private void initDiscardAllButton(GridBagConstraints constraints) {
        discardAllButton = new JButton("Discard All");
        discardAllButton.setBackground(Cell.INITIAL_CELL_COLOR);
        discardAllButton.addActionListener(e -> sureToDelete());
        discardAllButton.setEnabled(false);
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.2;
        add(discardAllButton, constraints);
    }

    private void sureToDelete() {
        if (totalRecordedAudio == 0) {
            return;
        }

        int sureToDelete = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete all the recordings you just recorded?",
                "Sure to delete?", JOptionPane.YES_NO_OPTION);

        if (sureToDelete == JOptionPane.YES_OPTION) {
            discardAll();
        }
    }

    private void discardAll() {

        if (AudioRecorder.isRecording()) {
            stopRecord();
        }

        for (int i = 1; i <= totalRecordedAudio; i++) {
            AudioFileManager.deleteRecording(userRecording.getFirstName(), wordToRecord,
                    totalOfAudioWhenRecordStart + i);
        }

        totalRecordedAudio = 0;
        totalRecordedAudioLabel.setText("Total recorded: " + totalRecordedAudio);

        discardButton.setEnabled(false);
        discardAllButton.setEnabled(false);
    }

    private void initGoBackButton(GridBagConstraints constraints) {
        goBackButton = new JButton("Go Back");
        goBackButton.setBackground(Cell.EXIT_CELL_COLOR);
        goBackButton.addActionListener(e -> goBackToAudioMenu());
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 0.5;
        constraints.weighty = 0.2;
        add(goBackButton, constraints);
    }

    private void goBackToAudioMenu() {
        frame.setContentPane(audioMenu);
        audioMenu.loadFileNavigator();
        audioMenu.revalidate();
    }
}
