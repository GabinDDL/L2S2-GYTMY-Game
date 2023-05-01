package com.gytmy.maze.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.gytmy.maze.model.Direction;
import com.gytmy.maze.model.player.Player;

public class KeyboardMovementController extends KeyAdapter implements MovementController {

    private MazeController controller;
    private Player[] players;

    private int selectedPlayer = 0;

    public KeyboardMovementController(MazeController controller) {
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
            case KeyEvent.VK_AMPERSAND:
            case KeyEvent.VK_NUMPAD1:
            case KeyEvent.VK_1:
                changeSelectedPlayer(0);
                break;
            case KeyEvent.VK_UNDEFINED:
            case KeyEvent.VK_NUMPAD2:
            case KeyEvent.VK_2:
                changeSelectedPlayer(1);
                break;
            case KeyEvent.VK_QUOTEDBL:
            case KeyEvent.VK_NUMPAD3:
            case KeyEvent.VK_3:
                changeSelectedPlayer(2);
                break;
            case KeyEvent.VK_QUOTE:
            case KeyEvent.VK_NUMPAD4:
            case KeyEvent.VK_4:
                changeSelectedPlayer(3);
                break;
            case KeyEvent.VK_LEFT_PARENTHESIS:
            case KeyEvent.VK_NUMPAD5:
            case KeyEvent.VK_5:
                changeSelectedPlayer(4);
                break;
            default:
                break;
        }
    }

    private void changeSelectedPlayer(int playerId) {
        if (playerId < 0 || playerId >= players.length) {
            return;
        }
        selectedPlayer = playerId;
    }

    @Override
    public void setup() {
        controller.addKeyController(this);
    }

}