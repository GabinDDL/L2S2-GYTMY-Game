package com.gytmy.sound.whisper;

import org.junit.Test;

public class TestWhisper {

    private static final String audioDirectoryPath = "src/test/resources/audio";
    private static final String jsonDirectoryPath = "src/test/resources/json";
    private static final String audioFileName = "yesmymaster";

    @Test
    public void testWhisper() {
        Whisper whisper = new Whisper(true, Whisper.Model.TINY_EN);
        whisper.run(audioDirectoryPath, audioFileName, jsonDirectoryPath);
    }
}
