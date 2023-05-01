package com.gytmy.maze.view.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.border.Border;

import com.gytmy.maze.model.Direction;
import com.gytmy.maze.model.MazeModel;
import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.view.GameOverPanel;
import com.gytmy.maze.view.MenuFrameHandler;
import com.gytmy.maze.view.TimerPanel;

public class MazeViewImplementation extends MazeView {
    protected MazeModel model;
    protected MazePanel mazePanel;
    protected TimerPanel timerPanel;
    private JFrame frame;

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;

    protected MazeViewImplementation(MazeModel model, JFrame frame) {
        this.model = model;
        this.frame = frame;
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);
        mazePanel = new MazePanel(model);
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
        mazePanel.update(player, direction);
    }

    @Override
    public MazePanel getMazePanel() {
        return mazePanel;
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

    @Override
    public void updateBorders(Color color) {

        if (color == null) {
            mazePanel.setBorder(null);
            return;
        }

        mazePanel.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(color);
                g.drawRect(x, y, width - 1, height - 1);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(0, 1, 1, 1);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        });

        repaint();
    }
}
