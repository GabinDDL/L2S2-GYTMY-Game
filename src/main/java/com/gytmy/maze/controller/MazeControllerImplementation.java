package com.gytmy.maze.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.CompletableFuture;

import javax.swing.JFrame;

import com.gytmy.maze.model.Direction;
import com.gytmy.maze.model.GameData;
import com.gytmy.maze.model.MazeModel;
import com.gytmy.maze.model.MazeModelFactory;
import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.model.score.ScoreCalculator;
import com.gytmy.maze.model.score.ScoreType;
import com.gytmy.maze.view.game.GameplayStatus;
import com.gytmy.maze.view.game.MazeView;
import com.gytmy.maze.view.game.MazeViewFactory;
import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.AudioRecognitionResult;
import com.gytmy.sound.AudioRecorder;
import com.gytmy.sound.RecordObserver;
import com.gytmy.sound.User;
import com.gytmy.sound.AlizeRecognitionResultParser.AlizeRecognitionResult;
import com.gytmy.sound.whisper.Whisper;
import com.gytmy.sound.whisper.Whisper.Model;
import com.gytmy.utils.Coordinates;
import com.gytmy.utils.HotkeyAdder;

public class MazeControllerImplementation implements MazeController, RecordObserver {

    private GameData gameData;
    private MazeModel model;
    private MazeView view;
    private JFrame frame;
    private boolean isKeyboardMovementEnabled = false;
    private boolean hasCountdownEnded = false;
    private boolean isRecordingEnabled = false;
    private boolean compareWithWhisper = true; // true if the compare function should use Whisper

    private static final String FILE_NAME = "currentGameAudio";
    private static final String AUDIO_GAME_PATH = "src/resources/audioFiles/client/audio/" + FILE_NAME + ".wav";
    private static final String JSON_OUTPUT_PATH = "src/resources/audioFiles/client/audio/model/json/";

    private MovementControllerType selectedMovementControllerType = MovementControllerType.KEYBOARD;

    private Whisper whisper = new Whisper(Model.TINY_EN);

    public enum MovementControllerType {
        KEYBOARD
    }

    public MazeControllerImplementation(GameData gameData, JFrame frame) {
        this.gameData = gameData;
        this.frame = frame;
        initGame();
        initToggleKeyboardMovementKeyBind();
        initializeMovementController();
        initializeVoiceRecorder();
    }

    private void initGame() {
        initScoreType();
        model = MazeModelFactory.createMaze(gameData);
        initPlayersInitialCell(model.getPlayers());
        view = MazeViewFactory.createMazeView(gameData, model, frame, this);
        updateStatus();
    }

    private void initScoreType() {
        switch (selectedMovementControllerType) {
            case KEYBOARD:
                gameData.setScoreType(ScoreType.SIMPLE_KEYBOARD);
                break;
            default:
                break;
        }
    }

    private void initPlayersInitialCell(Player[] players) {
        Coordinates initialCell = model.getInitialCell();
        Player.initAllPlayersCoordinates(initialCell, players);
    }

    private void initToggleKeyboardMovementKeyBind() {
        HotkeyAdder.addHotkey(view, KeyEvent.VK_T, () -> {
            toggleKeyboardMovement();
        }, "Enable / Disable Keyboard Movement");

        view.getKeyboardMovementSwitchPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleKeyboardMovement();
            }
        });
    }

    private void toggleKeyboardMovement() {
        isKeyboardMovementEnabled = !isKeyboardMovementEnabled;
        view.toggleKeyboardMovement(isKeyboardMovementEnabled);
    }

    private void initializeMovementController() {
        // Switch statement used in place of an if-then-else statement because it is
        // more readable and allows for more than two conditions (future implementations
        // of different controllers)
        switch (selectedMovementControllerType) {
            case KEYBOARD:
                initializeKeyboardMovementController();
                break;
            default:
                break;
        }
    }

    private void initializeKeyboardMovementController() {
        MovementController movementController = new KeyboardMovementController(this);
        movementController.setup();
    }

    private void initializeVoiceRecorder() {

        AudioRecorder recorder = AudioRecorder.getInstance();
        AudioRecorder.addObserver(this);
        HotkeyAdder.addHotkey(view, KeyEvent.VK_SPACE, () -> {

            if (!isRecordingEnabled) {
                return;
            }

            if (AudioRecorder.isRecording()) {
                recorder.finish();
                return;
            }

            recorder.start(AUDIO_GAME_PATH);

            updateStatus();

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
                updateStatus();
            });
        }

        updateStatus();
    }

    /**
     * Move the player if its name is recognized, in the direction of
     * nameOfDirection
     * 
     * @param recognizedUser
     * @param directionName
     */
    private void movePlayerWithCompareResult(User recognizedUser, String directionName) {
        Player[] players = getPlayers();

        for (Player player : players) {

            if (recognizedUser.getUserName().equals(player.getName())) {

                Direction direction = Direction.stringToDirection(directionName);

                if (direction != null) {
                    movePlayer(player, direction);
                }
                return;
            }
        }
    }

    @Override
    public MazeView getView() {
        return view;
    }

    @Override
    public Player[] getPlayers() {
        return model.getPlayers();
    }

    @Override
    public void movePlayer(Player player, Direction direction) {
        if (!isMovementAllowed() || !canPlayerMove(player)) {
            return;
        }
        if (model.movePlayer(player, direction)) {
            view.update(player, direction);
        }

        handlePlayersAtExit(player);
    }

    private boolean canPlayerMove(Player player) {
        return !model.isPlayerAtExit(player);
    }

    private boolean isMovementAllowed() {
        if (model.isGameOver()) {
            view.stopTimer();
            return false;
        }
        return hasCountdownEnded && isKeyboardMovementEnabled;
    }

    /**
     * Takes care of the players that have reached the exit cell. If the player has
     * reached the exit cell, the player's time is saved. If all players have
     * reached the exit cell, the game is over.
     * 
     * @param player
     */
    private void handlePlayersAtExit(Player player) {
        if (!canPlayerMove(player)) {
            player.setTimePassedInSeconds(view.getTimerCounterInSeconds());
        }

        if (model.isGameOver()) {
            view.stopTimer();
            view.notifyGameOver();

            AudioRecorder.getInstance().finish();
        }
    }

    @Override
    public ScoreCalculator getScoreCalculator(ScoreType scoreType, Player player) {
        return model.getScoreCalculator(scoreType, player);
    }

    @Override
    public void notifyGameStarted() {
        hasCountdownEnded = true;
        isRecordingEnabled = true;
        view.notifyGameStarted();
        updateStatus();
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

    private void updateStatus() {

        view.updateRecordStatus(
                GameplayStatus.getStatusAccordingToGameplay(
                        hasCountdownEnded, AudioRecorder.isRecording(), isRecordingEnabled));
    }
}
