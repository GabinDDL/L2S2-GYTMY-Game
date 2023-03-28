package com.gytmy.labyrinth.controller;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.score.ScoreCalculator;
import com.gytmy.labyrinth.model.score.ScoreType;
import com.gytmy.labyrinth.view.game.LabyrinthView;

public interface LabyrinthController {

    public LabyrinthView getView();

    public Player[] getPlayers();

    public void addKeyController(KeyboardMovementController controller);

    public void movePlayer(Player player, Direction direction);

    public ScoreCalculator getScoreCalculator(ScoreType type, Player player);

    public void notifyGameStarted();

}
