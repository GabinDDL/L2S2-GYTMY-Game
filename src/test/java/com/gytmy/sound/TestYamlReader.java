package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestYamlReader {

    @Test
    public void testReadYAML() {

        User userTest = new User();
        AudioFileManager.addUser(userTest);

        User user = null;
        try {
            user = YamlReader.read(userTest.yamlConfigPath());
        } catch (Exception e) {
        }
        AudioFileManager.removeUser(userTest);

        assertEquals(userTest.toString(), user.toString());
    }

    @Test
    public void testWrite() {
        User user = new User();

        YamlReader.write("./test.yaml", user);

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
                () -> YamlReader.write(null, user),
                "Invalid file path : " + null);

        TestingUtils.assertArgumentExceptionMessage(
                () -> YamlReader.write("", user),
                "Invalid file path : ");

        TestingUtils.assertArgumentExceptionMessage(
                () -> YamlReader.write("  ", user),
                "Invalid file path :   ");
    }

    @Test
    public void testInvalidFileExtension() {
        User user = new User();

        TestingUtils.assertArgumentExceptionMessage(
                () -> YamlReader.write("./test.txt", user),
                "Invalid file extension : ./test.txt");
        TestingUtils.assertArgumentExceptionMessage(
                () -> YamlReader.write("./test", user),
                "Invalid file extension : ./test");
    }
}
