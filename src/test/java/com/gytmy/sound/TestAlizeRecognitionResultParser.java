package com.gytmy.sound;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.gytmy.sound.AlizeRecognitionResultParser.AlizeRecognitionResult;
import com.gytmy.sound.AlizeRecognitionResultParser.IncorrectFileFormatException;

public class TestAlizeRecognitionResultParser {

    @Test
    public void testNormalFile() {
        assertResult("src/test/java/com/gytmy/sound/AlizeParserTestNormal.txt",
                new AlizeRecognitionResult("GABIN", "UP", -112.691));
    }

    @Test
    public void testEmptyFile() {
        assertResult("src/test/java/com/gytmy/sound/AlizeParserTestEmpty.txt",
                AlizeRecognitionResult.DEFAULT_ALIZE_RESULT);
    }

    @Test
    public void testWithPositiveValues() {
        assertResult("src/test/java/com/gytmy/sound/AlizeParserTestPositive.txt",
                new AlizeRecognitionResult("MATHUSAN", "LEFT", 113.003));
    }

    @Test
    public void testWithLongNames() {
        assertResult("src/test/java/com/gytmy/sound/AlizeParserTestLongNames.txt",
                new AlizeRecognitionResult("GABIN_TEST_DOUBLE", "UP", 112.691));
    }

    @Test
    public void testWithInteger() {
        assertResult("src/test/java/com/gytmy/sound/AlizeParserTestInteger.txt",
                new AlizeRecognitionResult("GABIN", "DOWN", 113));
    }

    private void assertResult(String path, AlizeRecognitionResult alizeResult) {
        File inputFile = new File(path);
        try {
            assertEquals(AlizeRecognitionResultParser.parseFile(inputFile), alizeResult);
        } catch (IncorrectFileFormatException e) {
            assertTrue(false);
        }

    }

}
