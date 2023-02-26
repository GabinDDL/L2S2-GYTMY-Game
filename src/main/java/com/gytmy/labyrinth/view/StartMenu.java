package com.gytmy.labyrinth.view;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gytmy.utils.Bounds;

public class StartMenu extends JPanel {
    private JFrame frame;

    private static final String[] IMAGES = {
            "src/resources/images/MazeMenu.jpg",
            "src/resources/images/StartButton.png"
    };
    private static final Bounds[] IMAGES_BOUNDS = {
            new Bounds(0, 0, 800, 500),
            new Bounds(336, 350, 128, 56)
    };
    private JPanelWithImages menu;

    public StartMenu(JFrame frame) {
        this.frame = frame;

        setLayout(new BorderLayout());

        initMenu();
        handleClickEventOnMenu();
    }

    private void initMenu() {
        try {
            menu = new JPanelWithImages(IMAGES, IMAGES_BOUNDS);
        } catch (IOException ioe) {
            System.out.println("Error while loading one of the images");
            System.out.println(ioe.getMessage());
            ioe.getStackTrace();
        }

        add(menu);
    }

    private void handleClickEventOnMenu() {

    }
}
