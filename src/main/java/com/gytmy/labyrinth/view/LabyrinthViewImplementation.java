package com.gytmy.labyrinth.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.player.Player;

public class LabyrinthViewImplementation extends LabyrinthView {
    private LabyrinthPanel labyrinthPanel;
    private TimerPanel timerPanel;

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;

    public LabyrinthViewImplementation(LabyrinthModel model) {
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

}
