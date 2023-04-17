package com.gytmy.sound;

import java.io.File;

import com.gytmy.utils.FileInformationFinder;
import com.gytmy.utils.WordsToRecord;

public class AudioToFile {

    private static AudioRecorder audioRecorder;
    private static String currentRecordingFile;

    // 10 KB which we consider to be the minimum size to determine if the audio is
    // not empty
    private static int minimumAudioFileSize = 10_000;

    private AudioToFile() {
    }

    /**
     * Starts recording an audio in which the user says the recorded word
     */
    public static void record(User user, String recordedWord, int durationInSeconds) {

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
        currentRecordingFile = path;
        audioRecorder = AudioRecorder.getInstance();
        audioRecorder.start(path, durationInSeconds);

        user.setUpToDate(false);
        YamlReader.write(user.yamlConfigPath(), user);
    }

    /**
     * Stops recording the audio. If the file's size is too small, it is deleted.
     * 
     * @throws FileTooSmallException if the file is too small
     */
    public static void stop() throws FileTooSmallException {
        audioRecorder.finish();

        File file = new File(currentRecordingFile);

        if (FileInformationFinder.getFileSizeBytes(file) < minimumAudioFileSize) {
            AudioFileManager.deleteRecording(currentRecordingFile);
            String temp = currentRecordingFile;
            currentRecordingFile = "";
            throw new FileTooSmallException(temp);
        }

        currentRecordingFile = "";
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

    public static class FileTooSmallException extends Exception {
        public FileTooSmallException(String file) {
            super("The file " + file + " is too small");
        }
    }
}