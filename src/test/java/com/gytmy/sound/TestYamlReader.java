package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.Test;

public class TestYamlReader {

    @Test
    public void testInitializationJsonFactory() throws Exception {

        String pathTest = "src/resources/audioFiles/TEST/";
        User userTest = new User("TEST", "TEST", 00000000);

        File testDirectory = new File(pathTest);
        testDirectory.mkdir();

        File configYaml = new File(pathTest + "config.yaml");
        configYaml.createNewFile();

        YamlReader.write(pathTest + "config.yaml", userTest, true);
        User user = YamlReader.read(pathTest + "config.yaml");

        for (File file : testDirectory.listFiles()) {
            file.delete();
        }
        testDirectory.delete();

        assertEquals("[0] TEST TEST", user.toString());
    }
}
