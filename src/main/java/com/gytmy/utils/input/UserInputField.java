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
        textField.setText(text);
    }

    public JTextField getTextField() {
        return textField;
    }

}
