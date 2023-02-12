package com.gytmy;

import javax.swing.JTextField;

public class UserInputField implements InputField {

  private JTextField textField;

  public UserInputField(String text) {
    this.textField = new JTextField(text);
  }

  public UserInputField(int size) {
    this.textField = new JTextField(size);
  }

  public UserInputField() {
    this(5);
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
