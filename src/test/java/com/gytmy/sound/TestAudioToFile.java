package com.gytmy.sound;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestAudioToFile {

    @Test
    public void testRecordInvalidUser() {

        TestingUtils.assertArgumentExceptionMessage(
                () -> AudioToFile.record(null, null),
                "Invalid null user");
    }

    @Test
    public void testRecordInvalidNullWord() {
        User user = new User();

        TestingUtils.assertArgumentExceptionMessage(
                () -> AudioToFile.record(user, null),
                "Invalid word recorded");

        AudioFileManager.removeUser(user);
    }

    @Test
    public void testRecordInvalidUnknownWord() {
        User user = new User();

        TestingUtils.assertArgumentExceptionMessage(
                () -> AudioToFile.record(user, "INSULTE"),
                "Invalid word recorded");

        AudioFileManager.removeUser(user);
    }
}
