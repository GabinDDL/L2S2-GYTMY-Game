package com.gytmy.sound;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.gytmy.sound.AlizeParser.AlizeResult;

public class TestAlizeParser {

    @Test
    public void testNormalFile() {
        assertResult("src/test/java/com/gytmy/sound/AlizeParserTestNormal.txt",
                new AlizeResult("GABIN", "UP", -112.691));
    }

    @Test
    public void testEmptyFile() {
        assertResult("src/test/java/com/gytmy/sound/AlizeParserTestEmpty.txt",
                AlizeResult.DEFAULT_ALIZE_RESULT);
    }

    @Test
    public void testWithPositiveValues() {
        assertResult("src/test/java/com/gytmy/sound/AlizeParserTestPositive.txt",
                new AlizeResult("MATHUSAN", "LEFT", 113.003));
    }

    @Test
    public void testWithLongNames() {
        assertResult("src/test/java/com/gytmy/sound/AlizeParserTestLongNames.txt",
                new AlizeResult("GABIN_TEST_DOUBLE", "UP", 112.691));
    }

    @Test
    public void testWithInteger() {
        assertResult("src/test/java/com/gytmy/sound/AlizeParserTestInteger.txt",
                new AlizeResult("GABIN", "DOWN", 113));
    }

    private void assertResult(String path, AlizeResult alizeResult) {
        File inputFile = new File(path);
        assertEquals(AlizeParser.parseFile(inputFile), alizeResult);

    }

}
