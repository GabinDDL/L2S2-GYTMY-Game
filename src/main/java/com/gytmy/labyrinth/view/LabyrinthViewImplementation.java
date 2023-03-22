package com.gytmy.labyrinth.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.player.Player;

public class LabyrinthViewImplementation extends LabyrinthView {
    private LabyrinthModel model;
    private LabyrinthPanel labyrinthPanel;
    private GameOverPanel gameOverPanel;
    private TimerPanel timerPanel;
    private JFrame frame;

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;

    public LabyrinthViewImplementation(LabyrinthModel model, JFrame frame) {
        this.model = model;
        this.frame = frame;
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);
        labyrinthPanel = new LabyrinthPanel(model);
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints c = new GridBagConstraints();
        timerPanel = new TimerPanel();
        c.gridx = 0;
        c.gridy = 0;
        add(timerPanel, c);
        startTimer();

        c.gridx = 0;
        c.gridy = 1;
        add(labyrinthPanel, c);
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
    public LabyrinthPanel getLabyrinthPanel() {
        return labyrinthPanel;
    }

    @Override
    public boolean isTimerCounting() {
        return timerPanel.isCounting();
    }

    @Override
    public void showGameOverPanel() {
        frame.setContentPane(new GameOverPanel(model));
        frame.setTitle(GameFrameHandler.GAME_TITLE + " - Game over");
        frame.revalidate();
        frame.repaint();
    }

}
