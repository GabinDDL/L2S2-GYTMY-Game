package com.gytmy.sound.whisper;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class TestWhisper {

    private static final String AUDIO_FULL_PATH = "src/test/resources/audio/yesmymaster.wav";
    private static final String AUDIO_FILE_NAME = "yesmymaster";
    private static final String JSON_DIRECTORY_PATH = "src/test/resources/json";

    @Ignore
    @Test
    public void testWhisper() {
        Whisper whisper = new Whisper(Whisper.Model.TINY_EN);

        CompletableFuture<String> futureCommand = whisper.ask(AUDIO_FULL_PATH, AUDIO_FILE_NAME, JSON_DIRECTORY_PATH);
        futureCommand.thenAccept(recognizedCommand -> {
            assertTrue(Files.exists(Paths.get(JSON_DIRECTORY_PATH, AUDIO_FILE_NAME + ".json")));
            new File(JSON_DIRECTORY_PATH + AUDIO_FILE_NAME + ".json").delete();
        });
    }
}
