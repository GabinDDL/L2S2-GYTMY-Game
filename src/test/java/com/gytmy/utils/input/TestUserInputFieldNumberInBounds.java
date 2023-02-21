package com.gytmy.utils.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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
    public void testIsValidInputNotInteger() {
        UserInputFieldNumberInBounds field = new UserInputFieldNumberInBounds(0, 10);
        field.setText("abc");
        assertFalse(field.isValidInput());
        field.setText(" abc ");
        assertFalse(field.isValidInput());
        field.setText(" ");
        assertFalse(field.isValidInput());
        field.setText("");
        assertFalse(field.isValidInput());
        field.setText("a");
        assertFalse(field.isValidInput());
        field.setText("  ");
        assertFalse(field.isValidInput());
        field.setText("  a");
        assertFalse(field.isValidInput());
        field.setText("  abc");
        assertFalse(field.isValidInput());
        field.setText("\t");
        assertFalse(field.isValidInput());
        field.setText("\t\t");
        assertFalse(field.isValidInput());
        field.setText("\t a");
        assertFalse(field.isValidInput());
        field.setText("\t abc");
        assertFalse(field.isValidInput());
        field.setText(" \t ");
        assertFalse(field.isValidInput());
        field.setText(" \t a");
        assertFalse(field.isValidInput());
        field.setText(" \t abc");
        assertFalse(field.isValidInput());
        field.setText(" \t abc \t ");
        assertFalse(field.isValidInput());
    }

    @Test
    public void testIsValidInputNotInBounds() {
        UserInputFieldNumberInBounds field = new UserInputFieldNumberInBounds(0, 10);
        field.setText("-1");
        assertFalse(field.isValidInput());
        field.setText("11");
        assertFalse(field.isValidInput());
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
    public void testGetTextSetText() {
        UserInputFieldNumberInBounds field = new UserInputFieldNumberInBounds(0, 10);
        field.setText("abc");
        assertEquals("abc", field.getText());
        field.setText(" abc ");
        assertEquals("abc", field.getText());
        field.setText(" ");
        assertEquals("", field.getText());
        field.setText("");
        assertEquals("", field.getText());
        field.setText("a");
        assertEquals("a", field.getText());
        field.setText("  ");
        assertEquals("", field.getText());
        field.setText("  a");
        assertEquals("a", field.getText());
        field.setText("  abc");
        assertEquals("abc", field.getText());
        field.setText("\t");
        assertEquals("", field.getText());
        field.setText("\t\t");
        assertEquals("", field.getText());
        field.setText("\t a");
        assertEquals("a", field.getText());
        field.setText("\t abc");
        assertEquals("abc", field.getText());
        field.setText(" \t ");
        assertEquals("", field.getText());
        field.setText(" \t a");
        assertEquals("a", field.getText());
        field.setText(" \t abc");
        assertEquals("abc", field.getText());
        field.setText(" \t abc \t ");
        assertEquals("abc", field.getText());
    }

    @Test
    public void testGetValueNoValue() {
        UserInputFieldNumberInBounds field = new UserInputFieldNumberInBounds(0, 10);
        field.setText("abc");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText(" abc ");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText(" ");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText("");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText("a");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText("  ");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText("  a");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText("  abc");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText("\t");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText("\t\t");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText("\t a");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText("\t abc");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText(" \t ");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText(" \t a");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText(" \t abc");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
        field.setText(" \t abc \t ");
        assertEquals(UserInputFieldNumberInBounds.NO_VALUE, field.getValue());
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