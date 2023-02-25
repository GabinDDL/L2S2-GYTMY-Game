package com.gytmy.labyrinth.model;

import com.gytmy.labyrinth.model.player.Player;

public class GameData {

    public static final int NO_LENGTH = -1;

    private final int dimension;
    private Player[] players;
    private final int widthLabyrinth;
    private final int heightLabyrinth;

    public GameData(Player[] players,
            int widthLabyrinth) {
        this(1, players, widthLabyrinth, NO_LENGTH);
    }

    public GameData(Player[] players,
            int widthLabyrinth, int heightLabyrinth) {
        this(2, players, widthLabyrinth, heightLabyrinth);
    }

    private GameData(int dimension, Player[] players,
            int widthLabyrinth, int heightLabyrinth) {

        this.dimension = dimension;
        this.players = players;
        this.widthLabyrinth = widthLabyrinth;
        this.heightLabyrinth = heightLabyrinth;
    }

    public int getDimension() {
        return dimension;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getWidthLabyrinth() {
        return widthLabyrinth;
    }

    public int getHeightLabyrinth() {
        return heightLabyrinth;
    }

}
