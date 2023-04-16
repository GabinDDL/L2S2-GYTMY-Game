package test.java.com.gytmy.sound.whisper;

import org.junit.Assert;
import org.junit.Test;

import com.gytmy.sound.whisper.Whisper;

public class TestWhisper {

    public static final String audioDirectoryPath = "test/resources/audio";
    public static final String jsonDirectoryPath = "test/resources/json";
    public static final String audioFileName = "yesmymaster";

    @Test
    public void testWhisper() {
        Whisper whisper = new Whisper(true, Whisper.Model.TINY_EN);
        String text = whisper.run(audioDirectoryPath, audioFileName, jsonDirectoryPath);
    }
}
