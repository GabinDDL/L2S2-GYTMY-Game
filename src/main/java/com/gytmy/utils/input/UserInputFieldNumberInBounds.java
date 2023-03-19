package com.gytmy.utils.input;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class UserInputFieldNumberInBounds extends UserInputField {

    private int lowerBound;
    private int upperBound;

    public UserInputFieldNumberInBounds(int lowerBound, int upperBound)
            throws IllegalArgumentException {

        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Lower bound must be smaller than upper bound");
        }

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        this.textField = new JTextField() {
            @Override
            public void paste() {
                String clipboardText = "";
                try {
                    clipboardText = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
                            .getData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                StringBuilder nonAlphabeticText = new StringBuilder();
                for (char c : clipboardText.toCharArray()) {
                    if (!Character.isAlphabetic(c)) {
                        nonAlphabeticText.append(c);
                    }
                }
                this.replaceSelection(nonAlphabeticText.toString());
            }
        };

        initTextFieldKeyListener();
        initTextFieldFocusListener();
    }

    private void initTextFieldKeyListener() {
        textField.addKeyListener(new KeyAdapter() {

            char typedChar;

            @Override
            public void keyTyped(KeyEvent evt) {

                typedChar = evt.getKeyChar();
                handleEnteredCharacter(evt);

            }

            private void handleEnteredCharacter(KeyEvent evt) {
                if (!isDigitCharacter(typedChar) &&
                        !isDeletionCharacter(typedChar))
                    evt.consume();
            }

            private boolean isDigitCharacter(char c) {
                return (c >= '0') && (c <= '9');
            }

            private boolean isDeletionCharacter(char c) {
                return (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE);
            }
        });

    }

    private void initTextFieldFocusListener() {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt) {
                if (textField.getText().isBlank()) {
                    return;
                }

                String inputString = textField.getText();
                int inputValue = Integer.parseInt(inputString);

                if (isInRangeOfBounds(inputValue)) {
                    return;
                }

                if (inputValue > upperBound) {
                    setValue(upperBound);
                } else if (inputValue < lowerBound) {
                    setValue(lowerBound);
                }
            }
        });
    }

    @Override
    public boolean isValidInput() {
        return super.isValidInput() &&
                isInRangeOfBounds(Integer.valueOf(super.getText()));
    }

    /**
     * @param value
     * @return true if lowerBound <= value <= upperBound
     */
    private boolean isInRangeOfBounds(int value) {
        return lowerBound <= value && value <= upperBound;
    }

    @Override
    public void setText(String text) throws NumberFormatException {
        if (text != null && text.isEmpty()) {
            super.setText("");
            return;
        }
        setValue(Integer.valueOf(text));
    }

    public int getValue() {
        return Integer.valueOf(super.getText());
    }

    public void setValue(int value) {
        if (isInRangeOfBounds(value)) {
            super.setText(String.valueOf(value));
        } else {
            super.setText(String.valueOf(value > upperBound ? upperBound : lowerBound));
        }
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
}
