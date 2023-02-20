package com.gytmy.sound;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Predicate;

import com.gytmy.utils.WordsToRecord;

/**
 * AudioFileManager is a class that manages the audio files
 * regarding the user
 */
public class AudioFileManager {

    private static final String SRC_DIR_PATH = "src/resources/audioFiles/";
    private static final File SRC_DIRECTORY = new File(SRC_DIR_PATH);

    /**
     * Does the folder "src/resources" exists ?
     */
    public static boolean resourcesFolderExists() {

        return new File("src/resources").exists();
    }

    /**
     * Does the folder "src/resources/audioFiles" exists ?
     */
    public static boolean audioFilesFolderExists() {

        return SRC_DIRECTORY.exists();
    }

    /**
     * If the folder "src/resources/audioFiles" does not exists,
     * create it and his arborescence
     */
    public static void generateAudioFolderStructure() {

        if (!resourcesFolderExists()) {
            new File("src/resources").mkdir();
        }

        if (!audioFilesFolderExists()) {
            SRC_DIRECTORY.mkdir();
        }
    }

    /**
     * Get the number of files valid for a predicate
     */
    public static int numberOfFilesVerifyingPredicate(File directory, Predicate<File> predicate) {
        return getFilesVerifyingPredicate(directory, predicate).size();
    }

    /**
     * Get the number of users of the global directory
     */
    public static int numberOfUsers() {
        return numberOfFilesVerifyingPredicate(SRC_DIRECTORY, File::isDirectory);
    }

    /**
     * Get the list of files verifying a predicate passed as parameter
     * 
     * @param directory
     * @param predicate
     * @return
     */
    public static ArrayList<File> getFilesVerifyingPredicate(File directory, Predicate<File> predicate) {
        ArrayList<File> files = new ArrayList<File>();
        for (File file : directory.listFiles()) {
            if (predicate.test(file)) {
                files.add(file);
            }
        }
        return files;
    }

    /**
     * Get the list of users verifying a predicate passed as parameter
     */
    public static ArrayList<User> getUsersVerifyingPredicate(Predicate<File> predicate) {
        ArrayList<User> users = new ArrayList<User>();
        for (File file : SRC_DIRECTORY.listFiles()) {
            if (predicate.test(file)) {
                tryAddingUser(users, file);
            }
        }
        return users;
    }

    /**
     * Get the list of users
     */
    public static ArrayList<User> getUsers() {
        return getUsersVerifyingPredicate(File::isDirectory);
    }

    private static boolean tryAddingUser(ArrayList<User> users, File file) {
        try {
            users.add(YamlReader.read(SRC_DIR_PATH + file.getName() + "/config.yaml"));
            return true;

        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
            return false;
        }
    }

    /**
     * Get the total number of audio files
     */
    public static int totalNumberOfAudioFiles() {
        int totalNumberOfAudioFiles = 0;
        for (User user : getUsers()) {
            totalNumberOfAudioFiles += totalNumberOfAudioFilesForUser(user.getFirstName());
        }
        return totalNumberOfAudioFiles;
    }

    /**
     * Get the number of audio files for a user for all the words
     */
    public static int totalNumberOfAudioFilesForUser(String userName) {
        int numberOfAudioFiles = 0;
        for (String word : WordsToRecord.getWordsToRecord()) {
            numberOfAudioFiles += numberOfRecordings(userName, word);
        }
        return numberOfAudioFiles;
    }

    /**
     * Get the number of audio files for a specific word
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
     * An audio file in our structure is a .wav file
     */
    private static boolean isAudioFile(File file) {
        return file.isFile() && file.getName().endsWith(".wav");
    }

    /**
     * Add an user to the directory if it doesn't already exist
     */
    public static void addUser(User userToAdd) {

        generateAudioFolderStructure();

        try {
            userAlreadyExists(userToAdd);
            createUserFiles(userToAdd);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Throw an exception if the user already exists
     */
    public static void userAlreadyExists(User user) {

        if (new File(user.userAudioFilePath()).exists()) {
            throw new IllegalArgumentException("User already exists");
        }
    }

    /**
     * Create the user directory and all the subdirectories and files
     */
    public static void createUserFiles(User user) {

        File userDirectory = new File(user.userAudioFilePath());
        userDirectory.mkdir();

        writeYamlConfig(user);
    }

    /**
     * Translate the user object to a yaml file
     */
    public static void writeYamlConfig(User user) {
        try {
            YamlReader.write(user.userYamlConfig(), user);
        } catch (IllegalArgumentException e) {
            System.out.println("Error while creating the `.yaml` file for the user " + user + " : " + e.getMessage());
        }
    }

    /**
     * Remove a user from the directory if it exists
     */
    public static void removeUser(User userToRemove) {
        ArrayList<User> usersWithSameFirstName = getUsersVerifyingPredicate(
                (file) -> file.getName().startsWith(userToRemove.getFirstName()));

        for (User user : usersWithSameFirstName) {
            if (user.equals(user)) {
                deleteFile(user);
            }
        }
    }

    /**
     * Delete the user directory and all the files in it
     */
    private static void deleteFile(User user) {
        File userDirectory = new File(user.userAudioFilePath());
        emptyDirectory(userDirectory);
        userDirectory.delete();
    }

    private static void emptyDirectory(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory())
                emptyDirectory(file);
            file.delete();
        }
    }
}
