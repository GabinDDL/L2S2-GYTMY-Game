package com.gytmy.sound;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestAudioToFile {

    private static final int RECORD_DURATION = 1;

    @Test
    public void testRecordInvalidUser() {

        TestingUtils.assertArgumentExceptionMessage(
                () -> AudioToFile.record(null, null, RECORD_DURATION),
                "Invalid null user");
    }

    @Test
    public void testRecordInvalidNullWord() {
        User user = new User();

        TestingUtils.assertArgumentExceptionMessage(
                () -> AudioToFile.record(user, null, RECORD_DURATION),
                "Invalid recorded word");

        AudioFileManager.removeUser(user);
    }

    @Test
    public void testRecordInvalidUnknownWord() {
        User user = new User();

        TestingUtils.assertArgumentExceptionMessage(
                () -> AudioToFile.record(user, "INSULTE", RECORD_DURATION),
                "Invalid recorded word");

        AudioFileManager.removeUser(user);
    }
}
