package com.gytmy.labyrinth.view;

import java.awt.EventQueue;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.player.Player;

public abstract class LabyrinthView extends JPanel {

    public abstract void update(Player player, Direction direction);

    public abstract LabyrinthPanel getLabyrinthPanel();

    public void addLabyrinthKeyController(KeyListener keyController) {

        EventQueue.invokeLater(() -> {
            addKeyListener(keyController);
            setFocusable(true);
            requestFocusInWindow();
        });

    }

}
