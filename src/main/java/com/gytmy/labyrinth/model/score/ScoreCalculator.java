package com.gytmy.labyrinth.model.score;

public interface ScoreCalculator {

    int getScore();

    void updateInfo(PlayerScoreInfo info);

}
