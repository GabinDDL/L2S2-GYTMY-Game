package com.gytmy.labyrinth.controller;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.GameData;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.LabyrinthModelFactory;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.score.ScoreCalculator;
import com.gytmy.labyrinth.model.score.ScoreCalculatorFactory.ScoreType;
import com.gytmy.labyrinth.view.LabyrinthView;
import com.gytmy.labyrinth.view.LabyrinthViewImplementation;
import com.gytmy.utils.Coordinates;

public class LabyrinthControllerImplementation implements LabyrinthController {

    private GameData gameData;
    private LabyrinthModel model;
    private LabyrinthView view;

    private MovementControllerType selectedMovementControllerType = MovementControllerType.KEYBOARD;

    public enum MovementControllerType {
        KEYBOARD
    }

    public LabyrinthControllerImplementation(GameData gameData) {
        this.gameData = gameData;
        initGame();
        initializeMovementController();
    }

    private void initGame() {
        model = LabyrinthModelFactory.createLabyrinth(gameData);
        initPlayersInitialCell(model.getPlayers());
        view = new LabyrinthViewImplementation(model);
    }

    private void initPlayersInitialCell(Player[] players) {
        Coordinates initialCell = model.getInitialCell();
        Player.initAllPlayersCoordinates(initialCell, players);
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

    @Override
    public LabyrinthView getView() {
        return view;
    }

    @Override
    public Player[] getPlayers() {
        return model.getPlayers();
    }

    // TODO: implement score calculation for multiple players
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
        return view.isTimerCounting();
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

            // TODO: Remove from production code
            ScoreCalculator scoreCalculator = getScoreCalculator(ScoreType.CLASSIC, model.getPlayers()[0]);
            System.out.println(scoreCalculator.getScore());
        }

        if (model.isGameOver()) {
            view.stopTimer();

        }
    }

    @Override
    public void addKeyController(KeyboardMovementController controller) {
        view.addKeyController(controller);
    }

    // TODO: remove this
    @Override
    public ScoreCalculator getScoreCalculator(ScoreType type, Player player) {
        return model.getScoreCalculator(type, player);
    }
}
