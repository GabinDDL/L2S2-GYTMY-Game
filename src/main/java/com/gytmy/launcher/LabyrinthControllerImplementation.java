package com.gytmy.launcher;

import com.gytmy.labyrinth.Direction;
import com.gytmy.labyrinth.LabyrinthController;
import com.gytmy.labyrinth.LabyrinthModel;
import com.gytmy.labyrinth.LabyrinthModel1D;
import com.gytmy.labyrinth.LabyrinthModelImplementation;
import com.gytmy.labyrinth.LabyrinthView;
import com.gytmy.labyrinth.LabyrinthViewImplementation;
import com.gytmy.labyrinth.Player;
import com.gytmy.utils.Coordinates;
import com.gytmy.utils.launcher.GameData;

public class LabyrinthControllerImplementation implements LabyrinthController {

    private GameData gameData;
    private LabyrinthModel model;
    private LabyrinthView view;

    public LabyrinthControllerImplementation(GameData gameData) {
        this.gameData = gameData;
        initGame();
    }

    public void initGame() {
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
        initPlayersInitialCell(model, players);
        view = new LabyrinthViewImplementation(model);
    }

    private void initGame2D() {
        Player[] players = gameData.getPlayers();
        model = new LabyrinthModelImplementation(
                gameData.getWidthLabyrinth(),
                gameData.getHeightLabyrinth(),
                gameData.getPlayers());
        initPlayersInitialCell(model, players);
        view = new LabyrinthViewImplementation(model);
    }

    private static void initPlayersInitialCell(LabyrinthModel model, Player[] players) {
        Coordinates initialCell = model.getInitialCell();
        Player.initAllPlayersCoordinates(initialCell, players);
    }

    @Override
    public LabyrinthView getView() {
        return view;
    }

    public void movePlayer(Player player, Direction direction) {
        model.movePlayer(null, direction);
        view.update();
    }

    // TODO: Think about the primitive controls (Keyboard? Click on UI?)
    // I think I'll go with the Keyboard,
    // find a way to add a KeyListener to the window ?
    // Handle inputs
    // Player selection with numbers
    // Movements with directional arrows

}
