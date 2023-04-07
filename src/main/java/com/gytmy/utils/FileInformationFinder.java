package com.gytmy.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class FileInformationFinder {

    private FileInformationFinder() {
    }

    /**
     * @return the total length of the audio files in seconds
     * @see <a href=
     *      "https://stackoverflow.com/questions/3009908/how-do-i-get-a-sound-files-total-time-in-java">
     *      How do I get a sound file's total time in Java? </a>
     */
    public static float getAudioFilesLength(List<File> audioFiles) {

        if (audioFiles == null) {
            return 0;
        }

        float totalDuration = 0;

        for (File file : audioFiles) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = audioInputStream.getFormat();
                long audioFileLength = file.length();
                int frameSize = format.getFrameSize();
                float frameRate = format.getFrameRate();
                totalDuration += (audioFileLength / (frameSize * frameRate));
            } catch (UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        }
        return totalDuration;
    }

}
