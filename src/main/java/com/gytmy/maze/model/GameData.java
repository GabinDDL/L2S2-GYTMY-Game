package com.gytmy.maze.model;

import com.gytmy.maze.model.gamemode.GameMode;
import com.gytmy.maze.model.gamemode.GameModeData;
import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.model.score.ScoreType;

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

    public ScoreType getScoreType() {
        return gameModeData.getScoreType();
    }

    public void setScoreType(ScoreType scoreType) {
        gameModeData.setScoreType(scoreType);
    }

}
