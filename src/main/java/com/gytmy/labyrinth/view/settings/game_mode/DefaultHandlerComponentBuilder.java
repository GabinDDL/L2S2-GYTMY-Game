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

public class DefaultHandlerComponentBuilder {

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    private static final Color FOREGROUND_COLOR = Cell.PATH_COLOR;

    private static final Font FONT = new Font("Arial", Font.BOLD, 25);

    private DefaultHandlerComponentBuilder() {
    }

    public static JTextField buildInputField(int lowerBound, int upperBound) {
        JTextField inputField = new UserInputFieldNumberInBounds(lowerBound, upperBound).getTextField();
        inputField.setFont(FONT);
        setBackgroundAndForeground(inputField);
        return inputField;
    }

    public static JLabel buildLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT);
        setBackgroundAndForeground(label);
        return label;
    }

    public static GridBagConstraints buildGridBagConstraints(int gridx, int gridy) {
        return buildGridBagConstraints(gridx, gridy, new Insets(20, 20, 20, 20));
    }

    public static GridBagConstraints buildGridBagConstraints(int gridx, int gridy, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = insets;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    public static void setBackgroundAndForeground(Component component) {
        component.setBackground(BACKGROUND_COLOR);
        component.setForeground(FOREGROUND_COLOR);
    }

}
