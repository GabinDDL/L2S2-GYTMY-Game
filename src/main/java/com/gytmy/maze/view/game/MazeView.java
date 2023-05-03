package com.gytmy.maze.view.game;

import javax.swing.JPanel;

import com.gytmy.maze.model.Direction;
import com.gytmy.maze.model.gamemode.GameMode;
import com.gytmy.maze.model.player.Player;

public abstract class MazeView extends JPanel {

    public abstract void update(Player player, Direction direction);

    public abstract MazePanel getMazePanel();

    public abstract int getTimerCounterInSeconds();

    public abstract boolean isTimerCounting();

    public abstract void startTimer();

    public abstract void stopTimer();

    public abstract void showGameOverPanel();

    public abstract void notifyGameStarted();

    public abstract void notifyGameOver();

    public abstract GameMode getGameMode();
}
