package com.gytmy.maze.controller;

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
import com.gytmy.utils.Coordinates;

public class MazeControllerImplementation implements MazeController {

    private GameData gameData;
    private MazeModel model;
    private MazeView view;
    private boolean hasCountdownEnded = false;

    private KeyboardMovementController keyboardMovementController;
    private VoiceMovementController voiceMovementController;

    public MazeControllerImplementation(GameData gameData, JFrame frame) {
        this.gameData = gameData;
        initGame(frame);
        initializeMovementControllers();
        updateStatus();
    }

    private void initGame(JFrame frame) {
        initScoreType();
        model = MazeModelFactory.createMaze(gameData);
        initPlayersInitialCell(model.getPlayers());
        view = MazeViewFactory.createMazeView(gameData, model, frame, this);
    }

    private void initScoreType() {
        gameData.setScoreType(ScoreType.SIMPLE_VOICE);
    }

    private void initPlayersInitialCell(Player[] players) {
        Coordinates initialCell = model.getInitialCell();
        Player.initAllPlayersCoordinates(initialCell, players);
    }

    private void initializeMovementControllers() {
        keyboardMovementController = new KeyboardMovementController(this);
        voiceMovementController = new VoiceMovementController(this);
    }

    @Override
    public void updateStatus() {

        view.updateStatus(
                GameplayStatus.getStatusAccordingToGameplay(hasCountdownEnded, voiceMovementController.isRecording(),
                        voiceMovementController.isRecognizing()));
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

    private boolean isMovementAllowed() {
        if (model.isGameOver()) {
            view.stopTimer();
            return false;
        }
        return hasCountdownEnded;
    }

    private boolean canPlayerMove(Player player) {
        return !model.isPlayerAtExit(player);
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

            voiceMovementController.notifyGameEnded();
        }
    }

    @Override
    public ScoreCalculator getScoreCalculator(ScoreType scoreType, Player player) {
        return model.getScoreCalculator(scoreType, player);
    }

    @Override
    public void notifyGameStarted() {
        hasCountdownEnded = true;
        view.notifyGameStarted();
        voiceMovementController.notifyGameStarted();
        updateStatus();
    }

}
