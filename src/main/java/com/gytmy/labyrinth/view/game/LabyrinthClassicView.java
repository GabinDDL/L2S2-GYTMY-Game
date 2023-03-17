package com.gytmy.labyrinth.view.game;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;

import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.view.TimerPanel;

public class LabyrinthClassicView extends LabyrinthViewImplementation {

    public LabyrinthClassicView(LabyrinthModel model, JFrame frame) {
        super(model, frame);
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

}
