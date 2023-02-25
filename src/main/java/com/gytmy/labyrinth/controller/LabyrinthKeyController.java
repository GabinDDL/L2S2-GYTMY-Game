package com.gytmy.labyrinth.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.player.Player;

public class LabyrinthKeyController extends KeyAdapter {

    private LabyrinthController controller;
    private Player[] players;

    private int selectedPlayer = 0;

    public LabyrinthKeyController(LabyrinthController controller) {
        this.controller = controller;
        this.players = controller.getPlayers();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                controller.movePlayer(players[selectedPlayer], Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                controller.movePlayer(players[selectedPlayer], Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                controller.movePlayer(players[selectedPlayer], Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                controller.movePlayer(players[selectedPlayer], Direction.RIGHT);
                break;
            case KeyEvent.VK_1:
                changeSelectedPlayer(0);
                break;
            case KeyEvent.VK_2:
                changeSelectedPlayer(1);
                break;
            case KeyEvent.VK_3:
                changeSelectedPlayer(2);
                break;
            case KeyEvent.VK_4:
                changeSelectedPlayer(3);
                break;
            case KeyEvent.VK_5:
                changeSelectedPlayer(4);
                break;
            default:
                break;
        }
    }

    private void changeSelectedPlayer(int player) {
        if (player < 0 || player >= players.length) {
            return;
        }
        selectedPlayer = player;
    }

}
