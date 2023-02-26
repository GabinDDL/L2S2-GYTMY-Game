package com.gytmy.labyrinth.view;

import java.awt.BorderLayout;
import java.awt.Cursor;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.gytmy.utils.Bounds;
import com.gytmy.utils.HelpWindow;

public class StartMenu extends JPanel {
    private JFrame frame;

    private static final String[] IMAGES = {
            "src/resources/images/menu/MazeMenu.jpg",
            "src/resources/images/menu/StartButton.png",
            "src/resources/images/menu/QuestionMark.png",
            "src/resources/images/menu/Settings.png",
            "src/resources/images/menu/MAZE_LOGO.gif"
    };
    private static final Bounds[] IMAGES_BOUNDS = {
            new Bounds(0, 0, 800, 500),
            new Bounds(336, 350, 128, 56),
            new Bounds(750, 20, 32, 32),
            new Bounds(20, 20, 32, 32),
            new Bounds(96, 88, 608, 224)
    };
    private static final boolean[] IS_IMAGE_CLICKABLE = { false, true, true, true, false };

    private static final String HELP_TEXT_FILE = "src/resources/helpTexts/menu.txt";

    private JPanelWithImages menu;

    public StartMenu(JFrame frame) {
        this.frame = frame;

        setLayout(new BorderLayout());

        initMenu();
        initMouseEventHandling();
    }

    private void initMenu() {

        menu = new JPanelWithImages(IMAGES, IMAGES_BOUNDS);

        add(menu);
    }

    private void initMouseEventHandling() {

        MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int xClick = mouseEvent.getX();
                int yClick = mouseEvent.getY();

                if (IMAGES_BOUNDS[1].isInside(xClick, yClick)) {
                    frame.setContentPane(new PlayerNumberSelectionMenu(frame));
                    GameFrameToolbox.frameUpdate(frame, "SettingsMenu");
                } else if (IMAGES_BOUNDS[2].isInside(xClick, yClick)) {
                    showHelp();
                } else if (IMAGES_BOUNDS[3].isInside(xClick, yClick)) {
                    frame.setContentPane(new OptionsMenu(frame));
                    GameFrameToolbox.frameUpdate(frame, "OptionsMenu");
                }
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
        };

        menu.addMouseListener(adapter);
        menu.addMouseMotionListener(adapter);
    }

    private void showHelp() {
        HelpWindow.showHelp(menu, HELP_TEXT_FILE);
    }

    private boolean isOnAClickableArea(int x, int y) {

        for (int i = 0; i < IMAGES_BOUNDS.length; i++) {
            if (IS_IMAGE_CLICKABLE[i] && IMAGES_BOUNDS[i].isInside(x, y)) {
                return true;
            }
        }
        return false;
    }
}
