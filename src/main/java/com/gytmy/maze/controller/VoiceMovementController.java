package com.gytmy.maze.controller;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.CompletableFuture;

import com.gytmy.maze.model.Direction;
import com.gytmy.maze.model.player.Player;
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
        AudioRecorder recorder = AudioRecorder.getInstance();
        AudioRecorder.addObserver(this);
        HotkeyAdder.addHotkey(controller.getView(), KeyEvent.VK_SPACE, () -> {

            if (!isRecordingEnabled) {
                System.out.println("Recording is not enabled");
                return;
            }

            if (AudioRecorder.isRecording()) {
                System.out.println("Already recording");
                recorder.finish();
                return;
            }

            System.out.println("Start recording");
            recorder.start(AUDIO_GAME_PATH);
            controller.updateStatus();
        }, "Record Audio In Game");
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
}