package com.gytmy.maze.view.game.end_transition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.gytmy.maze.view.MenuFrameHandler;
import com.gytmy.maze.view.game.Cell;

class DrawShapes extends JPanel {

    private static final Dimension MAX_DIMENSION = MenuFrameHandler.DEFAULT_DIMENSION;

    private static final Color COLOR_TRANSITION = Color.BLACK;
    private static final Color COLOR_DISAPPEARING = Cell.WALL_COLOR;

    private static final int REMOVE_TIMER_DELAY = 25; // ms
    private static final int APPARITION_TIMER_DELAY = 30; // ms

    private static final int DISAPPEARING_STEP_SPEED = 3;

    private List<AnimationMazePanel> shapeList = new ArrayList<>();
    private List<AnimationObserver> observers = new ArrayList<>();

    private int x = 0;
    private int y = 0;

    DrawShapes(Dimension defaultDimension) {
        setPreferredSize(defaultDimension);
        setSize(defaultDimension);

        int squareHeight = defaultDimension.height / 6;

        new Timer(APPARITION_TIMER_DELAY, evt -> {

            shapeList.add(
                    new AnimationMazePanel(new Rectangle(x, y, squareHeight, squareHeight), COLOR_TRANSITION));
            repaint();

            x += squareHeight;
            if (x > defaultDimension.width) {
                x = 0;
                y += squareHeight;
            }

            if (y >= defaultDimension.height) {
                ((Timer) evt.getSource()).stop();
                notifyObservers();
            }
        }).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (AnimationMazePanel shape : shapeList) {
            g2.setColor(shape.getColor());
            g2.fill(shape.getShape());
            g2.draw(shape.getShape());
        }
    }

    public void clearAnimation() {
        shapeList.clear();
        shapeList.add(
                new AnimationMazePanel(new Rectangle(0, 0, MAX_DIMENSION.width, MAX_DIMENSION.height),
                        COLOR_DISAPPEARING));
        removeAnimation();
    }

    private void removeAnimation() {
        new Timer(REMOVE_TIMER_DELAY, evt -> {

            Color actualColor = shapeList.get(0).getColor();

            if (actualColor.getAlpha() - DISAPPEARING_STEP_SPEED < 0) {
                ((Timer) evt.getSource()).stop();
                notifyObservers();
            }

            if (actualColor.getAlpha() > 0) {
                Color newColor = new Color(actualColor.getRed(), actualColor.getGreen(), actualColor.getBlue(),
                        actualColor.getAlpha() - DISAPPEARING_STEP_SPEED);
                shapeList.clear();
                shapeList.add(new AnimationMazePanel(new Rectangle(new Point(0, 0), MAX_DIMENSION), newColor));

                repaint();
            }
        }).start();
    }

    public void addAnimationObserver(AnimationObserver observer) {
        observers.add(observer);
    }

    public void removeAnimationObserver(AnimationObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(AnimationObserver::endAnimationUpdate);
    }

}
