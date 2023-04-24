package com.gytmy.labyrinth.view.game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.MazeModel;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.view.GameOverPanel;
import com.gytmy.labyrinth.view.MenuFrameHandler;
import com.gytmy.labyrinth.view.TimerPanel;

public class MazeViewImplementation extends MazeView {
    protected MazeModel model;
    protected MazePanel labyrinthPanel;
    protected TimerPanel timerPanel;
    private JFrame frame;

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;

    protected MazeViewImplementation(MazeModel model, JFrame frame) {
        this.model = model;
        this.frame = frame;
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);
        labyrinthPanel = new MazePanel(model);
    }

    public void startTimer() {
        timerPanel.start();
    }

    @Override
    public void stopTimer() {
        timerPanel.stop();
    }

    public int getTimerCounterInSeconds() {
        return timerPanel.getCounterInSeconds();
    }

    @Override
    public void update(Player player, Direction direction) {
        labyrinthPanel.update(player, direction);
    }

    @Override
    public MazePanel getLabyrinthPanel() {
        return labyrinthPanel;
    }

    @Override
    public boolean isTimerCounting() {
        return timerPanel.isCounting();
    }

    @Override
    public void showGameOverPanel() {
        frame.setContentPane(new GameOverPanel(model));
        frame.setPreferredSize(MenuFrameHandler.DEFAULT_DIMENSION);
        MenuFrameHandler.frameUpdate("Game Over");
    }

    @Override
    public void notifyGameStarted() {
        // For this view, nothing needs to be done.
    }

    @Override
    public void notifyGameOver() {
        // EventQueue is used to pause the screen a little bit before showing the game
        // over panel
        EventQueue.invokeLater(
                () -> {
                    try {
                        Thread.sleep(2000);
                        showGameOverPanel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

}
