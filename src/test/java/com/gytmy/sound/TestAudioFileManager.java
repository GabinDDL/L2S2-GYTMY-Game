package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.gytmy.TestingUtils;

public class TestAudioFileManager {

    @Test
    public void testGenerateAudioFolderStructure() {
        AudioFileManager.generateAudioFolderStructure();
        assertTrue(AudioFileManager.audioFilesFolderExists());
    }

    @Test
    public void testNumberOfFilesVerifyingPredicate() {
        File directory = new File("src/resources/audioFiles");
        assertTrue(AudioFileManager.numberOfFilesVerifyingPredicate(directory,
                file -> file.getName().length() == 0) == 0);
    }

    @Test
    public void testGetFilesVerifyingPredicate() throws IOException {
        File directory = new File("src/resources/audioFiles");
        for (int i = 0; i < 10; i++) {
            new File(directory, "test" + i).createNewFile();
        }
        ArrayList<File> files = AudioFileManager.getFilesVerifyingPredicate(directory,
                file -> file.getName().startsWith("test"));

        assertTrue(files.size() == 10);
        for (int i = 0; i < 10; i++) {
            File tempFile = new File(directory, "test" + i);
            assertTrue(files.contains(tempFile));
            tempFile.delete();
        }
    }

    @Test
    public void testGetUsersVerifyingPredicate() {

        for (int i = 0; i < 10; i++) {
            User temporaryUser = new User();
            temporaryUser.setFirstName(User.DEFAULT_FIRST_NAME + i);
            AudioFileManager.addUser(temporaryUser);
        }
        ArrayList<User> users = AudioFileManager.getUsersVerifyingPredicate(
                file -> file.getName().startsWith(User.DEFAULT_LAST_NAME));
        assertTrue(users.size() == 0);

        users = AudioFileManager.getUsersVerifyingPredicate(
                file -> file.getName().startsWith(User.DEFAULT_FIRST_NAME));

        System.out.println("users.size() = " + users.size());
        assertTrue(users.size() == 10);
        for (int i = 0; i < 10; i++) {
            User temporaryUser = new User();
            temporaryUser.setFirstName(User.DEFAULT_FIRST_NAME + i);

            assertTrue(users.contains(temporaryUser));
            AudioFileManager.removeUser(temporaryUser);
        }
    }

    @Test
    public void testTotalNumberOfAudioFiles() throws IOException {

        User user = new User();
        AudioFileManager.addUser(user);

        int totalAudioFiles = AudioFileManager.totalNumberOfAudioFiles();

        File up = new File(user.userAudioFilePath(), "HAUT");
        File down = new File(user.userAudioFilePath(), "BAS");
        up.mkdir();
        down.mkdir();

        new File(up, "test1.wav").createNewFile();
        new File(up, "test2.wav").createNewFile();
        new File(down, "test3.wav").createNewFile();
        new File(down, "test4").createNewFile();

        assertEquals(totalAudioFiles + 3, AudioFileManager.totalNumberOfAudioFiles());

        AudioFileManager.removeUser(user);
    }

    @Test
    public void testAddingUser() {

        User user = new User();

        TestingUtils.assertArgumentExceptionMessage(
                () -> addTwiceUser(user), "User already exists");

        assertTrue(new File(user.userAudioFilePath()).exists());

        AudioFileManager.removeUser(user);
        assertFalse(new File(user.userAudioFilePath()).exists());
    }

    private void addTwiceUser(User user) {

        AudioFileManager.addUser(user);
        AudioFileManager.addUser(user);
    }
}
