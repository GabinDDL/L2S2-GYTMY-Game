package com.gytmy.labyrinth.model;

import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.view.settings.game_mode.GameMode;
import com.gytmy.labyrinth.view.settings.game_mode.GameModeData;

public class GameData {

    private Player[] players;
    private GameMode gameMode;
    private GameModeData gameModeData;

    public GameData(GameModeData gameModeData, GameMode gameMode, Player[] players) {
        this.players = players;
        this.gameMode = gameMode;
        this.gameModeData = gameModeData;
    }

    public Player[] getPlayers() {
        return players;
    }

    public GameModeData getGameModData() {
        return gameModeData;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

}
