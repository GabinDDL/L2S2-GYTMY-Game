package com.gytmy.utils.launcher;

import com.gytmy.labyrinth.LabyrinthModel;
import com.gytmy.labyrinth.LabyrinthView;
import com.gytmy.labyrinth.Player;

public class GameData {

  public static final int NO_LENGTH = -1;

  private final int dimension;
  private Player[] players;
  private final int verticalLengthLabyrinth;
  private final int horizontalLengthLabyrinth;

  public GameData(LabyrinthModel model, LabyrinthView view, Player[] players, int horizontalLengthLabyrinth) {
    this(1, model, view, players, NO_LENGTH, horizontalLengthLabyrinth);
  }

  public GameData(LabyrinthModel model, LabyrinthView view, Player[] players, int verticalLengthLabyrinth,
      int horizontalLengthLabyrinth) {
    this(2, model, view, players, verticalLengthLabyrinth, horizontalLengthLabyrinth);
  }

  private GameData(int dimension, LabyrinthModel model, LabyrinthView view, Player[] players,
      int verticalLengthLabyrinth, int horizontalLengthLabyrinth) {

    this.dimension = dimension;
    this.players = players;
    this.verticalLengthLabyrinth = verticalLengthLabyrinth;
    this.horizontalLengthLabyrinth = horizontalLengthLabyrinth;
  }

  public int getDimension() {
    return dimension;
  }

  public Player[] getPlayers() {
    return players;
  }

  public int getVerticalLengthLabyrinth() {
    return verticalLengthLabyrinth;
  }

  public int getHorizontalLengthLabyrinth() {
    return horizontalLengthLabyrinth;
  }

}
