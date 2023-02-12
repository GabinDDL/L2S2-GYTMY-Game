package com.gytmy;

public class UserInputFieldRange extends UserInputField {

  private int lowerBound;
  private int upperBound;

  public UserInputFieldRange(int lowerBound, int upperBound) {

    super();
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;

  }

  @Override
  public boolean isValidInput() {
    String strippedText = super.getText().strip();
    return super.isValidInput() &&
        isOnlyDigits(strippedText) &&
        isInRangeOfBounds(Integer.valueOf(strippedText));
  }

  /**
   * @param value
   * @return true if value is in [lowerBound; upperBound] (INCLUSIVE);
   *         false otherwise
   */
  private boolean isInRangeOfBounds(int value) {
    return lowerBound <= value && value <= upperBound;
  }

  private boolean isOnlyDigits(String input) {
    for (char character : input.toCharArray()) {
      if (!('0' <= character && character <= '9')) {
        return false;
      }
    }
    return true;
  }

  public int getValue() {
    String strippedText = super.getText().strip();
    int value = Integer.valueOf(strippedText);
    return value;
  }

}
