package com.gytmy.labyrinth.view;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.player.Player;

public class LabyrinthViewImplementation implements LabyrinthView {
    private LabyrinthPanel labyrinthPanel;

    public LabyrinthViewImplementation(LabyrinthModel model) {
        labyrinthPanel = new LabyrinthPanel(model);
    }

    @Override
    public void update(Player player, Direction direction) {
        labyrinthPanel.update(player, direction);
    }

    @Override
    public LabyrinthPanel getLabyrinthPanel() {
        return labyrinthPanel;
    }

}
