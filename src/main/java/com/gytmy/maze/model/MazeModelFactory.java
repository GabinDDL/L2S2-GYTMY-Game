package com.gytmy.maze.model;

import com.gytmy.maze.model.gamemode.BlackoutGameData;
import com.gytmy.maze.model.gamemode.ClassicGameModeData;
import com.gytmy.maze.model.generators.BoardGenerator;
import com.gytmy.maze.model.generators.DepthFirstGenerator;
import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.model.score.ScoreType;
import com.gytmy.utils.Coordinates;

/**
 * This class is used to create a {@code MazeModel} instance. It is used
 * by the {@code MazeController} to create a new {@code MazeModel}
 * instance when the user starts a new game.
 * 
 * @see com.gytmy.maze.model.gamemode.GameMode
 */
public class MazeModelFactory {

    public static final int MINIMUM_WIDTH_1D = 3;
    public static final int MINIMUM_WIDTH_2D = 5;
    public static final int MINIMUM_HEIGHT_2D = 5;
    public static final int MAXIMUM_SIZE = 40;

    private MazeModelFactory() {
    }

    public static MazeModel createMaze(GameData gameData) {
        switch (gameData.getGameMode()) {
            case CLASSIC:
                return createClassicMaze(gameData);
            case BLACKOUT:
                return createBlackoutMaze(gameData);
            default:
                throw new IllegalArgumentException("Game mode not supported");
        }
    }

    private static MazeModel createClassicMaze(GameData gameData) {
        ClassicGameModeData gameModeData = (ClassicGameModeData) gameData.getGameModeData();
        int width = gameModeData.getWidth();
        int height = gameModeData.getHeight();
        BoardGenerator generator = getBoardGenerator(width, height, null);
        return createMaze(generator, null, null, gameData.getPlayers(), gameData.getScoreType());
    }

    private static MazeModel createBlackoutMaze(GameData gameData) {
        BlackoutGameData gameModeData = (BlackoutGameData) gameData.getGameModeData();
        int size = gameModeData.getDifficulty().getSize();
        BoardGenerator generator = getBoardGenerator(size, size, null);
        return createMaze(generator, null, null, gameData.getPlayers(), gameData.getScoreType());
    }

    private static BoardGenerator getBoardGenerator(int width, int height, Coordinates initialCell) {
        return new DepthFirstGenerator(width, height, initialCell);
    }

    public static MazeModel createMaze(BoardGenerator generator, Coordinates initialCell, Coordinates endCell,
            Player[] players, ScoreType scoreType) {
        return new MazeModelImplementation(generator, initialCell, endCell, players, scoreType);
    }

}
