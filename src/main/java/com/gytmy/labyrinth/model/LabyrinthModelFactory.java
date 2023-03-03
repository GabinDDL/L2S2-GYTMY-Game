package com.gytmy.labyrinth.model;

import com.gytmy.labyrinth.model.generators.BoardGenerator;
import com.gytmy.labyrinth.model.generators.DepthFirstGenerator;
import com.gytmy.labyrinth.model.generators.OneDimensionBoardGenerator;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.view.settings.game_mode.ClassicGameModeData;
import com.gytmy.utils.Coordinates;

public class LabyrinthModelFactory {

    public static final int MINIMUM_WIDTH_1D = 3;
    public static final int MINIMUM_WIDTH_2D = 5;
    public static final int MAXIMUM_SIZE = 40;

    private LabyrinthModelFactory() {
    }

    public static LabyrinthModel createLabyrinth(GameData gameData) {

        switch (gameData.getGameMode()) {
            case CLASSIC:
                return createClassicLabyrinth(gameData);
            default:
                throw new IllegalArgumentException("Game mode not supported");
        }

    }

    private static LabyrinthModel createClassicLabyrinth(GameData gameData) {
        ClassicGameModeData classicGameModeData = (ClassicGameModeData) gameData.getGameModData();
        int width = classicGameModeData.getWidth();
        int height = classicGameModeData.getHeight();
        BoardGenerator generator = getBoardGenerator(width, height, null);
        return createLabyrinth(generator, null, null, gameData.getPlayers());
    }

    private static BoardGenerator getBoardGenerator(int width, int height, Coordinates initialCell) {
        if (height <= 3) {
            return new OneDimensionBoardGenerator(width);
        }
        return new DepthFirstGenerator(width, height, initialCell);

    }

    public static LabyrinthModel createLabyrinth(BoardGenerator generator, Coordinates initialCell, Coordinates endCell,
            Player[] players) {
        return new LabyrinthModelImplementation(generator, initialCell, endCell, players);
    }

}
