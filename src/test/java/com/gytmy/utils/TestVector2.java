package com.gytmy.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestVector2 {

    @Test
    public void testToString() {
        Vector2 v1 = new Vector2(1, 2);
        assertEquals(v1.toString(), "(1, 2)");
    }

    @Test
    public void testEquals() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = new Vector2(1, 2);
        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));

        Vector2 v3 = new Vector2(1, 2);
        Vector2 v4 = new Vector2(2, 4);
        assertFalse(v3.equals(v4));
        assertFalse(v4.equals(v3));
    }

    @Test
    public void testCopy() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = new Vector2(1, 2);
        assertEquals(v1, v2);

    }

}
