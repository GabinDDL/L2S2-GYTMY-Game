package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

public class TestAudioRecorder {

    @Ignore
    @Test
    public void testAudioRecorderConstructor() {

        String path = "./AUDIO_TEST.wav";
        File audioFile = new File(path);
        audioFile.deleteOnExit();

        AudioRecorder audioRec = new AudioRecorder(path);
        audioRec.start();
        audioRec.finish();

        assertTrue(audioFile.exists());
    }
}
