package com.gytmy.sound;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestAudioToFile {

    @Test
    public void testRecord() {

        TestingUtils.assertArgumentExceptionMessage(
                () -> AudioToFile.record(null, null),
                "Invalid null user");

        User user = new User();

        TestingUtils.assertArgumentExceptionMessage(
                () -> AudioToFile.record(user, null),
                "Invalid word recorded");

        AudioFileManager.removeUser(user);

        TestingUtils.assertArgumentExceptionMessage(
                () -> AudioToFile.record(user, "INSULTE"),
                "Invalid word recorded");

        AudioFileManager.removeUser(user);
    }
}
