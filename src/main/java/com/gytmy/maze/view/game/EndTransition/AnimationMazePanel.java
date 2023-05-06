package com.gytmy.maze.view.game.EndTransition;

import java.awt.Color;
import java.awt.Shape;

public class AnimationMazePanel {

    private Shape shape;
    private Color color;

    public AnimationMazePanel(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }
}
