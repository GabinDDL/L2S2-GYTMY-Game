package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestYamlReader {

    @Test
    public void testReadYAML() {

        User userTest = new User("TEST", "TEST", 00000000);
        AudioFileManager.addUser(userTest);

        User user = null;
        try {
            user = YamlReader.read(userTest.userYamlConfig());
        } catch (Exception e) {
        }
        AudioFileManager.removeUser(userTest);

        assertEquals("[0] TEST TEST", user.toString());
    }

    @Test
    public void testWrite() {
        User user = new User();

        try {
            YamlReader.write("./test.yaml", user, false);
        } catch (Exception e) {
        }

        User userTest = null;
        try {
            userTest = YamlReader.read("./test.yaml");
        } catch (Exception e) {
        }
        new File("./test.yaml").delete();

        assertTrue(user.equals(userTest));
    }

    @Test
    public void testInvalidFilePath() {
        User user = new User();

        TestingUtils.assertArgumentExceptionMessage(
                () -> YamlReader.write(null, user, false),
                "Invalid file path : " + null);

        TestingUtils.assertArgumentExceptionMessage(
                () -> YamlReader.write("", user, false),
                "Invalid file path : ");

        TestingUtils.assertArgumentExceptionMessage(
                () -> YamlReader.write("  ", user, false),
                "Invalid file path :   ");
    }

    @Test
    public void testInvalidFileExtension() {
        User user = new User();

        TestingUtils.assertArgumentExceptionMessage(
                () -> YamlReader.write("./test.txt", user, false),
                "Invalid file extension : ./test.txt");
        TestingUtils.assertArgumentExceptionMessage(
                () -> YamlReader.write("./test", user, false),
                "Invalid file extension : ./test");
    }

    @Test
    public void testFileDoesNotExists() {

        TestingUtils.assertArgumentExceptionMessage(
                () -> YamlReader.write("./test.yaml", null, true),
                "File does not exists : ./test.yaml");
    }
}
