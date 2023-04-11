package com.gytmy.labyrinth.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.gytmy.labyrinth.view.game.Cell;
import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.AudioRecorder;
import com.gytmy.sound.AudioToFile;
import com.gytmy.sound.User;
import com.gytmy.sound.AudioToFile.FileToSmallException;
import com.gytmy.utils.HotkeyAdder;

import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.GridBagConstraints;

public class RecordPage extends JPanel {

    private AudioMenu audioMenu;

    private User userRecording;
    private String wordToRecord;
    private String pausedStatusRecord;
    private String recordingStatusRecord;

    private static final String STATUS_RECORD = "[%s] --> %s";
    private static final String TOTAL_RECORDED_AUDIO = "∑ Total recorded audio: ";
    private static final String RECORD_MESSAGE = "⬤ Record";
    private static final String RECORDING_MESSAGE = "● Recording...";
    private static final String STOPPED_MESSAGE = "∅ Stopped.";
    private static final String STOP_MESSAGE = "⊠ Stop";
    private static final String DISCARD_MESSAGE = "✖ Discard";
    private static final String DISCARD_ALL_MESSAGE = "✱ Discard All";
    private static final String GO_BACK_MESSAGE = "⬅ Go Back";

    private JLabel statusRecordLabel;
    private JLabel totalRecordedAudioLabel;

    private JButton goBackButton;
    private JButton recordButton;
    private JButton discardButton;
    private JButton discardAllButton;

    private TimerPanel timerPanel;

    private int totalOfAudioWhenRecordStart;
    private int totalRecordedAudio = 0;

    public RecordPage(AudioMenu audioMenu, User userRecording, String wordToRecord) {
        this.audioMenu = audioMenu;
        this.userRecording = userRecording;
        this.wordToRecord = wordToRecord;

        pausedStatusRecord = String.format(STATUS_RECORD, wordToRecord, STOPPED_MESSAGE);
        recordingStatusRecord = String.format(STATUS_RECORD, wordToRecord, RECORDING_MESSAGE);

        totalOfAudioWhenRecordStart = AudioFileManager.numberOfRecordings(userRecording.getFirstName(), wordToRecord);

        setLayout(new GridBagLayout());
        setBackground(Cell.WALL_COLOR);
        GridBagConstraints constraints = new GridBagConstraints();

        initTimerPanel(constraints);
        initStatusRecordLabel(constraints);
        initTotalRecordedAudioLabel(constraints);
        initRecordButton(constraints);
        initDiscardButton(constraints);
        initDiscardAllButton(constraints);
        initGoBackButton(constraints);

        HotkeyAdder.addHotkey(this, KeyEvent.VK_ESCAPE, this::goBackToAudioMenu);
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
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(statusRecordLabel, constraints);
    }

    private void initTotalRecordedAudioLabel(GridBagConstraints constraints) {
        totalRecordedAudioLabel = new JLabel(TOTAL_RECORDED_AUDIO + totalRecordedAudio);
        totalRecordedAudioLabel.setForeground(Cell.PATH_COLOR);
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(totalRecordedAudioLabel, constraints);
    }

    public void initRecordButton(GridBagConstraints constraints) {
        recordButton = new JButton(RECORD_MESSAGE);
        recordButton.setBackground(Cell.PATH_COLOR);
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.weightx = 0.75;
        constraints.weighty = 0.2;
        recordButton.addActionListener(e -> {
            if (AudioRecorder.isRecording()) {
                stopRecord();
            } else {
                startRecord();
            }
        });
        add(recordButton, constraints);
    }

    private void startRecord() {

        recordButton.setText(STOP_MESSAGE);
        discardButton.setEnabled(true);
        discardAllButton.setEnabled(true);

        statusRecordLabel.setText(recordingStatusRecord);
        AudioToFile.record(userRecording, wordToRecord);
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

    protected void stopRecord() {

        recordButton.setText(RECORD_MESSAGE);

        try {
            AudioToFile.stop();
            ++totalRecordedAudio;
            totalRecordedAudioLabel.setText(TOTAL_RECORDED_AUDIO + totalRecordedAudio);
        } catch (FileToSmallException e) {
            JOptionPane.showMessageDialog(this, "The recorded file is too small. Please record again.");
        }

        statusRecordLabel.setText(pausedStatusRecord);
        timerPanel.stop();

        this.remove(timerPanel);
        initTimerPanel(new GridBagConstraints());
        this.revalidate();
    }

    private void initDiscardButton(GridBagConstraints constraints) {
        discardButton = new JButton(DISCARD_MESSAGE);
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

        totalRecordedAudioLabel.setText(TOTAL_RECORDED_AUDIO + totalRecordedAudio);

        if (totalRecordedAudio == 0) {
            discardAllButton.setEnabled(false);
            discardButton.setEnabled(false);
        }
    }

    private void initDiscardAllButton(GridBagConstraints constraints) {
        discardAllButton = new JButton(DISCARD_ALL_MESSAGE);
        discardAllButton.setBackground(Cell.INITIAL_CELL_COLOR);
        discardAllButton.addActionListener(e -> sureToDelete());
        discardAllButton.setEnabled(false);
        constraints.gridx = 2;
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
        totalRecordedAudioLabel.setText(TOTAL_RECORDED_AUDIO + totalRecordedAudio);

        discardButton.setEnabled(false);
        discardAllButton.setEnabled(false);
    }

    private void initGoBackButton(GridBagConstraints constraints) {
        goBackButton = new JButton(GO_BACK_MESSAGE);
        goBackButton.setBackground(Cell.EXIT_CELL_COLOR);
        goBackButton.addActionListener(e -> goBackToAudioMenu());
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 0.5;
        constraints.weighty = 0.2;
        add(goBackButton, constraints);
    }

    private void goBackToAudioMenu() {
        audioMenu.loadFileNavigator();
        audioMenu.revalidate();
        MenuFrameHandler.goToAudioMenu();
    }
}
