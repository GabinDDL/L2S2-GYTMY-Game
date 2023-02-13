package com.gytmy.utils.launcher;

import com.gytmy.labyrinth.LabyrinthModel;
import com.gytmy.labyrinth.LabyrinthView;
import com.gytmy.labyrinth.Player;

public class GameData {

  public static final int NO_LENGTH = -1;

  private final int dimension;
  private Player[] players;
  private final int heightLabyrinth;
  private final int widthLabyrinth;

  public GameData(LabyrinthModel model, LabyrinthView view, Player[] players, int widthLabyrinth) {
    this(1, model, view, players, NO_LENGTH, widthLabyrinth);
  }

  public GameData(LabyrinthModel model, LabyrinthView view, Player[] players, int heightLabyrinth,
      int widthLabyrinth) {
    this(2, model, view, players, heightLabyrinth, widthLabyrinth);
  }

  private GameData(int dimension, LabyrinthModel model, LabyrinthView view, Player[] players,
      int heightLabyrinth, int widthLabyrinth) {

    this.dimension = dimension;
    this.players = players;
    this.heightLabyrinth = heightLabyrinth;
    this.widthLabyrinth = widthLabyrinth;
  }

  public int getDimension() {
    return dimension;
  }

  public Player[] getPlayers() {
    return players;
  }

  public int getHeightLabyrinth() {
    return heightLabyrinth;
  }

  public int getWidthLabyrinth() {
    return widthLabyrinth;
  }

}
