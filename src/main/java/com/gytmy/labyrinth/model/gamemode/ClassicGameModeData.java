package com.gytmy.labyrinth.model.gamemode;

import com.gytmy.labyrinth.model.score.ScoreType;

public class ClassicGameModeData implements GameModeData {

    private int width;
    private int height;
    private ScoreType scoreType;

    public ClassicGameModeData(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public ScoreType getScoreType() {
        return scoreType;
    }

    @Override
    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.CLASSIC;
    }

}