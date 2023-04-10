package com.gytmy.sound;

import java.io.File;
import java.util.List;

import com.gytmy.utils.FileInformationFinder;
import com.gytmy.utils.WordsToRecord;

public class AudioToFile {

    private static AudioRecorder audioRecorder;
    private static String currentRecordingFile;
    private static int minimumAudioFileSize = 10_225;

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
        assertIsValidUserDirectory(user, recordedWord);

        int numberOfRecordings = AudioFileManager.numberOfRecordings(user.getFirstName(), recordedWord) + 1;

        String path = user.audioFilesPath() + recordedWord + "/" + recordedWord + numberOfRecordings + ".wav";
        currentRecordingFile = path;

        audioRecorder = AudioRecorder.getInstance();
        audioRecorder.start(path);
    }

    /**
     * Stops recording the audio. If the file's size is too small, it is deleted.
     * 
     * @throws FileToSmallException if the file is too small
     */
    public static void stop() throws FileToSmallException {
        audioRecorder.finish();

        File file = new File(currentRecordingFile);

        if (FileInformationFinder.getFileSizeBytes(file) < minimumAudioFileSize) {
            AudioFileManager.deleteRecording(currentRecordingFile);
            String temp = currentRecordingFile;
            currentRecordingFile = "";
            throw new FileToSmallException(temp);
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

    /**
     * Asserts that the user folder contains the recorded word folder
     */
    private static void assertIsValidUserDirectory(User user, String recordedWord) {

        File userDirectory = new File(user.audioFilesPath());
        List<File> userFiles = AudioFileManager.getFilesVerifyingPredicate(userDirectory, File::isDirectory);

        if (!userFiles.contains(new File(user.audioFilesPath() + recordedWord))) {
            new File(user.audioFilesPath() + recordedWord).mkdir();
        }
    }

    public static class FileToSmallException extends Exception {
        public FileToSmallException(String file) {
            super("The file " + file + " is too small");
        }
    }
}