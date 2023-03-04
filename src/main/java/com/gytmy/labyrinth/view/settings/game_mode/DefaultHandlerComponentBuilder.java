package com.gytmy.labyrinth.view.settings.game_mode;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.gytmy.labyrinth.view.Cell;
import com.gytmy.utils.input.UserInputFieldNumberInBounds;

/**
 * This class is used to build default components for the GameModeHandlers. It
 * is used to avoid code duplication.
 */
public class DefaultHandlerComponentBuilder {

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    private static final Color FOREGROUND_COLOR = Cell.PATH_COLOR;

    private static final Font FONT = new Font("Arial", Font.BOLD, 25);

    private DefaultHandlerComponentBuilder() {
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
        setBackgroundAndForeground(inputField);
        return inputField;
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
     * Builds a GridBagConstraints with the given gridx and gridy. The default
     * options are used:
     * <ul>
     * <li>Insets: 20px on all sides</li>
     * <li>Fill: {@link GridBagConstraints#BOTH}</li>
     * </ul>
     * 
     */
    public static GridBagConstraints buildGridBagConstraints(int gridx, int gridy) {
        return buildGridBagConstraints(gridx, gridy, new Insets(20, 20, 20, 20));
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
