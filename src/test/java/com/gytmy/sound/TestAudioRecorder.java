package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

public class TestAudioRecorder {

    @Ignore // As the CI server does not have a microphone
    @Test
    public void testAudioRecorderConstructor() {

        String path = "./AUDIO_TEST.wav";
        File audioFile = new File(path);
        audioFile.deleteOnExit();

        AudioRecorder audioRec = AudioRecorder.getInstance();
        audioRec.start(path);
        audioRec.finish();

        assertTrue(audioFile.exists());
    }
}
