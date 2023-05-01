package com.gytmy.sound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gytmy.utils.WordsToRecord;

public class TestModelManager {

    private static final int NUMBER_OF_TEST_USERS = 10;
    private static final String WORD_TO_TEST = WordsToRecord.getWordsToRecord().get(0);

    @Test
    public void testTryToCreateModelDirectoryOfWord() {
        List<User> users = getTestUsers();

        for (User user : users) {
            assertTrue(ModelManager.tryToCreateModelDirectoriesOfWord(user, WORD_TO_TEST));
            assertFalse(ModelManager.tryToCreateModelDirectoriesOfWord(user, WORD_TO_TEST));

            assertTrue(new File(user.modelPath() + WORD_TO_TEST + "/").exists());
            assertTrue(new File(user.modelPath() + WORD_TO_TEST + ModelManager.LST_PATH).exists());
        }

        removeTestUsers(users);
    }

    @Test
    public void testTryToCreateLstFile() {
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_TEST_USERS; i++) {
            User temporaryUser = new User();
            temporaryUser.setFirstName(User.DEFAULT_FIRST_NAME + i);
            AudioFileManager.addUser(temporaryUser);

            users.add(temporaryUser);
        }

        for (User user : users) {
            assertTrue(ModelManager.tryToCreateModelDirectoriesOfWord(user, WORD_TO_TEST));
            assertTrue(ModelManager.tryToInitLstFilesOfUserWord(user, WORD_TO_TEST));
            assertTrue(new File(user.modelPath() + WORD_TO_TEST + ModelManager.LST_PATH).exists());
        }

        removeTestUsers(users);
    }

    @Test
    public void testTryToCreateNdxFile() {
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_TEST_USERS; i++) {
            User temporaryUser = new User();
            temporaryUser.setFirstName(User.DEFAULT_FIRST_NAME + i);
            AudioFileManager.addUser(temporaryUser);

            users.add(temporaryUser);
        }

        for (User user : users) {
            assertTrue(ModelManager.tryToCreateModelDirectoriesOfWord(user, WORD_TO_TEST));
            assertTrue(ModelManager.tryToInitNdxFilesOfUserWord(user, WORD_TO_TEST));
            assertTrue(new File(user.modelPath() + WORD_TO_TEST + ModelManager.NDX_PATH).exists());
        }

        removeTestUsers(users);
    }

    @Test
    public void testTryToWriteAndResetLstFile() {

        List<User> users = getTestUsers();

        for (User user : users) {
            File file = new File(user.modelPath() + WORD_TO_TEST + ModelManager.LIST_LST_PATH);
            ModelManager.tryToCreateModelDirectoriesOfWord(user, WORD_TO_TEST);
            ModelManager.tryToInitLstFilesOfUserWord(user, WORD_TO_TEST);

            try (FileWriter writer = new FileWriter(user.modelPath() + WORD_TO_TEST + ModelManager.LIST_LST_PATH, true);
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);) {

                writer.append(WORD_TO_TEST + "\n");
                writer.append(WORD_TO_TEST + "\n");

                String line;
                while ((line = br.readLine()) != null) {
                    assertEquals(line, WORD_TO_TEST);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);) {
                assertTrue(ModelManager.tryToInitLstFilesOfUserWord(user, WORD_TO_TEST));

                assertTrue((br.readLine()) == null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        removeTestUsers(users);
    }

    @Test
    public void testTryToWriteAndResetNdxFile() {

        List<User> users = getTestUsers();

        for (User user : users) {
            File file = new File(user.modelPath() + WORD_TO_TEST + ModelManager.LIST_NDX_PATH);
            ModelManager.tryToCreateModelDirectoriesOfWord(user, WORD_TO_TEST);
            ModelManager.tryToInitLstFilesOfUserWord(user, WORD_TO_TEST);

            try (FileWriter writer = new FileWriter(user.modelPath() + WORD_TO_TEST + ModelManager.LIST_NDX_PATH, true);
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);) {

                writer.append(user.getFirstName() + "_" + WORD_TO_TEST);
                writer.append(" " + WORD_TO_TEST);
                writer.append(" " + WORD_TO_TEST);

                String line;
                while ((line = br.readLine()) != null) {
                    assertEquals(line,
                            user.getFirstName() + "_" + WORD_TO_TEST + " " + WORD_TO_TEST + " " + WORD_TO_TEST);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);) {
                assertTrue(ModelManager.tryToInitNdxFilesOfUserWord(user, WORD_TO_TEST));

                assertEquals((br.readLine()), user.getFirstName() + "_" + WORD_TO_TEST);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        removeTestUsers(users);
    }

    @Test
    public void testDoesUserHaveDataOfWord() {
        List<User> users = getTestUsers();

        for (User user : users) {
            assertFalse(ModelManager.doesUserHaveDataOfWord(user, WORD_TO_TEST));
            AudioFileManager.tryToCreateUserWordDirectory(user, WORD_TO_TEST);
            assertTrue(ModelManager.doesUserHaveDataOfWord(user, WORD_TO_TEST));
        }

        removeTestUsers(users);
    }

    private static List<User> getTestUsers() {
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_TEST_USERS; i++) {
            User temporaryUser = new User();
            temporaryUser.setFirstName(User.DEFAULT_FIRST_NAME + i);
            AudioFileManager.addUser(temporaryUser);

            users.add(temporaryUser);
        }

        return users;
    }

    private static void removeTestUsers(List<User> users) {
        for (User userToRemove : users) {
            AudioFileManager.removeUser(userToRemove);
        }
    }
}
