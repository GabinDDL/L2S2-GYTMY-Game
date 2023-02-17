package com.gytmy.labyrinth;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CirclePanel extends JPanel {

    private static final int DIAMETER = 6;
    private Color color;

    public CirclePanel(Color color) {
        this.color = color;
        setPreferredSize(new Dimension(DIAMETER, DIAMETER));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set the color to the specified color
        g.setColor(color);

        // Draw a filled oval with the specified radius and color
        g.fillOval(0, 0, DIAMETER, DIAMETER);
    }
}
