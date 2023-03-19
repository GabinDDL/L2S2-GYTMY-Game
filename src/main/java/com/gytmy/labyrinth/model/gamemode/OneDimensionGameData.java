package com.gytmy.labyrinth.model.gamemode;

import com.gytmy.labyrinth.model.score.ScoreType;

/**
 * Implementation of {@link GameModeData} for the one dimension game mode.
 */
public class OneDimensionGameData implements GameModeData {

    private int width;
    private ScoreType scoreType;

    public OneDimensionGameData(int width) {
        this.width = width;

    }

    public int getWidth() {
        return width;
    }

    @Override
    public ScoreType getScoreType() {
        return scoreType;
    }

    @Override
    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }

}
