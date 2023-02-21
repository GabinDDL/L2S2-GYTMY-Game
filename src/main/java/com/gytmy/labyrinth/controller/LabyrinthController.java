package com.gytmy.labyrinth.controller;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.view.LabyrinthView;

public interface LabyrinthController {

    LabyrinthView getView();

    void movePlayer(Player player, Direction direction);

}
