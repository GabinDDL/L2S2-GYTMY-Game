package com.gytmy.labyrinth.view;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.player.Player;

public class LabyrinthViewImplementation implements LabyrinthView {
    private LabyrinthPanel tilePanel;

    public LabyrinthViewImplementation(LabyrinthModel model) {
        tilePanel = new LabyrinthPanel(model);
    }

    @Override
    public void update(Player player, Direction direction) {
        tilePanel.updateLabyrinthPanel(player, direction);
    }

    @Override
    public LabyrinthPanel getTilePanel() {
        return tilePanel;
    }

}
