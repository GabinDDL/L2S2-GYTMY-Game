package com.gytmy.maze.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.gytmy.maze.model.Direction;
import com.gytmy.maze.view.game.MazeView;
import com.gytmy.utils.HotkeyAdder;

public class KeyboardMovementController {

    private MazeController controller;

    private MazeView view;

    private boolean isKeyboardMovementEnabled = false;

    public KeyboardMovementController(MazeController controller) {
        this.controller = controller;
        this.view = controller.getView();

        setup();
    }

    public void setup() {

        initToggleKeyboardMovementKeyBind();

        HotkeyAdder.addHotkey(view, KeyEvent.VK_UP, () -> movePlayer(Direction.UP), "Move player up");
        HotkeyAdder.addHotkey(view, KeyEvent.VK_DOWN, () -> movePlayer(Direction.DOWN), "Move player down");
        HotkeyAdder.addHotkey(view, KeyEvent.VK_LEFT, () -> movePlayer(Direction.LEFT), "Move player left");
        HotkeyAdder.addHotkey(view, KeyEvent.VK_RIGHT, () -> movePlayer(Direction.RIGHT), "Move player right");

    }

    private void initToggleKeyboardMovementKeyBind() {
        HotkeyAdder.addHotkey(view, KeyEvent.VK_T, this::toggleKeyboardMovement, "Enable / Disable Keyboard Movement");

        view.getKeyboardMovementSwitchPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleKeyboardMovement();
            }
        });
    }

    private void toggleKeyboardMovement() {
        isKeyboardMovementEnabled = !isKeyboardMovementEnabled;
        view.toggleKeyboardMovement(isKeyboardMovementEnabled);
    }

    private void movePlayer(Direction direction) {
        if (!isKeyboardMovementEnabled) {
            return;
        }
        controller.movePlayer(direction);
    }

}
