package com.gytmy.labyrinth.view;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        initMouseEventHandling();
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

    private void initMouseEventHandling() {
        menu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                int xClick = mouseEvent.getX();
                int yClick = mouseEvent.getY();

                if (IMAGES_BOUNDS[1].isInside(xClick, yClick)) {
                    frame.setContentPane(new HowManyPlayersMenu(frame));
                    GameFrameToolbox.frameUpdate(frame, "SettingsMenu");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }
}
