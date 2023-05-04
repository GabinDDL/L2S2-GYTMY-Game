package com.gytmy.maze.controller;

import java.awt.event.KeyEvent;

import javax.swing.JComponent;

import com.gytmy.maze.model.Direction;
import com.gytmy.maze.model.player.Player;
import com.gytmy.utils.HotkeyAdder;

public class KeyboardMovementController implements MovementController {

    private MazeController controller;
    private Player[] players;

    private int selectedPlayer = 0;

    public KeyboardMovementController(MazeController controller) {
        this.controller = controller;
        this.players = controller.getPlayers();
    }

    @Override
    public void setup() {

        JComponent view = controller.getView();

        HotkeyAdder.addHotkey(view, KeyEvent.VK_UP, () -> movePlayer(Direction.UP), "Move player up");
        HotkeyAdder.addHotkey(view, KeyEvent.VK_DOWN, () -> movePlayer(Direction.DOWN), "Move player down");
        HotkeyAdder.addHotkey(view, KeyEvent.VK_LEFT, () -> movePlayer(Direction.LEFT), "Move player left");
        HotkeyAdder.addHotkey(view, KeyEvent.VK_RIGHT, () -> movePlayer(Direction.RIGHT), "Move player right");

        int[] player1Keys = { KeyEvent.VK_NUMPAD1, KeyEvent.VK_1, KeyEvent.VK_AMPERSAND };
        int[] player2Keys = { KeyEvent.VK_NUMPAD2, KeyEvent.VK_2, KeyEvent.VK_UNDEFINED };
        int[] player3Keys = { KeyEvent.VK_NUMPAD3, KeyEvent.VK_3, KeyEvent.VK_QUOTEDBL };
        int[] player4Keys = { KeyEvent.VK_NUMPAD4, KeyEvent.VK_4, KeyEvent.VK_QUOTE };
        int[] player5Keys = { KeyEvent.VK_NUMPAD5, KeyEvent.VK_5, KeyEvent.VK_LEFT_PARENTHESIS };

        int[][] playersKeys = { player1Keys, player2Keys, player3Keys, player4Keys, player5Keys };

        for (int index = 0; index < playersKeys.length; index++) {
            for (int key : playersKeys[index]) {
                final int playerId = index;
                HotkeyAdder.addHotkey(view, key, () -> changeSelectedPlayer(playerId),
                        "[Key " + key + "] Select player " + (playerId + 1));
            }
        }
    }

    private void movePlayer(Direction direction) {
        controller.movePlayer(players[selectedPlayer], direction);
    }

    private void changeSelectedPlayer(int playerId) {
        if (playerId < 0 || playerId >= players.length) {
            return;
        }
        selectedPlayer = playerId;
    }

}
