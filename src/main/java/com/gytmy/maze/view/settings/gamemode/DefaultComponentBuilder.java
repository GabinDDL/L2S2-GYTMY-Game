package com.gytmy.maze.view.settings.gamemode;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.gytmy.maze.view.game.Cell;
import com.gytmy.utils.input.UserInputFieldNumberInBounds;

/**
 * This class is used to build default components for the GameModeHandlers. It
 * is used to avoid code duplication and to ensure that all components have the
 * same appearance.
 */
public class DefaultComponentBuilder {

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    private static final Color FOREGROUND_COLOR = Cell.PATH_COLOR;

    private static final Font FONT = new Font("Arial", Font.BOLD, 25);

    private DefaultComponentBuilder() {
    }

    /**
     * Builds a JTextField that only accepts numbers between the given bounds.
     * The bounds are included and the default options are used:
     * <ul>
     * <li>Background color: {@link Cell#WALL_COLOR}</li>
     * <li>Foreground color: {@link Cell#PATH_COLOR}</li>
     * <li>Font: {@link #FONT}</li>
     * </ul>
     * 
     */
    public static JTextField buildInputField(int lowerBound, int upperBound) {
        JTextField inputField = new UserInputFieldNumberInBounds(lowerBound, upperBound).getTextField();
        inputField.setFont(FONT);
        inputField.setText("" + getMiddleValue(lowerBound, upperBound));
        setBackgroundAndForeground(inputField);
        return inputField;
    }

    private static int getMiddleValue(int lowerBound, int upperBound) {
        return (lowerBound + upperBound) / 2;
    }

    /**
     * Builds a JLabel with the given text. The default options are used:
     * <ul>
     * <li>Background color: {@link Cell#WALL_COLOR}</li>
     * <li>Foreground color: {@link Cell#PATH_COLOR}</li>
     * <li>Font: {@link #FONT}</li>
     * </ul>
     * 
     */
    public static JLabel buildLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT);
        setBackgroundAndForeground(label);
        return label;
    }

    /**
     * Builds a GridBagConstraints with the given gridx, gridy and insets. The
     * default options are used:
     * <ul>
     * <li>Fill: {@link GridBagConstraints#BOTH}</li>
     * </ul>
     * 
     */
    public static GridBagConstraints buildGridBagConstraints(int gridx, int gridy, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = insets;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    /**
     * Sets the background and foreground color of the given component to the
     * default values:
     * <ul>
     * <li>Background color: {@link Cell#WALL_COLOR}</li>
     * <li>Foreground color: {@link Cell#PATH_COLOR}</li>
     * </ul>
     * 
     */
    public static void setBackgroundAndForeground(Component component) {
        component.setBackground(BACKGROUND_COLOR);
        component.setForeground(FOREGROUND_COLOR);
    }

}
