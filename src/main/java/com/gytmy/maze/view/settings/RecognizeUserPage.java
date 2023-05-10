package com.gytmy.maze.view.settings;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.concurrent.CompletableFuture;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gytmy.maze.view.game.Cell;
import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.AudioRecognitionResult;
import com.gytmy.sound.AudioRecorder;
import com.gytmy.sound.RecordObserver;
import com.gytmy.sound.User;
import com.gytmy.sound.AlizeRecognitionResultParser.AlizeRecognitionResult;

public class RecognizeUserPage extends JPanel implements RecordObserver {

    private JPanel playerPanel;
    private JLabel playerName;
    private JLabel recordStatus;

    private boolean isRecordingEnabled = false;

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    private static final String FILE_NAME = "currentGameAudio";
    private static final String AUDIO_GAME_PATH = "src/resources/audioFiles/client/audio/" + FILE_NAME + ".wav";

    private static RecognizeUserPage instance = null;

    public static RecognizeUserPage getInstance() {
        if (instance == null) {
            instance = new RecognizeUserPage();
        }

        return instance;
    }

    private RecognizeUserPage() {
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);

        initPlayerPanel();
        initRecordStatus();

        updateGUI();
    }

    private void initPlayerPanel() {
        // TODO: Add colored square {Size : 2x2}
        // TODO: Add in the center of square playerName label
    }

    private void initRecordStatus() {
        // TODO: Same principle as in-game mic indicator
    }

    public void recognizeUser(User user) {
        // TODO: Set playerPanel color to user color
        // TODO: Set playerName to user name

        startRecord();
    }

    private void startRecord() {
        AudioRecorder recorder = AudioRecorder.getInstance();

        if (!isRecordingEnabled) {
            return;
        }

        if (AudioRecorder.isRecording()) {
            recorder.finish();
            return;
        }

        recorder.start(AUDIO_GAME_PATH);

        updateStatus();
    }

    private void compareAudioWithModel() {
        isRecordingEnabled = false;
        updateStatus();

        CompletableFuture<AlizeRecognitionResult> futureRecognitionResult = AudioRecognitionResult
                .askRecognitionResult();
        futureRecognitionResult.thenAccept(recognitionResult -> {

            if (recognitionResult == null) {
                updateStatus();
            }

            User recognizedUser = AudioFileManager.getUser(recognitionResult.getName());

            if (recognizedUser == null) {
                updateStatus();
            }

            isThatYou(recognizedUser);
        });
    }

    private void isThatYou(User recognizedUser) {
        // Open JOptionPane "Is that you?" "Yes / No"
    }

    private void updateStatus() {
        // TODO: Update recordStatus label according to recording status
    }

    @Override
    public void startRecordUpdate() {
        updateStatus();
    }

    @Override
    public void endRecordUpdate() {
        compareAudioWithModel();
        updateStatus();
    }

    private void updateGUI() {
        revalidate();
        repaint();
    }
}
