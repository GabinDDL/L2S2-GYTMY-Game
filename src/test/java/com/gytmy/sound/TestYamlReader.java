package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.io.File;

import org.junit.Test;

public class TestYamlReader {

    @Test
    public void testCreateAnUser() throws Exception {

        User userTest = new User("TEST", "TEST", 00000000);
        AudioFileManager.addUser(userTest);

        User user = YamlReader.read(userTest.userYamlConfig());

        assertEquals(userTest, user);

        AudioFileManager.removeUser(userTest);

        assertEquals("[0] TEST TEST", user.toString());
    }
}
