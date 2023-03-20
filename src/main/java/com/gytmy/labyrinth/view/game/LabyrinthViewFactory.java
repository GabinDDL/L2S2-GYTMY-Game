package com.gytmy.labyrinth.view.game;

import javax.swing.JFrame;

import com.gytmy.labyrinth.controller.LabyrinthController;
import com.gytmy.labyrinth.model.GameData;
import com.gytmy.labyrinth.model.LabyrinthModel;

public class LabyrinthViewFactory {

    private LabyrinthViewFactory() {
    }

    public static LabyrinthView createLabyrinthView(GameData gameData, LabyrinthModel model, JFrame frame,
            LabyrinthController controller) {
        LabyrinthView view = null;
        switch (gameData.getGameMode()) {
            case CLASSIC:
            case ONE_DIMENSION:
                view = new LabyrinthClassicView(model, frame, controller);
                break;
            case BLACKOUT:
                view = new LabyrinthBlackoutView(model, frame, controller);
                break;
            default:
                break;
        }
        return view;
    }

}
