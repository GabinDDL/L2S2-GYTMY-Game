package com.gytmy.utils.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import java.lang.NumberFormatException;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestUserInputFieldNumberInBounds {

    @Test
    public void testConstructor() {
        UserInputFieldNumberInBounds field = new UserInputFieldNumberInBounds(0, 10);
        assertEquals(0, field.getLowerBound());
        assertEquals(10, field.getUpperBound());
    }

    @Test
    public void testConstructorIllegalBounds() {
        TestingUtils.assertArgumentExceptionMessage(
                () -> new UserInputFieldNumberInBounds(10, 0),
                "Lower bound must be smaller than upper bound");
    }

    @Test
    public void testIsValidSetText() {
        UserInputFieldNumberInBounds field = new UserInputFieldNumberInBounds(0, 10);
        assertThrows(NumberFormatException.class, () -> field.setText("a"));
        assertThrows(NumberFormatException.class, () -> field.setText("1a"));
        assertThrows(NumberFormatException.class, () -> field.setText("a1"));
        assertThrows(NumberFormatException.class, () -> field.setText("1.1"));
        assertThrows(NumberFormatException.class, () -> field.setText("1,1"));
        assertThrows(NumberFormatException.class, () -> field.setText("1 1"));
        assertThrows(NumberFormatException.class, () -> field.setText("1\t1"));
    }

    @Test
    public void testIsValidInputNotInBounds() {
        UserInputFieldNumberInBounds field = new UserInputFieldNumberInBounds(0, 10);
        field.setText("-1");
        assertEquals(field.getLowerBound(), field.getValue());
        field.setText("11");
        assertEquals(field.getUpperBound(), field.getValue());
    }

    @Test
    public void testIsValidInputInBounds() {
        UserInputFieldNumberInBounds field = new UserInputFieldNumberInBounds(0, 10);
        field.setText("0");
        assertTrue(field.isValidInput());
        field.setText("10");
        assertTrue(field.isValidInput());
        field.setText("5");
        assertTrue(field.isValidInput());
    }

    @Test
    public void testGetValue() {
        UserInputFieldNumberInBounds field = new UserInputFieldNumberInBounds(0, 10);
        field.setText("0");
        assertEquals(0, field.getValue());
        field.setText("10");
        assertEquals(10, field.getValue());
        field.setText("5");
        assertEquals(5, field.getValue());
    }

}