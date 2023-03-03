package com.gytmy.utils;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageManipulator {

    private ImageManipulator() {
    }

    /**
     * Generates an {@code ImageIcon} from the given path with the given size. An
     * {@code ImageIcon} is used to display an image.
     * 
     * @param path      the path of the image
     * @param dimension the size of the image
     * @return the {@code ImageIcon}
     */
    public static ImageIcon initImageAsImageIcon(String path, Dimension dimension) {
        return initImageAsImageIcon(path, dimension.width, dimension.height);
    }

    /**
     * Generates an {@code ImageIcon} from the given path with the given size. An
     * {@code ImageIcon} is used to display an image.
     * 
     * @param path   the path of the image
     * @param width  the width of the image
     * @param height the height of the image
     * @return the {@code ImageIcon}
     */
    public static ImageIcon initImageAsImageIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);

        Image resizedImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

}
