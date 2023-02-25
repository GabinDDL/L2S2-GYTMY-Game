package com.gytmy.utils.input;

import javax.swing.JTextField;

public interface InputField {

    public boolean isValidInput();

    public static boolean areAllValidInputs(InputField... inputFields) {
        for (InputField inputField : inputFields) {
            if (!inputField.isValidInput()) {
                return false;
            }
        }
        return true;
    }

    public String getText();

    public void setText(String text);

    public JTextField getTextField();

}
