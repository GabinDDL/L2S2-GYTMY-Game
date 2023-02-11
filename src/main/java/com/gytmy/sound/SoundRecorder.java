package com.gytmy.sound;

import javax.sound.sampled.*;
import java.io.*;
 
/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class SoundRecorder {
    private static final long RECORD_DURATION = 6; // In seconds
 
    private String audioFilePath = "src/VoiceSample/RecordedAudio.wav";
    private File wavFile = new File(audioFilePath);
 
    // We want the format of the file to be WAV
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    private TargetDataLine line;
 
    /**
     * Defines an audio format
     * We want the format to be 16kHz, 8-bit and mono
     */
    AudioFormat getAudioFormat() {
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
     * Captures the sound and record into a WAV file
     */
    private void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing
 
            captureAndRecord(line);
 
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void captureAndRecord(TargetDataLine targetLine) throws IOException {
    
        AudioInputStream inputStream = capture(targetLine);
        record(inputStream);
    }

    /**
     * The act of capture a sound is the process of transform a sound canal into
     * readable data, which is a audio input stream.
     * 
     * @param targetLine Audio Input Stream
     * @return Audio input stream that reads its data from targetLine
     */
    private AudioInputStream capture(TargetDataLine targetLine) {

        System.out.println("Start capturing...");
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
        AudioSystem.write(inputStream, fileType, wavFile);
    }
 
    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish() {
        // line.stop();
        // line.close();
        System.out.println("Finished");
    }
 
    /**
     * Entry to run the program
     */
    public static void main(String[] args) {
        final SoundRecorder recorder = new SoundRecorder();
 
        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_DURATION * 1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });
 
        stopper.start();
 
        // start recording
        recorder.start();
    }
}