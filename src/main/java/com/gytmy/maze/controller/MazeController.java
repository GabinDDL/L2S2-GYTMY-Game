package com.gytmy.maze.controller;

import com.gytmy.maze.model.Direction;
import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.model.score.ScoreCalculator;
import com.gytmy.maze.model.score.ScoreType;
import com.gytmy.maze.view.game.MazeView;

public interface MazeController {

    public MazeView getView();

    public Player[] getPlayers();

    public void movePlayer(Direction direction);

    public ScoreCalculator getScoreCalculator(ScoreType type, Player player);

    public void notifyGameStarted();

    public void updateStatus();

    public void cleanObservers();

    public Player getCurrentPlayer();

}
