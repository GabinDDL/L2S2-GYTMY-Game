package com.gytmy.labyrinth.view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.gytmy.utils.Bounds;

public class JPanelWithImages extends JPanel {

    private List<Image> images;
    private Bounds[] bounds;

    public JPanelWithImages(String[] filesName, Bounds[] bounds)
            throws IOException, IllegalArgumentException {

        if (filesName.length != bounds.length) {
            throw new IllegalArgumentException("The number of files and the number of coordinates must be the same");
        }

        images = new ArrayList<>();
        this.bounds = bounds;

        for (String fileName : filesName) {
            images.add(ImageIO.read(new File(fileName)));
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the images.
        // g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        for (int i = 0; i < images.size(); i++) {
            g.drawImage(images.get(i),
                    bounds[i].getX(), bounds[i].getY(),
                    bounds[i].getWidth(), bounds[i].getHeight(),
                    this);
        }
    }
}