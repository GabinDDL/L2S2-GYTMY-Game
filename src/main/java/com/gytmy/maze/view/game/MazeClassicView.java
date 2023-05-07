package com.gytmy.maze.view.game;

import java.awt.GridBagConstraints;
import javax.swing.JFrame;

import com.gytmy.maze.controller.MazeController;
import com.gytmy.maze.model.MazeModel;
import com.gytmy.maze.model.gamemode.GameMode;
import com.gytmy.maze.view.TimerPanel;

public class MazeClassicView extends MazeViewImplementation {

    public MazeClassicView(MazeModel model, JFrame frame, MazeController controller) {
        super(model, frame, controller);
        this.controller = controller;
        initComponents();
    }

    @Override
    protected void initTimerPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 2.0;
        c.fill = GridBagConstraints.HORIZONTAL;

        timerPanel = new TimerPanel(controller);
        topPanel.add(timerPanel, c);
        startTimer();
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.CLASSIC;
    }
}
