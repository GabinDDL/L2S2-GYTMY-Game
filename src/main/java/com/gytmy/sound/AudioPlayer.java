package com.gytmy.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * An utility class for playing back audio files using Java Sound API.
 * 
 * @see https://www.codejava.net/coding/java-audio-player-sample-application-in-swing
 *
 */
public class AudioPlayer implements LineListener {

    /**
     * this flag indicates whether the playback completes or not.
     */
    private boolean playing;

    /**
     * this flag indicates whether the playback is stopped or not.
     */
    private boolean isStopped;

    private Clip audioClip;

    /**
     * Load audio file before playing back
     * 
     * @param audioFilePath
     *                      Path of the audio file.
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public void load(String audioFilePath)
            throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        File audioFile = new File(audioFilePath);

        AudioInputStream audioStream = AudioSystem
                .getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(Clip.class, format);

        audioClip = (Clip) AudioSystem.getLine(info);

        audioClip.addLineListener(this);

        audioClip.open(audioStream);
    }

    public long getClipSecondLength() {
        return audioClip.getMicrosecondLength() / 1_000_000;
    }

    /**
     * Play a given audio file.
     * 
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public void play() throws IOException {

        audioClip.start();

        playing = true;
        isStopped = false;

        while (playing) {
            // wait for the playback completes
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                if (isStopped) {
                    audioClip.stop();
                    break;
                }
            }
        }
        audioClip.close();

    }

    /**
     * Stop playing back.
     */
    public void stop() {
        isStopped = true;
    }

    /**
     * Listens to the audio line events to know when the playback completes.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.STOP) {
            playing = false;
        }
    }

    public Clip getAudioClip() {
        return audioClip;
    }
}