package com.gytmy.launcher;

import com.gytmy.labyrinth.LabyrinthModel;
import com.gytmy.labyrinth.LabyrinthModel1D;
import com.gytmy.labyrinth.LabyrinthModelImplementation;
import com.gytmy.labyrinth.LabyrinthView;
import com.gytmy.labyrinth.LabyrinthViewImplementation;
import com.gytmy.labyrinth.generators.DepthFirstGenerator;
import com.gytmy.utils.launcher.GameData;

public class GameController {

  private GameData gameData;
  private LabyrinthModel model;
  private LabyrinthView view;

  public GameController(GameData gameData) {
    this.gameData = gameData;
  }

  public void initGame() {
    switch (gameData.getDimension()) {
      case 1:
        model = new LabyrinthModel1D(gameData.getHorizontalLengthLabyrinth(),
            gameData.getPlayers());
        view = new LabyrinthViewImplementation(model);
        break;
      case 2:
        DepthFirstGenerator generator = new DepthFirstGenerator(
            gameData.getHorizontalLengthLabyrinth(),
            gameData.getVerticalLengthLabyrinth());
        // TODO: Wait bugfix Yago
        // model = new LabyrinthModelImplementation(generator, ,
        // null,gameData.getPlayers());
        view = new LabyrinthViewImplementation(model);
        break;
      default:
        return;
    }
  }

}
