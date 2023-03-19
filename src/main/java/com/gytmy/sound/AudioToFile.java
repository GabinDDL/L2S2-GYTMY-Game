package com.gytmy.sound;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.gytmy.utils.WordsToRecord;

public class AudioToFile {

    private static AudioRecorder audioRecorder;

    private AudioToFile() {
    }

    /**
     * Starts recording an audio in which the user says the recorded word
     */
    public static void record(User user, String recordedWord) {

        assertIsValidUser(user);

        try {
            AudioFileManager.addUser(user);
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals("User already exists")) {
                throw e;
            }
        }

        assertIsValidWordRecorded(recordedWord);

        AudioFileManager.tryToCreateUserWordDirectory(user, recordedWord);

        int numberOfRecordings = AudioFileManager.numberOfRecordings(user.getFirstName(), recordedWord) + 1;

        String path = user.audioPath() + recordedWord + "/" + recordedWord + numberOfRecordings + ".wav";

        audioRecorder = new AudioRecorder(path);
        audioRecorder.start();

        addAudioToLST(user, recordedWord, numberOfRecordings);
    }

    private static void addAudioToLST(User user, String recordedWord, int numberOfRecordings) {
        AudioFileManager.tryToCreateListFile(user, recordedWord);

        try {
            FileWriter writer = new FileWriter(user.modelPath() + recordedWord + "/lst/List.lst", true);
            writer.append(recordedWord + numberOfRecordings);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        audioRecorder.finish();
    }

    /**
     * Asserts that the user is not null
     */
    private static void assertIsValidUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Invalid null user");
        }
    }

    /**
     * Asserts that the recorded word is a word we want to record
     * 
     * @param recordedWord
     */
    private static void assertIsValidWordRecorded(String recordedWord) {
        if (recordedWord == null || recordedWord.isEmpty() || recordedWord.isBlank()
                || !WordsToRecord.exists(recordedWord)) {
            throw new IllegalArgumentException("Invalid recorded word");
        }
    }

    public static void main(String[] args) {
        User user = new User();
        record(user, "HAUT");
    }
}
