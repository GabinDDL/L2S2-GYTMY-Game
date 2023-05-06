package com.gytmy.maze.view.game;

import javax.swing.JFrame;

import com.gytmy.maze.controller.MazeController;
import com.gytmy.maze.model.GameData;
import com.gytmy.maze.model.MazeModel;

public class MazeViewFactory {

    private MazeViewFactory() {
    }

    public static MazeView createMazeView(GameData gameData, MazeModel model, JFrame frame,
            MazeController controller) {
        MazeView view = null;
        switch (gameData.getGameMode()) {
            case CLASSIC:
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
