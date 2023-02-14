package com.gytmy.sound;

import javax.sound.sampled.*;
import java.io.*;

/**
 * SoundRecorder is a class that records sound from a microphone and saves it
 * into a WAV file.
 * 
 * @author Structure :
 *         https://www.codejava.net/coding/capture-and-record-sound-into-wav-file-with-java-sound-api
 */
public class SoundRecorder {

    // We want the format of our files to be WAV
    private static final AudioFileFormat.Type FILE_TYPE = AudioFileFormat.Type.WAVE;
    private static final long MAX_RECORD_DURATION = 11000; // In milliseconds (11 secs)
    private static Thread stopper;

    private File wavFile; // The file that will store the recorded sound

    // A TargetDataLine represents a mono or multi-channel audio feed
    // from which audio data can be read.
    private TargetDataLine channel;

    public SoundRecorder(String audioFilePath) {

        this.wavFile = new File(audioFilePath);

        // Make sure the file exists
        if (!wavFile.exists()) {
            try {
                wavFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error: File cannot be created");
            }
        }
    }

    /**
     * Defines an audio format
     * We want the format to be 16kHz, 8-bit and mono
     */
    private AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    /**
     * Opens the channel and starts recording
     */
    public void start() {

        initiateStopper();

        try {
            openChannel();
            stopper.start();
            captureAndRecord(channel);

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
            System.out.println("Error: Line Unavailable");

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Error: File was not found");
        }
    }

    /**
     * Creates a new thread that waits for a specified delay before stopping record
     * 
     * @return Thread object
     */
    private void initiateStopper() {
        stopper = new Thread(() -> {
            try {
                Thread.sleep(MAX_RECORD_DURATION);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                System.out.println("Error: Recording was interrupted");
            }
            closeChannel();
        });
    }

    /**
     * Open the channel to capture the sound
     * 
     * @throws LineUnavailableException If the channel is not available
     */
    private void openChannel() throws LineUnavailableException {
        AudioFormat format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        // checks if system supports the data line
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line not supported");
            System.exit(0);
        }

        channel = (TargetDataLine) AudioSystem.getLine(info);
        channel.open(format);
        channel.start(); // start capturing
    }

    /**
     * Capture the sound and record it into a WAV file
     * 
     * @param targetLine
     * @throws IOException
     */
    private void captureAndRecord(TargetDataLine targetLine) throws IOException {

        AudioInputStream inputStream = capture(targetLine);
        record(inputStream);
    }

    /**
     * The act of capture a sound is the process of transform a sound channel into
     * readable data, which is a audio input stream.
     * 
     * @param targetLine Audio Input Stream
     * @return Audio input stream that reads its data from targetLine
     */
    private AudioInputStream capture(TargetDataLine targetLine) {

        System.out.println("Canal is open.");
        return new AudioInputStream(targetLine);
    }

    /**
     * Read the audio input stream and write it into a file
     * 
     * @param inputStream Audio input stream
     * @throws IOException If the file is not found
     */
    private void record(AudioInputStream inputStream) throws IOException {

        System.out.println("Start recording...");
        AudioSystem.write(inputStream, FILE_TYPE, wavFile);
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    private void closeChannel() {
        channel.stop();
        channel.close();
        System.out.println("Finished");
    }
}