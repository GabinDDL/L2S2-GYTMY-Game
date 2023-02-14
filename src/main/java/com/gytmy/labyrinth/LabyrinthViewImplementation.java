package com.gytmy.labyrinth;

public class LabyrinthViewImplementation implements LabyrinthView {
  private LabyrinthTilePanel tilePanel;

  public LabyrinthViewImplementation(LabyrinthModel model) {
    tilePanel = new LabyrinthTilePanel(model);
  }

  @Override
  public void update() {
    tilePanel.updateTilePanel();
  }

  @Override
  public LabyrinthTilePanel getTilePanel() {
    return tilePanel;
  }

}
