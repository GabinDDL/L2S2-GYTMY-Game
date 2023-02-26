package com.gytmy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TestingUtils {

    private TestingUtils() {
    }

    /**
     * Asserts that the given {@link Runnable} throws an
     * {@link IllegalArgumentException} with the given message.
     * 
     * @param runner
     * @param message
     */
    public static void assertArgumentExceptionMessage(Runnable runner, String message) {
        Exception exceptionZero = assertThrows(IllegalArgumentException.class, runner::run);
        assertEquals(message, exceptionZero.getMessage());
    }

}
