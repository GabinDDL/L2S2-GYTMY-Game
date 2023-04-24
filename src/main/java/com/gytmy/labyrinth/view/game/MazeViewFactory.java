package com.gytmy.labyrinth.view.game;

import javax.swing.JFrame;

import com.gytmy.labyrinth.controller.MazeController;
import com.gytmy.labyrinth.model.GameData;
import com.gytmy.labyrinth.model.MazeModel;

public class MazeViewFactory {

    private MazeViewFactory() {
    }

    public static MazeView createLabyrinthView(GameData gameData, MazeModel model, JFrame frame,
            MazeController controller) {
        MazeView view = null;
        switch (gameData.getGameMode()) {
            case CLASSIC:
            case ONE_DIMENSION:
                view = new MazeClassicView(model, frame, controller);
                break;
            case BLACKOUT:
                view = new MazeBlackoutView(model, frame, controller);
                break;
            default:
                break;
        }
        return view;
    }

}
