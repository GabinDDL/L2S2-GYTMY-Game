package com.gytmy.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class TestVector2 {

    @Test
    public void testAddPositiveVectors() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = new Vector2(3, 4);
        Vector2 v3 = v1.add(v2);
        assertEquals(v3.getX(), 4);
        assertEquals(v3.getY(), 6);
    }

    @Test
    public void testAddNegativeVectors() {
        Vector2 v1 = new Vector2(-1, -2);
        Vector2 v2 = new Vector2(-3, -4);
        Vector2 v3 = v1.add(v2);
        assertEquals(v3.getX(), -4);
        assertEquals(v3.getY(), -6);
    }

    @Test
    public void testSubPositiveVectors() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = new Vector2(3, 4);
        Vector2 v3 = v1.sub(v2);
        assertEquals(v3.getX(), -2);
        assertEquals(v3.getY(), -2);
    }

    @Test
    public void testSubNegativeVectors() {
        Vector2 v1 = new Vector2(-1, -2);
        Vector2 v2 = new Vector2(-3, -4);
        Vector2 v3 = v1.sub(v2);
        assertEquals(v3.getX(), 2);
        assertEquals(v3.getY(), 2);
    }

    @Test
    public void testSubOnlyOneNegativeCoordinate() {
        Vector2 v1 = new Vector2(-1, 2);
        Vector2 v2 = new Vector2(-3, 4);
        Vector2 v3 = v1.sub(v2);
        assertEquals(v3.getX(), 2);
        assertEquals(v3.getY(), -2);

        Vector2 v4 = new Vector2(1, -2);
        Vector2 v5 = new Vector2(3, -4);
        Vector2 v6 = v4.sub(v5);
        assertEquals(v6.getX(), -2);
        assertEquals(v6.getY(), 2);
    }

    @Test
    public void testMulByZero() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = v1.mul(0);
        assertEquals(v2.getX(), 0);
        assertEquals(v2.getY(), 0);
    }

    @Test
    public void testMulByOne() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = v1.mul(1);
        assertEquals(v2.getX(), 1);
        assertEquals(v2.getY(), 2);
    }

    @Test
    public void testMulByNegative() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = v1.mul(-1);
        assertEquals(v2.getX(), -1);
        assertEquals(v2.getY(), -2);
    }

    @Test
    public void testMulByPositive() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = v1.mul(2);
        assertEquals(v2.getX(), 2);
        assertEquals(v2.getY(), 4);
    }

    @Test
    public void testDivByZero() {
        Vector2 v1 = new Vector2(1, 2);
        Exception exceptionZero = assertThrows(IllegalArgumentException.class,
                () -> v1.div(0));
        assertEquals("Cannot divide by zero", exceptionZero.getMessage());
    }

    @Test
    public void testDivByOne() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = v1.div(1);
        assertEquals(v2.getX(), 1);
        assertEquals(v2.getY(), 2);
    }

    @Test
    public void testDivByNegative() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = v1.div(-1);
        assertEquals(v2.getX(), -1);
        assertEquals(v2.getY(), -2);
    }

    @Test
    public void testDivByPositiveWithoutRemainder() {
        Vector2 v1 = new Vector2(2, 4);
        Vector2 v2 = v1.div(2);
        assertEquals(v2.getX(), 1);
        assertEquals(v2.getY(), 2);
    }

    @Test
    public void testDivByPositiveWithRemainder() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = v1.div(3);
        assertEquals(v2.getX(), 0);
        assertEquals(v2.getY(), 0);
    }

    @Test
    public void testNegZeroVector() {
        Vector2 v1 = new Vector2(0, 0);
        Vector2 v2 = v1.neg();
        assertEquals(v2.getX(), 0);
        assertEquals(v2.getY(), 0);
    }

    @Test
    public void testNegPositiveVector() {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = v1.neg();
        assertEquals(v2.getX(), -1);
        assertEquals(v2.getY(), -2);
    }

    @Test
    public void testNegNegativeVector() {
        Vector2 v1 = new Vector2(-1, -2);
        Vector2 v2 = v1.neg();
        assertEquals(v2.getX(), 1);
        assertEquals(v2.getY(), 2);
    }

    @Test
    public void testNegOnlyOneNegativeCoordinate() {
        Vector2 v1 = new Vector2(-1, 2);
        Vector2 v2 = v1.neg();
        assertEquals(v2.getX(), 1);
        assertEquals(v2.getY(), -2);

        Vector2 v3 = new Vector2(1, -2);
        Vector2 v4 = v3.neg();
        assertEquals(v4.getX(), -1);
        assertEquals(v4.getY(), 2);
    }

    @Test
    public void testNormZeroVector() {
        Vector2 v1 = new Vector2(0, 0);
        assertEquals(v1.norm(), 0, 0.001);
    }

    @Test
    public void testNormPositiveVector() {
        Vector2 v1 = new Vector2(1, 2);
        assertEquals(v1.norm(), 2.236, 0.001);
    }

    @Test
    public void testNormNegativeVector() {
        Vector2 v1 = new Vector2(-1, -2);
        assertEquals(v1.norm(), 2.236, 0.001);
    }

    @Test
    public void testNormOnlyOneNegativeCoordinate() {
        Vector2 v1 = new Vector2(-1, 2);
        assertEquals(v1.norm(), 2.236, 0.001);

        Vector2 v2 = new Vector2(1, -2);
        assertEquals(v2.norm(), 2.236, 0.001);
    }

}
