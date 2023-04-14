package com.gytmy.sound;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.gytmy.sound.AlizeParser.AlizeResult;

public class TestAlizeParser {

    @Test
    public void testNormalFile() {
        File inputFile = new File("src/test/java/com/gytmy/sound/AlizeParserTestNormal.txt");
        assertEquals(AlizeParser.parseFile(inputFile), new AlizeResult("GABIN", "UP", -112.691));
    }

    @Test
    public void testEmptyFile() {
        File inputFile = new File("src/test/java/com/gytmy/sound/AlizeParserTestEmpty.txt");
        assertEquals(AlizeParser.parseFile(inputFile), AlizeResult.DEFAULT_ALIZE_RESULT);
    }

    @Test
    public void testWithPositiveValues() {
        File inputFile = new File("src/test/java/com/gytmy/sound/AlizeParserTestPositive.txt");
        assertEquals(AlizeParser.parseFile(inputFile), new AlizeResult("MATHUSAN", "LEFT", 113.003));
    }

    @Test
    public void testWithLongNames() {
        File inputFile = new File("src/test/java/com/gytmy/sound/AlizeParserTestLongNames.txt");
        assertEquals(AlizeParser.parseFile(inputFile), new AlizeResult("GABIN_TEST_DOUBLE", "UP", 112.691));
    }

}
