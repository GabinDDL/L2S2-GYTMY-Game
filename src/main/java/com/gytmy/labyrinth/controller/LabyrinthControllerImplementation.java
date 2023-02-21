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

    public LabyrinthControllerImplementation(GameData gameData) {
        this.gameData = gameData;
        initGame();
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

    @Override
    public LabyrinthView getView() {
        return view;
    }

    @Override
    public void movePlayer(Player player, Direction direction) {
        model.movePlayer(player, direction);
        view.update(player, direction);
    }

    // TODO: Think about the primitive controls (Keyboard? Click on UI?)
    // I think I'll go with the Keyboard,
    // find a way to add a KeyListener to the window ?
    // Handle inputs
    // Player selection with numbers
    // Movements with directional arrows

}
