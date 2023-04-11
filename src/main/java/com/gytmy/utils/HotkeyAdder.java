package com.gytmy.utils;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class HotkeyAdder {

    private HotkeyAdder() {
    }

    public static void addHotkey(JComponent component, int key, Runnable actionPerformed, String name) {
        // define the action to be performed when the shortcut is pressed
        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                actionPerformed.run();
            }
        };

        // create a KeyStroke object to represent the key combination
        KeyStroke keyStroke = KeyStroke.getKeyStroke(key, 0);

        // map the key combination to the action using the component's input map
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
        component.getActionMap().put(name, action);
    }
}
