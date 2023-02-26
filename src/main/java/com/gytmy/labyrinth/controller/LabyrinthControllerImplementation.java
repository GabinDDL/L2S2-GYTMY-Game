package com.gytmy.labyrinth.controller;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.LabyrinthModel1D;
import com.gytmy.labyrinth.model.LabyrinthModelImplementation;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.view.GameData;
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
        switch (gameData.getDimension()) {
            case 1:
                initGame1D();
                break;
            case 2:
                initGame2D();
                break;
            default:
                break;
        }
    }

    private void initGame1D() {
        Player[] players = gameData.getPlayers();
        model = new LabyrinthModel1D(
                gameData.getWidthLabyrinth(),
                gameData.getPlayers());
        initPlayersInitialCell(players);
        view = new LabyrinthViewImplementation(model);
    }

    private void initGame2D() {
        Player[] players = gameData.getPlayers();
        model = new LabyrinthModelImplementation(
                gameData.getWidthLabyrinth(),
                gameData.getHeightLabyrinth(),
                gameData.getPlayers());
        initPlayersInitialCell(players);
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

    @Override
    public void movePlayer(Player player, Direction direction) {
        if (model.movePlayer(player, direction)) {
            view.update(player, direction);
        }
    }

    @Override
    public void addKeyController(KeyboardMovementController controller) {
        view.addKeyController(controller);
    }
}
