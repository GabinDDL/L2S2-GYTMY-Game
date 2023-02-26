package com.gytmy.labyrinth.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;

public class OptionsMenu extends JPanel {
    private JFrame frame;

    public OptionsMenu(JFrame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());
    }
}
