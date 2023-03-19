package com.gytmy.labyrinth.model;

import com.gytmy.labyrinth.model.gamemode.ClassicGameModeData;
import com.gytmy.labyrinth.model.gamemode.OneDimensionGameData;
import com.gytmy.labyrinth.model.generators.BoardGenerator;
import com.gytmy.labyrinth.model.generators.DepthFirstGenerator;
import com.gytmy.labyrinth.model.generators.OneDimensionBoardGenerator;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.score.ScoreType;
import com.gytmy.utils.Coordinates;

/**
 * This class is used to create a {@code LabyrinthModel} instance. It is used
 * by the {@code LabyrinthController} to create a new {@code LabyrinthModel}
 * instance when the user starts a new game.
 * 
 * @see com.gytmy.labyrinth.model.gamemode.GameMode
 */
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
        ClassicGameModeData gameModeData = (ClassicGameModeData) gameData.getGameModeData();
        int width = gameModeData.getWidth();
        int height = gameModeData.getHeight();
        BoardGenerator generator = getBoardGenerator(width, height, null);
        return createLabyrinth(generator, null, null, gameData.getPlayers(), gameData.getScoreType());
    }

    private static LabyrinthModel createOneDimensionLabyrinth(GameData gameData) {
        OneDimensionGameData gameModeData = (OneDimensionGameData) gameData.getGameModeData();
        int width = gameModeData.getWidth();
        BoardGenerator generator = getBoardGenerator(width, ONE_DIMENSION_HEIGHT, null);
        return createLabyrinth(generator, null, null, gameData.getPlayers(), gameData.getScoreType());
    }

    private static BoardGenerator getBoardGenerator(int width, int height, Coordinates initialCell) {
        if (height <= ONE_DIMENSION_HEIGHT) {
            return new OneDimensionBoardGenerator(width);
        }
        return new DepthFirstGenerator(width, height, initialCell);

    }

    public static LabyrinthModel createLabyrinth(BoardGenerator generator, Coordinates initialCell, Coordinates endCell,
            Player[] players, ScoreType scoreType) {
        return new LabyrinthModelImplementation(generator, initialCell, endCell, players, scoreType);
    }

}
