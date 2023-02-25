package com.gytmy.utils.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestUserInputField {

    @Test
    public void testConstructor() {
        UserInputField field = new UserInputField();
        assertEquals(UserInputField.DEFAULT_SIZE, field.getTextField().getColumns());
        assertEquals("", field.getTextField().getText());
    }

    @Test
    public void testIsValidInputTrue() {
        UserInputField field = new UserInputField();
        field.setText("abc");
        assertTrue(field.isValidInput());
        field.setText(" abc ");
        assertTrue(field.isValidInput());
        field.setText("a");
        assertTrue(field.isValidInput());
        field.setText("  a");
        assertTrue(field.isValidInput());
        field.setText("  abc");
        assertTrue(field.isValidInput());
        field.setText("\t a");
        assertTrue(field.isValidInput());
        field.setText("\t abc");
        assertTrue(field.isValidInput());
        field.setText(" \t a");
        assertTrue(field.isValidInput());
        field.setText(" \t abc");
        assertTrue(field.isValidInput());
        field.setText(" \t abc \t ");
        assertTrue(field.isValidInput());

    }

    @Test
    public void testIsValidInputFalse() {
        UserInputField field = new UserInputField();
        field.setText(" \t ");
        assertFalse(field.isValidInput());
        field.setText("\t");
        assertFalse(field.isValidInput());
        field.setText("\t\t");
        assertFalse(field.isValidInput());
        field.setText("  ");
        assertFalse(field.isValidInput());
        field.setText(" ");
        assertFalse(field.isValidInput());
        field.setText("");
        assertFalse(field.isValidInput());
    }

    @Test
    public void testGetTextSetText() {
        UserInputField field = new UserInputField();
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

}
