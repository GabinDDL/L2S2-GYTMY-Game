package com.gytmy.labyrinth.model;

import com.gytmy.labyrinth.model.gamemode.GameMode;
import com.gytmy.labyrinth.model.gamemode.GameModeData;
import com.gytmy.labyrinth.model.player.Player;

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

    public GameModeData getGameModeData() {
        return gameModeData;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

}
