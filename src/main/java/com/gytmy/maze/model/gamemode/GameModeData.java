package com.gytmy.maze.model.gamemode;

import com.gytmy.maze.model.score.ScoreType;

/**
 * Empty interface representing data for settings. Similar to
 * {@link com.gytmy.maze.model.score.ScoreInfo}.
 */
public interface GameModeData {

    GameMode getGameMode();

    ScoreType getScoreType();

    void setScoreType(ScoreType scoreType);

}
