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
    public static ImageIcon resizeImage(String path, Dimension dimension) {
        return resizeImage(path, dimension.width, dimension.height);
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
    public static ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);

        Image resizedImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static ImageIcon resizeImage(String path, double scale) {
        ImageIcon icon = new ImageIcon(path);

        int width = (int) (icon.getIconWidth() * scale);
        int height = (int) (icon.getIconHeight() * scale);

        Image resizedImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static int getWidth(String path) {
        return getDimension(path).width;
    }

    public static int getHeight(String path) {
        return getDimension(path).height;
    }

    /**
     * Generates a {@code Dimension} from the given path. The {@code Dimension} is
     * used to set the size of an image.
     * 
     * @param path the path of the image
     * @return the {@code Dimension}
     */
    public static Dimension getDimension(String path) {
        ImageIcon icon = new ImageIcon(path);
        return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }
}
