package com.gytmy.launcher;

import javax.swing.JFrame;

import com.gytmy.labyrinth.view.GameFrameToolbox;
import com.gytmy.labyrinth.view.StartMenu;

public class GraphicalLauncher implements Runnable {

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        StartMenu menu = new StartMenu(frame);
        frame.add(menu);
        GameFrameToolbox.frameUpdate(frame, "Menu");

        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
