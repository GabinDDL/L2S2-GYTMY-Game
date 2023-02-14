package com.gytmy.labyrinth;

public interface LabyrinthController {

  LabyrinthView getView();

  void movePlayer(Player player, Direction direction);

}
