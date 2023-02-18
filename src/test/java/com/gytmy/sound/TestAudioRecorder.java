package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.Test;

public class TestAudioRecorder {

    @Test
    public void TestAudioRecorderConstructor() {

        String path = "./AUDIO_TEST.wav";

        AudioRecorder audioRec = new AudioRecorder(path);
        audioRec.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        audioRec.finish();

        assertTrue(new File(path).exists());

        new File(path).delete();
    }
}
