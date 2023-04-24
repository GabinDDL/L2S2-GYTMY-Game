package com.gytmy.labyrinth.view.game;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;

import com.gytmy.labyrinth.controller.MazeController;
import com.gytmy.labyrinth.model.MazeModel;
import com.gytmy.labyrinth.view.TimerPanel;

public class MazeClassicView extends MazeViewImplementation {

    private MazeController controller;

    public MazeClassicView(MazeModel model, JFrame frame, MazeController controller) {
        super(model, frame);
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints c = new GridBagConstraints();
        timerPanel = new TimerPanel(controller);
        c.gridx = 0;
        c.gridy = 0;
        add(timerPanel, c);
        startTimer();

        c.gridx = 0;
        c.gridy = 1;
        add(labyrinthPanel, c);
    }

}
