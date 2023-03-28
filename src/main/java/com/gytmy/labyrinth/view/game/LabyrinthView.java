package com.gytmy.labyrinth.view.game;

import java.awt.EventQueue;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.player.Player;

public abstract class LabyrinthView extends JPanel {

    public abstract void update(Player player, Direction direction);

    public abstract LabyrinthPanel getLabyrinthPanel();

    public abstract int getTimerCounterInSeconds();

    public abstract boolean isTimerCounting();

    public abstract void stopTimer();

    public abstract void showGameOverPanel();

    public abstract void notifyGameStarted();

    public abstract void notifyGameOver();

    public void addKeyController(KeyListener keyController) {
        // The EventQueue is used to ensure that the key listener is added after the
        // component is added to the frame. If it is added before, the component will
        // only receive key events after it is clicked.
        EventQueue.invokeLater(() -> {
            addKeyListener(keyController);
            // In order to be able to receive key events, the component must be focusable.
            setFocusable(true);
            requestFocusInWindow();
        });
    }

}
