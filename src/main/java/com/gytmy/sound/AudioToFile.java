package com.gytmy.sound;

import java.io.File;
import java.util.List;

import com.gytmy.utils.WordsToRecord;

public class AudioToFile {

    private AudioToFile() {
    }

    /**
     * Starts recording an audio in which the user says the word recorded
     */
    public static void record(User user, String recordedWord) {

        assertIsValidUser(user);

        AudioFileManager.addUser(user);

        assertIsValidWordRecorded(recordedWord);
        assertIsValidUserFolder(user, recordedWord);

        int numberOfRecordings = AudioFileManager.numberOfRecordings(user.getFirstName(), recordedWord) + 1;

        String path = user.audioFilesPath() + recordedWord + "/" + recordedWord + numberOfRecordings + ".wav";

        new AudioRecorder(path).start();
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
     * Asserts that the word recorded is a word we want to record
     * 
     * @param recordedWord
     */
    private static void assertIsValidWordRecorded(String recordedWord) {
        if (recordedWord == null || recordedWord.isEmpty() || recordedWord.isBlank()
                || !WordsToRecord.exists(recordedWord)) {
            throw new IllegalArgumentException("Invalid word recorded");
        }
    }

    /**
     * Asserts that the user folder contains the word recorded folder
     */
    private static void assertIsValidUserFolder(User user, String recordedWord) {

        File userDirectory = new File(user.audioFilesPath());
        List<File> userFiles = AudioFileManager.getFilesVerifyingPredicate(userDirectory, File::isDirectory);

        if (!userFiles.contains(new File(user.audioFilesPath() + recordedWord))) {
            new File(user.audioFilesPath() + recordedWord).mkdir();
        }
    }
}
