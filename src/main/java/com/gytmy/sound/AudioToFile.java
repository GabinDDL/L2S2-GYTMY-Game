package com.gytmy.sound;

import java.io.File;
import java.util.ArrayList;

import com.gytmy.utils.WordsToRecord;

public class AudioToFile {

    private AudioToFile() {
    }

    /**
     * Starts recording an audio in which the user says the word recorded
     */
    public static void record(User user, String wordRecorded) {

        assertIsValidUser(user);

        AudioFileManager.addUser(user);

        assertIsValidWordRecorded(wordRecorded);
        assertIsValidUserFolder(user, wordRecorded);

        int numberOfRecordings = AudioFileManager.numberOfRecordings(user.getFirstName(), wordRecorded) + 1;

        String path = user.userAudioFilePath() + wordRecorded + "/" + wordRecorded + numberOfRecordings + ".wav";

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
     * @param wordRecorded
     */
    private static void assertIsValidWordRecorded(String wordRecorded) {
        if (wordRecorded == null || wordRecorded.isEmpty() || wordRecorded.isBlank()
                || !WordsToRecord.exists(wordRecorded)) {
            throw new IllegalArgumentException("Invalid word recorded");
        }
    }

    /**
     * Asserts that the user folder contains the word recorded folder
     */
    private static void assertIsValidUserFolder(User user, String wordRecorded) {

        File userDirectory = new File(user.userAudioFilePath());
        ArrayList<File> userFiles = AudioFileManager.getFilesVerifyingPredicate(userDirectory, File::isDirectory);

        if (!userFiles.contains(new File(user.userAudioFilePath() + wordRecorded))) {
            new File(user.userAudioFilePath() + wordRecorded).mkdir();
        }
    }
}
