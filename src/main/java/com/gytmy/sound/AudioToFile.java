package com.gytmy.sound;

import java.io.File;
import java.util.ArrayList;

import com.gytmy.utils.WordsToRecord;

public class AudioToFile {

    /**
     * Starts recording an audio in which the user says the word recorded
     */
    public static void record(User user, String wordRecorded) {

        try {
            AudioFileManager.addUser(user);
        } catch (IllegalArgumentException e) {
        }

        assertWordRecordedIsValid(wordRecorded);
        assertUserFolderIsValid(user, wordRecorded);

        int numberOfRecordings = AudioFileManager.numberOfRecordings(user.getFirstname(), wordRecorded) + 1;

        String path = user.userAudioFilePath() + wordRecorded + "/" + wordRecorded + numberOfRecordings + ".wav";

        new SoundRecorder(path).start();
    }

    /**
     * Asserts that the word recorded is a word we want to record
     * 
     * @param wordRecorded
     */
    private static void assertWordRecordedIsValid(String wordRecorded) {
        if (!WordsToRecord.exists(wordRecorded)) {
            throw new IllegalArgumentException("Invalid word recorded");
        }
    }

    /**
     * Asserts that the user folder contains the word recorded folder
     */
    private static void assertUserFolderIsValid(User user, String wordRecorded) {

        File userDirectory = new File(user.userAudioFilePath());
        ArrayList<File> userFiles = AudioFileManager.getFilesVerifyingPredicate(userDirectory, File::isDirectory);

        if (!userFiles.contains(new File(user.userAudioFilePath() + wordRecorded))) {
            new File(user.userAudioFilePath() + wordRecorded).mkdir();
        }
    }
}
