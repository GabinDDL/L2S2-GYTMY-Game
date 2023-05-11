package com.gytmy.maze.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.gytmy.maze.view.game.Cell;
import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.AudioRecorder;
import com.gytmy.sound.AudioToFile;
import com.gytmy.sound.User;
import com.gytmy.sound.AudioToFile.FileTooSmallException;
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

    private static final int RECORD_DURATION_COMMANDS_IN_SECONDS = 5;
    private static final int RECORD_DURATION_FOR_OTHER_IN_SECONDS = 10;

    private int recordDurationInSeconds;

    public RecordPage(AudioMenu audioMenu, User userRecording, String wordToRecord) {
        this.audioMenu = audioMenu;
        this.userRecording = userRecording;
        this.wordToRecord = wordToRecord;
        this.recordDurationInSeconds = mapWordIntoDuration();

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

        HotkeyAdder.addHotkey(this, KeyEvent.VK_R, this::recordOrStop, "Record Audio");
        HotkeyAdder.addHotkey(this, KeyEvent.VK_ESCAPE, this::goBackToAudioMenu, "Go to Audio Menu");
    }

    private int mapWordIntoDuration() {
        switch (wordToRecord) {
            case "OTHER":
                return RECORD_DURATION_FOR_OTHER_IN_SECONDS;
            default:
                return RECORD_DURATION_COMMANDS_IN_SECONDS;
        }
    }

    private void recordOrStop() {
        if (AudioRecorder.isRecording()) {
            stopRecord();
        } else {
            startRecord();
        }
    }

    private void initTimerPanel(GridBagConstraints constraints) {
        timerPanel = new TimerPanel(this.recordDurationInSeconds);
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
            recordOrStop();
        });
        add(recordButton, constraints);
    }

    private void startRecord() {

        recordButton.setText(STOP_MESSAGE);
        discardButton.setEnabled(true);
        discardAllButton.setEnabled(true);

        statusRecordLabel.setText(recordingStatusRecord);
        AudioToFile.record(userRecording, wordToRecord, recordDurationInSeconds);
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

    protected boolean stopRecord() {
        return stopRecord(true);
    }

    /**
     * Stop recording and return true if the audio file has been recorded
     * successfully. In other words, that the audio file is not too small.
     */
    protected boolean stopRecord(boolean verbose) {

        boolean tooSmall = false;
        recordButton.setText(RECORD_MESSAGE);
        timerPanel.stop();

        try {
            AudioToFile.stop();
            ++totalRecordedAudio;
            totalRecordedAudioLabel.setText(TOTAL_RECORDED_AUDIO + totalRecordedAudio);
        } catch (FileTooSmallException e) {
            if (verbose) {
                JOptionPane.showMessageDialog(this, "The recorded file is too small. Please record again.");
            }
            tooSmall = true;
        }

        statusRecordLabel.setText(pausedStatusRecord);

        this.remove(timerPanel);
        initTimerPanel(new GridBagConstraints());
        this.revalidate();
        return !tooSmall;
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

        boolean hasBeenSaved = true;
        if (AudioRecorder.isRecording()) {
            hasBeenSaved = stopRecord(false);
        }

        if (hasBeenSaved) {
            AudioFileManager.deleteRecording(userRecording.getFirstName(), wordToRecord,
                    totalOfAudioWhenRecordStart + totalRecordedAudio);

            // If the audio has been saved, then the count has been increased
            // and should be decreased. Otherwise, the count should not be changed
            // since the audio has not been saved.
            --totalRecordedAudio;
        }

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
        // The handling of the state of the recreateModelsButton is done here
        // because there is a problem when checking for the upToDate variables
        // of the users after recording. This might be caused by the usage
        // of a Thread
        audioMenu.handleRecreateModelsButtonState();
        audioMenu.loadFileNavigator();
        audioMenu.revalidate();
        MenuFrameHandler.goToAudioMenu();
    }
}
