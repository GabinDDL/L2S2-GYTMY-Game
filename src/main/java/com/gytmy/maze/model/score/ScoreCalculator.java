package com.gytmy.maze.model.score;

/**
 * Interface for the score calculator. The score calculator is used to compute
 * the score of a player. And it should be constructed with an instance of
 * {@link ScoreInfo} that contains the information needed to compute the score.
 */
public interface ScoreCalculator {

    int getScore();

    void updateInfo(ScoreInfo info);

}
