package com.gytmy.labyrinth.view;

import javax.swing.JPanel;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.player.Player;

public abstract class LabyrinthView extends JPanel {

    public abstract void update(Player player, Direction direction);

    public abstract LabyrinthPanel getLabyrinthPanel();

}
