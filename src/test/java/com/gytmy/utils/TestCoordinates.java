package com.gytmy.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestCoordinates {

    @Test
    public void testToString() {
        Coordinates v1 = new Coordinates(1, 2);
        assertEquals(v1.toString(), "(1, 2)");
    }

    @Test
    public void testEquals() {
        Coordinates v1 = new Coordinates(1, 2);
        Coordinates v2 = new Coordinates(1, 2);
        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));

        Coordinates v3 = new Coordinates(1, 2);
        Coordinates v4 = new Coordinates(2, 4);
        assertFalse(v3.equals(v4));
        assertFalse(v4.equals(v3));
    }

    @Test
    public void testCopy() {
        Coordinates v1 = new Coordinates(1, 2);
        Coordinates v2 = new Coordinates(1, 2);
        assertEquals(v1, v2);

    }

}
