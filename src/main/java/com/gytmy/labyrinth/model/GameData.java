package com.gytmy.labyrinth.model;

import com.gytmy.labyrinth.model.player.Player;

public class GameData {
    private static final int NO_HEIGHT = -1;

    private Player[] players;
    private final int width;
    private final int height;

    public GameData(int width, Player[] players) {
        this(width, NO_HEIGHT, players);
    }

    public GameData(int width, int height, Player[] players) {

        this.players = players;
        this.width = width;
        this.height = height;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
