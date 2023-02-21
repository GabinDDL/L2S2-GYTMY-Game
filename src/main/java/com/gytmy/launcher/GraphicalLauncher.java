package com.gytmy.launcher;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.gytmy.utils.GameFrameToolbox;

public class GraphicalLauncher implements Runnable {

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StartMenu menu = new StartMenu(frame);
        frame.add(menu);
        GameFrameToolbox.frameUpdate(frame, "StartMenu");
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
