package com.gytmy.launcher;

import javax.swing.JFrame;

import com.gytmy.utils.GameFrameToolbox;

public class GraphicalLauncher implements Runnable {

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        StartMenu menu = new StartMenu(frame);
        frame.add(menu);
        GameFrameToolbox.frameUpdate(frame, "StartMenu");
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
