package com.gytmy.labyrinth.model;

import com.gytmy.labyrinth.model.generators.BoardGenerator;
import com.gytmy.labyrinth.model.generators.DepthFirstGenerator;
import com.gytmy.labyrinth.model.generators.OneDimensionBoardGenerator;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.view.settings.game_mode.ClassicGameModeData;
import com.gytmy.labyrinth.view.settings.game_mode.OneDimensionGameData;
import com.gytmy.utils.Coordinates;

public class LabyrinthModelFactory {

    public static final int MINIMUM_WIDTH_1D = 3;
    public static final int MINIMUM_WIDTH_2D = 5;
    public static final int MINIMUM_HEIGHT_2D = 5;
    public static final int MAXIMUM_SIZE = 40;

    private static final int ONE_DIMENSION_HEIGHT = 3;

    private LabyrinthModelFactory() {
    }

    public static LabyrinthModel createLabyrinth(GameData gameData) {
        switch (gameData.getGameMode()) {
            case CLASSIC:
                return createClassicLabyrinth(gameData);
            case ONE_DIMENSION:
                return createOneDimensionLabyrinth(gameData);
            default:
                throw new IllegalArgumentException("Game mode not supported");
        }
    }

    private static LabyrinthModel createClassicLabyrinth(GameData gameData) {
        ClassicGameModeData gameModeData = (ClassicGameModeData) gameData.getGameModData();
        int width = gameModeData.getWidth();
        int height = gameModeData.getHeight();
        BoardGenerator generator = getBoardGenerator(width, height, null);
        return createLabyrinth(generator, null, null, gameData.getPlayers());
    }

    private static LabyrinthModel createOneDimensionLabyrinth(GameData gameData) {
        OneDimensionGameData gameModeData = (OneDimensionGameData) gameData.getGameModData();
        int width = gameModeData.getWidth();
        BoardGenerator generator = getBoardGenerator(width, ONE_DIMENSION_HEIGHT, null);
        return createLabyrinth(generator, null, null, gameData.getPlayers());
    }

    private static BoardGenerator getBoardGenerator(int width, int height, Coordinates initialCell) {
        if (height <= ONE_DIMENSION_HEIGHT) {
            return new OneDimensionBoardGenerator(width);
        }
        return new DepthFirstGenerator(width, height, initialCell);

    }

    public static LabyrinthModel createLabyrinth(BoardGenerator generator, Coordinates initialCell, Coordinates endCell,
            Player[] players) {
        return new LabyrinthModelImplementation(generator, initialCell, endCell, players);
    }

}
