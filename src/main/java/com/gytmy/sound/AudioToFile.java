package com.gytmy.sound;

import java.io.File;
import java.util.List;

import com.gytmy.utils.WordsToRecord;

public class AudioToFile {

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

        if (!assertIsValidUserDirectory(user, recordedWord)) {
            AudioFileManager.createUserRecordedWordFile(user, recordedWord);

        }

        int numberOfRecordings = AudioFileManager.numberOfRecordings(user.getFirstName(), recordedWord) + 1;

        String path = user.audioPath() + recordedWord + "/" + recordedWord + numberOfRecordings + ".wav";

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

    /**
     * Asserts that the user folder contains the recorded word folder
     */
    private static boolean assertIsValidUserDirectory(User user, String recordedWord) {

        File userDirectory = new File(user.audioPath());
        List<File> userFiles = AudioFileManager.getFilesVerifyingPredicate(userDirectory, File::isDirectory);

        if (!userFiles.contains(new File(user.audioPath() + recordedWord))) {
            return false;
        }
        return true;
    }
}
