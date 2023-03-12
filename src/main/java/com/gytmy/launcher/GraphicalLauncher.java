package com.gytmy.launcher;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.gytmy.labyrinth.view.GameFrameToolbox;

public class GraphicalLauncher implements Runnable {

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GameFrameToolbox.setMainFrame(frame);

        GameFrameToolbox.goToStartMenu();

        GameFrameToolbox.initMenus();

        try {
            frame.setIconImage(ImageIO.read(new File("src/resources/images/gytmy_logo.png")));
        } catch (IOException e) {
            // If the image doesn't load, we want to continue anyway
        }

        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
