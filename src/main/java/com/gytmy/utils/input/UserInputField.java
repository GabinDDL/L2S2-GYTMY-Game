package com.gytmy.utils.input;

import javax.swing.JTextField;

public class UserInputField implements InputField {

    // JTextFields need a default size if text is empty
    // We chose 16 for reading comfort
    public static final int DEFAULT_SIZE = 16;

    private JTextField textField;

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
        String text = textField.getText();
        String strippedText = text.strip();
        return !strippedText.equals("");
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

    public void setBackground(java.awt.Color color) {
        textField.setBackground(color);
    }

    public void setForeground(java.awt.Color color) {
        textField.setForeground(color);
    }
}
