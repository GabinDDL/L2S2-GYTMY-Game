package com.gytmy.sound.whisper;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class TestWhisper {

    private static final String audioDirectoryPath = "src/test/resources/audio";
    private static final String jsonDirectoryPath = "src/test/resources/json";
    private static final String audioFileName = "yesmymaster";

    @Test
    public void testWhisper() {
        Whisper whisper = new Whisper(false, Whisper.Model.TINY_EN);
        // Assert that the following code does not throw an exception
        whisper.run(audioDirectoryPath, audioFileName, jsonDirectoryPath);
        assertTrue(Files.exists(Paths.get(jsonDirectoryPath, audioFileName + ".json")));
    }
}
