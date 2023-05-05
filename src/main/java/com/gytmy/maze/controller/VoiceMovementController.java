package com.gytmy.maze.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.CompletableFuture;

import com.gytmy.maze.model.Direction;
import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.view.game.MazeView;
import com.gytmy.sound.AlizeRecognitionResultParser.AlizeRecognitionResult;
import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.AudioRecognitionResult;
import com.gytmy.sound.AudioRecorder;
import com.gytmy.sound.RecordObserver;
import com.gytmy.sound.User;
import com.gytmy.sound.whisper.Whisper;
import com.gytmy.sound.whisper.Whisper.Model;
import com.gytmy.utils.HotkeyAdder;

public class VoiceMovementController implements RecordObserver {

    private boolean compareWithWhisper = true; // true if the compare function should use Whisper
    private MazeController controller;
    private Player[] players;

    private boolean isRecordingEnabled = false;

    private static final String FILE_NAME = "currentGameAudio";
    private static final String AUDIO_GAME_PATH = "src/resources/audioFiles/client/audio/" + FILE_NAME + ".wav";
    private static final String JSON_OUTPUT_PATH = "src/resources/audioFiles/client/audio/model/json/";

    private Whisper whisper = new Whisper(Model.TINY_EN);

    public VoiceMovementController(MazeController controller) {
        this.controller = controller;
        this.players = controller.getPlayers();

        setup();
    }

    public void setup() {
        AudioRecorder.addObserver(this);

        MazeView view = controller.getView();

        HotkeyAdder.addHotkey(view, KeyEvent.VK_SPACE, () -> {
            startRecord();
        }, "Record Audio In Game");

        view.getRecordStatusPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startRecord();
            }
        });
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

        controller.updateStatus();
    }

    /**
     * Compare the audio with model and move the player in consequence
     * 
     * (the direction is determined with whisper or with alize, depends on
     * compareWithWhisper)
     */
    private void compareAudioWithModel() {
        isRecordingEnabled = false;

        AlizeRecognitionResult result = AudioRecognitionResult.getRecognitionResult();

        if (result == null) {
            isRecordingEnabled = true;
            return;
        }

        User recognizedUser = AudioFileManager.getUser(result.getName());
        if (recognizedUser == null) {
            isRecordingEnabled = true;
            return;
        }

        if (!compareWithWhisper) {
            movePlayerWithCompareResult(recognizedUser, result.getWord());
        } else {
            CompletableFuture<String> futureCommand = whisper.ask(AUDIO_GAME_PATH, FILE_NAME, JSON_OUTPUT_PATH);

            futureCommand.thenAccept(recognizedCommand -> {

                System.out.println("------------------------------------");
                System.out.println("recognizedCommand: " + recognizedCommand);
                System.out.println("recognizedUser: " + recognizedUser.getUserName());
                System.out.println("------------------------------------");

                movePlayerWithCompareResult(recognizedUser, recognizedCommand);

                new File(JSON_OUTPUT_PATH + FILE_NAME + ".json").delete();

                new File(AUDIO_GAME_PATH).delete();

                isRecordingEnabled = true;
                controller.updateStatus();
            });
        }

        controller.updateStatus();
    }

    /**
     * Move the player if its name is recognized, in the direction of
     * nameOfDirection
     * 
     * @param recognizedUser
     * @param directionName
     */
    private void movePlayerWithCompareResult(User recognizedUser, String directionName) {
        for (Player player : players) {

            if (recognizedUser.getUserName().equals(player.getName())) {

                Direction direction = Direction.stringToDirection(directionName);

                if (direction != null) {
                    controller.movePlayer(player, direction);
                }
                return;
            }
        }
    }

    @Override
    public void startRecordUpdate() {
        controller.updateStatus();
    }

    @Override
    public void endRecordUpdate() {
        compareAudioWithModel();
        controller.updateStatus();
    }

    public boolean isRecording() {
        return AudioRecorder.isRecording();
    }

    public boolean isRecognizing() {
        return isRecordingEnabled;
    }

    public void notifyGameStarted() {
        isRecordingEnabled = true;
    }

    public void notifyGameEnded() {
        AudioRecorder.getInstance().finish();
    }
}