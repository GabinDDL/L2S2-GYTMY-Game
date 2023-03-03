package com.gytmy.utils.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserInputFieldNumberInBounds extends UserInputField {

    private int lowerBound;
    private int upperBound;
    public static final int NO_VALUE = -1;

    public UserInputFieldNumberInBounds(int lowerBound, int upperBound)
            throws IllegalArgumentException {
        super();
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Lower bound must be smaller than upper bound");
        }

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        super.textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    evt.consume();
                }
            }
        });
    }

    @Override
    public boolean isValidInput() {
        String strippedText = super.getText().strip();
        return super.isValidInput() &&
                isValidInteger(strippedText) &&
                isInRangeOfBounds(Integer.valueOf(strippedText));
    }

    /**
     * @param value
     * @return true if lowerBound <= value <= upperBound
     */
    private boolean isInRangeOfBounds(int value) {
        return lowerBound <= value && value <= upperBound;
    }

    private boolean isValidInteger(String input) {
        try {
            Integer.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getValue() {
        if (isValidInput()) {
            return Integer.valueOf(super.getText().strip());
        }
        return NO_VALUE;
    }

    public void setValue(int value) {
        if (isInRangeOfBounds(value)) {
            super.setText(String.valueOf(value));
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

    public static int getNoValue() {
        return NO_VALUE;
    }

}
