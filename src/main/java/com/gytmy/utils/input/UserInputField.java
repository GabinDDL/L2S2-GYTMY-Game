package com.gytmy.utils.input;

import java.awt.Color;

import javax.swing.JTextField;

public class UserInputField implements InputField {

    // JTextFields need a default size if text is empty
    // We chose 16 for reading comfort
    public static final int DEFAULT_SIZE = 16;

    protected JTextField textField;

    public UserInputField(String text) {
        this.textField = new JTextField(text);
    }

    public UserInputField(int size) {
        this.textField = new JTextField(size);
    }

    public UserInputField() {
        this(DEFAULT_SIZE);
    }

    @Override
    public boolean isValidInput() {
        return textField.getText() != null &&
                !textField.getText().isBlank();
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        String formattedText = formatString(text);
        textField.setText(formattedText);
    }

    private static String formatString(String text) {
        String textCleanTabs = text.replace("\t", " ");
        String textCleanNewlines = textCleanTabs.replace("\n", " ");
        String textCleanLeadingWhitespace = textCleanNewlines.stripLeading();
        return textCleanLeadingWhitespace.stripTrailing();
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setBackground(Color color) {
        textField.setBackground(color);
    }

    public void setForeground(Color color) {
        textField.setForeground(color);
    }
}
