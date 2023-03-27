package com.gytmy.labyrinth.model.gamemode;

import com.gytmy.labyrinth.model.score.ScoreType;

/**
 * Empty interface representing data for settings. Similar to
 * {@link com.gytmy.labyrinth.model.score.ScoreInfo}.
 */
public interface GameModeData {

    GameMode getGameMode();

    ScoreType getScoreType();

    void setScoreType(ScoreType scoreType);

}
