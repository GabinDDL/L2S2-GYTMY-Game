package com.gytmy.labyrinth.view.settings;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.gytmy.labyrinth.model.gamemode.GameMode;

public class GameGIFLabel extends JLabel {

    private static final String ANIMATED_GAME_GIF_PATH = "src/resources/images/settings_menu/MAZE_LOGO_ROTATED.gif";
    private static final String ANIMATED_BLACKOUT_GIF_PATH = "src/resources/images/settings_menu/MAZE_BLACKOUT_MODE.gif";

    private static GameGIFLabel instance = null;

    private GameGIFLabel() {
        super(new ImageIcon(ANIMATED_GAME_GIF_PATH));
    }

    public static GameGIFLabel getInstance() {
        if (instance == null) {
            instance = new GameGIFLabel();
        }
        return instance;
    }

    public String getGIF(GameMode gameMode) {
        switch (gameMode) {
            case BLACKOUT:
                return ANIMATED_BLACKOUT_GIF_PATH;
            case CLASSIC:
            case ONE_DIMENSION:
            default:
                return ANIMATED_GAME_GIF_PATH;
        }
    }

    public void updateGIF(GameMode gameMode) {
        setIcon(new ImageIcon(getGIF(gameMode)));
    }
}
