package com.gytmy.maze.view.settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.view.TimerPanel;
import com.gytmy.maze.view.game.Cell;
import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.AudioRecognitionResult;
import com.gytmy.sound.AudioRecorder;
import com.gytmy.sound.RecordObserver;
import com.gytmy.sound.User;
import com.gytmy.sound.AlizeRecognitionResultParser.AlizeRecognitionResult;
import com.gytmy.utils.HotkeyAdder;
import com.gytmy.utils.ImageManipulator;

public class RecognizeUserPage extends JPanel implements RecordObserver {

    private JPanel playerPanel;
    private JLabel playerName;
    private JLabel recordStatus;
    private TimerPanel timerPanel;
    private JLabel triesStatus;

    private User choosenUser;

    private static final String TRIES_STATUS_TEXT = "Tries left: ";
    private int maximumRecognitionTries = 3;

    private boolean isRecordingEnabled = false;

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    private static final Color FOREGROUND_COLOR = Cell.PATH_COLOR;

    private static final int RECORD_DURATION_SENTENCE_IN_SECONDS = 5;

    private static final String FILE_NAME = "currentGameAudio";
    private static final String AUDIO_GAME_PATH = "src/resources/audioFiles/client/audio/" + FILE_NAME + ".wav";

    private static final Icon ENABLED_MIC_ICON = ImageManipulator.resizeImage(
            "src/resources/images/game/mic_on.png", 50, 50);
    private static final Icon DISABLED_MIC_ICON = ImageManipulator.resizeImage(
            "src/resources/images/game/mic_off.png", 50, 50);
    private static final Icon RECOGNIZING_ICON = ImageManipulator.resizeImage(
            "src/resources/images/game/computing_on.png", 50, 50);

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
        setVisible(true);

        AudioRecorder.addObserver(this);

        initTriesStatus();
        initPlayerPanel();
        initRecordStatus();
        initTimerPanel();
        initKeyBindRecord();
        placeComp(Box.createVerticalBox(), this, 3, 1, 1, 1);
        placeComp(Box.createHorizontalBox(), this, 1, 5, 1, 1);

        updateGUI();
    }

    private void initTriesStatus() {
        triesStatus = new JLabel(TRIES_STATUS_TEXT + maximumRecognitionTries);
        triesStatus.setForeground(FOREGROUND_COLOR);
        triesStatus.setHorizontalAlignment(JLabel.CENTER);
        placeComp(triesStatus, this, 0, 0, 1, 1);
    }

    private void initPlayerPanel() {
        playerPanel = new JPanel(new BorderLayout());
        placeComp(playerPanel, this, 1, 1, 2, 2);

        playerName = new JLabel();
        playerName.setForeground(FOREGROUND_COLOR);
        playerName.setHorizontalAlignment(JLabel.CENTER);
        playerPanel.add(playerName, BorderLayout.CENTER);
    }

    private void initRecordStatus() {
        recordStatus = new JLabel();
        recordStatus.setIcon(DISABLED_MIC_ICON);
        recordStatus.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startRecord();
            }
        });

        placeComp(recordStatus, this, 2, 4, 1, 1);
    }

    private void initKeyBindRecord() {
        HotkeyAdder.addHotkey(this, KeyEvent.VK_SPACE, this::startRecord, "Record Sentence");
    }

    private void initTimerPanel() {
        timerPanel = new TimerPanel(RECORD_DURATION_SENTENCE_IN_SECONDS);
        placeComp(timerPanel, this, 1, 4, 1, 1);
    }

    public void recognizePlayer(Player player, User user) {

        choosenUser = user;

        playerPanel.setBackground(player.getColor());
        playerName.setText(user.getUserName());
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

        if (recognizedUser.equals(choosenUser)) {
            JOptionPane.showMessageDialog(this, "That's you!\nYou were successfully recognized.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        maximumRecognitionTries--;

        if (maximumRecognitionTries == 0) {
            JOptionPane.showMessageDialog(this, "You have no more tries.\nYou were not recognized.", "Failure",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "That's not you!\nTry again.", "Failure",
                JOptionPane.ERROR_MESSAGE);
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

    private static void placeComp(Component comp, JPanel panel, int x, int y, int w, int h) {
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = x;
        cons.gridy = y;
        cons.gridwidth = w;
        cons.gridheight = h;
        panel.add(comp, cons);
    }

    private void updateGUI() {
        revalidate();
        repaint();
    }
}
