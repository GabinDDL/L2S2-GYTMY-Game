package com.gytmy.labyrinth;

public interface LabyrinthModel {

    Object getBoard();

    boolean isMoveValid(Player player, Direction direction);

    void movePlayer(Player player, Direction direction);

    boolean isPlayerAtExit(Player player);

    boolean isGameOver();

}
