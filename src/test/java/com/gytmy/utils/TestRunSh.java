package com.gytmy.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestRunSh {

    public static final String TEST_SH_PATH = "src/main/java/com/gytmy/utils/TestRunSh.sh";

    @Test
    public void testRun() {
        String[] args1 = { "exit0" };
        assertEquals(0, RunSH.run(TEST_SH_PATH, args1));

        String[] args2 = { "exit1" };
        assertEquals(1, RunSH.run(TEST_SH_PATH, args2));

        String[] args3 = { "exit", "exit", "exit" };
        assertEquals(3, RunSH.run(TEST_SH_PATH, args3));

        String[] args4 = { "exit", "exit2", "exit" };
        assertEquals(2, RunSH.run(TEST_SH_PATH, args4));
    }
}