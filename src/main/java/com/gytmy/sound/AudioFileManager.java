package com.gytmy.sound;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.gytmy.utils.WordsToRecord;

/**
 * AudioFileManager is a class that manages the audio files
 * regarding the user
 */
public class AudioFileManager {

    private static final String SRC_DIR_PATH = "src/resources/audioFiles/";
    private static final File SRC_DIRECTORY = new File(SRC_DIR_PATH);

    private AudioFileManager() {
    }

    /**
     * Add an user to the directory if it doesn't already exist
     */
    public static void addUser(User userToAdd) throws IllegalArgumentException {

        generateAudioDirectoryStructure();

        if (doesUserAlreadyExist(userToAdd)) {
            throw new IllegalArgumentException("User already exists");
        }

        createUserFiles(userToAdd);
    }

    /**
     * If the folder "src/resources/audioFiles" does not exist,
     * create it and its arborescence
     */
    public static void generateAudioDirectoryStructure() {

        if (!doesResourcesDirectoryExist()) {
            new File("src/resources").mkdir();
        }

        if (!doesAudioFilesDirectoryExist()) {
            SRC_DIRECTORY.mkdir();
        }
    }

    public static boolean doesResourcesDirectoryExist() {
        return new File("src/resources").exists();
    }

    public static boolean doesAudioFilesDirectoryExist() {
        return SRC_DIRECTORY.exists();
    }

    public static boolean doesUserAlreadyExist(User user) {
        return new File(user.audioFilesPath()).exists();
    }

    /**
     * Create the user directory, all the subdirectories and files
     */
    public static void createUserFiles(User user) {

        File userDirectory = new File(user.audioFilesPath());
        userDirectory.mkdir();

        writeYamlConfig(user);
    }

    /**
     * Translate the user object to a yaml file
     */
    public static void writeYamlConfig(User user) {
        try {
            YamlReader.write(user.yamlConfigPath(), user);
        } catch (IllegalArgumentException e) {
            System.out.println("Error while creating the `.yaml` file for the user " + user + " : " + e.getMessage());
        }
    }

    /**
     * Remove a user from the directory if it exists
     */
    public static void removeUser(User userToRemove) {
        List<User> usersWithSameFirstName = getUsersVerifyingPredicate(
                file -> file.getName().startsWith(userToRemove.getFirstName()));

        for (User user : usersWithSameFirstName) {
            if (user.equals(userToRemove)) {
                deleteFiles(user);
            }
        }
    }

    public static List<User> getUsers() {
        return getUsersVerifyingPredicate(File::isDirectory);
    }

    public static List<User> getUsersVerifyingPredicate(Predicate<File> predicate) {
        List<User> users = new ArrayList<>();

        generateAudioDirectoryStructure();

        for (File file : SRC_DIRECTORY.listFiles()) {
            if (predicate.test(file)) {
                tryToAddUser(users, file);
            }
        }

        return users;
    }

    private static boolean tryToAddUser(List<User> users, File file) {
        try {
            users.add(YamlReader.read(SRC_DIR_PATH + file.getName() + "/config.yaml"));
            return true;

        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
            return false;
        }
    }

    /**
     * Delete the user directory and all the files in it
     */
    private static void deleteFiles(User user) {
        File userDirectory = new File(user.audioFilesPath());
        clearDirectory(userDirectory);
        userDirectory.delete();
    }

    private static void clearDirectory(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory())
                clearDirectory(file);
            file.delete();
        }
    }

    public static int totalNumberOfAudioFiles() {
        int totalNumberOfAudioFiles = 0;

        for (User user : getUsers()) {
            totalNumberOfAudioFiles += totalNumberOfAudioFilesForUser(user.getFirstName());
        }

        return totalNumberOfAudioFiles;
    }

    /**
     * Get the number of audio files for a user for all the words that can be
     * recorded
     */
    public static int totalNumberOfAudioFilesForUser(String userName) {
        int numberOfAudioFiles = 0;

        for (String word : WordsToRecord.getWordsToRecord()) {
            numberOfAudioFiles += numberOfRecordings(userName, word);
        }

        return numberOfAudioFiles;
    }

    /**
     * Get the number of audio files about a specific word
     */
    public static int numberOfRecordings(String userName, String word) {

        if (!WordsToRecord.exists(word)) {
            throw new IllegalArgumentException("Word does not exist");
        }

        File userDirectory = new File(SRC_DIR_PATH + "/" + userName.toUpperCase() + "/" + word);

        if (!userDirectory.exists()) {
            return 0;
        }

        return numberOfFilesVerifyingPredicate(userDirectory, AudioFileManager::isAudioFile);
    }

    /**
     * An audio file, in our project, is a .wav file
     */
    private static boolean isAudioFile(File file) {
        return file.isFile() && file.getName().endsWith(".wav");
    }

    public static int numberOfUsers() {
        return numberOfFilesVerifyingPredicate(SRC_DIRECTORY, File::isDirectory);
    }

    public static int numberOfFilesVerifyingPredicate(File directory, Predicate<File> predicate) {
        return getFilesVerifyingPredicate(directory, predicate).size();
    }

    public static List<File> getFilesVerifyingPredicate(File directory, Predicate<File> predicate) {
        List<File> files = new ArrayList<>();

        if (!directory.exists()) {
            return files;
        }

        for (File file : directory.listFiles()) {
            if (predicate.test(file)) {
                files.add(file);
            }
        }

        return files;
    }
}
