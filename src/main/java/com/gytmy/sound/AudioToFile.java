package com.gytmy.sound;

import com.gytmy.utils.WordsToRecord;

public class AudioToFile {

    public static void record(User user, String wordRecorded) {

        try {
            AudioFileManager.addUser(user);
        } catch (IllegalArgumentException e) {
        }

        assertWordRecordedIsValid(wordRecorded);

        int numberOfRecordings = AudioFileManager.numberOfRecordings(user.getFirstname(), wordRecorded) + 1;

        String path = user.userAudioFilePath() + wordRecorded + "/" + wordRecorded + numberOfRecordings + ".wav";

        new SoundRecorder(path).start();
    }

    private static void assertWordRecordedIsValid(String wordRecorded) {
        if (!WordsToRecord.exists(wordRecorded)) {
            throw new IllegalArgumentException("Invalid word recorded");
        }
    }
}
