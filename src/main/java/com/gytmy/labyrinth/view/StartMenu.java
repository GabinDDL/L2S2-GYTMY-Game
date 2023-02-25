package com.gytmy.labyrinth.view;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartMenu extends JPanel {
    private JFrame frame;

    private static final String BACKGROUND_IMAGE = "src/resources/images/MazeMenu.jpg";
    private JPanel menu;

    public StartMenu(JFrame frame) {
        this.frame = frame;

        setLayout(new BorderLayout());

        initMenu();
    }

    private void initMenu() {
        menu = new JPanel(new BorderLayout());

        initImageBackground();
        initPlayButton();

        add(menu, BorderLayout.CENTER);
    }

    private void initImageBackground() {
        JPanel background = null;
        try {
            background = new JPanelWithBackground(BACKGROUND_IMAGE);
        } catch (IOException ioe) {
            System.out.println("Error while loading the background image");
            System.out.println(ioe.getMessage());
            ioe.getStackTrace();
        }
        menu.add(background);
    }

    private void initPlayButton() {
        JButton playButton = new JButton("Play");
        playButton.addActionListener(event -> {
            frame.setContentPane(new HowManyPlayersMenu(frame));
            GameFrameToolbox.frameUpdate(frame, "HowManyPlayersMenu");
        });
        menu.add(playButton, BorderLayout.SOUTH);
    }
}
