package com.gytmy.labyrinth.controller;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.GameData;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.LabyrinthModelFactory;
import com.gytmy.labyrinth.model.player.Player;
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
        model = LabyrinthModelFactory.createLabyrinth(gameData);
        initPlayersInitialCell(model.getPlayers());
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
