package com.gytmy.labyrinth.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.gytmy.utils.Bounds;
import com.gytmy.utils.HelpWindow;

public class StartMenu extends JPanel {
    private JFrame frame;

    private static final String[] IMAGES = {
            "src/resources/images/menu/MazeMenu.jpg",
            "src/resources/images/menu/StartButton.png",
            "src/resources/images/menu/QuestionMark.png",
            "src/resources/images/menu/Settings.png"
    };
    private static final Bounds[] IMAGES_BOUNDS = {
            new Bounds(0, 0, 800, 500),
            new Bounds(336, 350, 128, 56),
            new Bounds(750, 20, 32, 32),
            new Bounds(20, 20, 32, 32)
    };
    private static boolean[] isImageClickable = { false, true, true, true };

    private static String HELP_TEXT_FILE = "src/resources/helpTexts/menu.txt";

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
                } else if (IMAGES_BOUNDS[2].isInside(xClick, yClick)) {
                    showHelp();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        menu.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                int xClick = mouseEvent.getX();
                int yClick = mouseEvent.getY();

                if (isOnAClickableArea(xClick, yClick)) {
                    menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    menu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    private void showHelp() {
        HelpWindow.showHelp(menu, HELP_TEXT_FILE);
    }

    private boolean isOnAClickableArea(int x, int y) {

        for (int i = 0; i < IMAGES_BOUNDS.length; i++) {
            if (isImageClickable[i] && IMAGES_BOUNDS[i].isInside(x, y)) {
                return true;
            }
        }
        return false;
    }
}
