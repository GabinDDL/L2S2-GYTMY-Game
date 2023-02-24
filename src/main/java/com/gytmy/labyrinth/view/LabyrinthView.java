package com.gytmy.labyrinth.view;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.player.Player;

public interface LabyrinthView {

    void update(Player player, Direction direction);

    LabyrinthPanel getLabyrinthPanel();

}
