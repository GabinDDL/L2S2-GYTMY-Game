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
     */
    public static float getAudioLength(List<File> audioFiles) {

        if (audioFiles == null) {
            return 0;
        }

        float totalDuration = 0;

        for (File file : audioFiles) {
            totalDuration += getAudioLength(file);
        }
        return totalDuration;
    }

    /**
     * @return the length of an audio file in seconds
     * @see <a href=
     *      "https://stackoverflow.com/questions/3009908/how-do-i-get-a-sound-files-total-time-in-java">
     *      How do I get a sound file's total time in Java? </a>
     */
    public static double getAudioLength(File audioFile) {
        double duration = 0;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioInputStream.getFormat();

            long audioFileLength = audioFile.length();
            int frameSize = format.getFrameSize();
            float frameRate = format.getFrameRate();

            duration = (audioFileLength / (frameSize * frameRate));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        return duration;
    }

    public static double getFileSizeBytes(File file) {
        return file.length();
    }

    public static double getFileSizeKiloBytes(File file) {
        return file.length() / 1024.;
    }

    public static double getFileSizeMegaBytes(File file) {
        return file.length() / (1024. * 1024.);
    }

}
