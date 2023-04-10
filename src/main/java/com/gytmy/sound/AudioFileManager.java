package com.gytmy.sound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

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
     * Create the user directory: all the subdirectories and files
     */
    public static void createUserFiles(User user) {

        File userDirectory = new File(user.audioFilesPath());
        userDirectory.mkdir();

        writeYamlConfig(user);

        File userAudioDirectory = new File(user.audioPath());
        userAudioDirectory.mkdir();

        File userModelDirectory = new File(user.modelPath());
        userModelDirectory.mkdir();
    }

    /**
     * Translate the user object to a yaml file
     */
    public static void writeYamlConfig(User user) {
        writeYamlConfigAtCertainPath(user, user.yamlConfigPath());
    }

    /**
     * Translate the user object to a yaml file at a certain path given as parameter
     */
    public static void writeYamlConfigAtCertainPath(User user, String path) {
        try {
            YamlReader.write(path, user);
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
     * Delete the user directory and all the files in it.
     */
    private static void deleteFiles(User user) {
        File userDirectory = new File(user.audioFilesPath());
        clearDirectory(userDirectory);
        userDirectory.delete();
    }

    private static void clearDirectory(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory() && file.canWrite()) {
                clearDirectory(file);
            }
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
     * Get the number of audio files for a specific user.
     */
    public static int totalNumberOfAudioFilesForUser(String userName) {
        int numberOfAudioFiles = 0;

        for (String word : WordsToRecord.getWordsToRecord()) {
            numberOfAudioFiles += numberOfRecordings(userName, word);
        }

        return numberOfAudioFiles;
    }

    /**
     * Get the number of audio files for a specific word and user.
     */
    public static int numberOfRecordings(String userName, String word) {

        if (!WordsToRecord.exists(word)) {
            throw new IllegalArgumentException("Word does not exist");
        }

        File userDirectory = new File(SRC_DIR_PATH + "/" + userName.toUpperCase() + "/audio/" + word);

        if (!userDirectory.exists()) {
            return 0;
        }

        return numberOfFilesVerifyingPredicate(userDirectory, AudioFileManager::isAudioFile);
    }

    /**
     * Returns {@code true} if the file is a .wav file. In our case, we only want to
     * record .wav files, any other file will be ignored.
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
        return getFilesVerifyingPredicate(directory, predicate, false);
    }

    public static List<File> getFilesVerifyingPredicate(File directory, Predicate<File> predicate, boolean recursive) {
        List<File> files = new ArrayList<>();

        if (!directory.exists()) {
            return files;
        }

        for (File file : directory.listFiles()) {
            if (recursive && file.isDirectory()) {
                files.addAll(getFilesVerifyingPredicate(file, predicate, true));
            } else if (predicate.test(file)) {
                files.add(file);
            }
        }

        return files;
    }

    public static void deleteRecording(String firstName, String wordToRecord, int i) {
        User user = YamlReader.read(SRC_DIR_PATH + firstName.toUpperCase() + "/config.yaml");

        deleteRecording(user.audioPath() + wordToRecord + "/" + wordToRecord + i + ".wav");

        user.setUpToDate(false);
        YamlReader.write(user.yamlConfigPath(), user);
    }

    public static void deleteRecording(String filePath) {
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    /**
     * Rename the audio files after a deletion of a recording
     * For example, if in the directory of a user, there are 3 files named
     * "word1.wav", "word2.wav" and "word3.wav".
     * And we delete the file "word2.wav", the file "word3.wav" will be renamed
     * "word2.wav"
     * 
     * @param wordIndex          the index of the file that has been deleted
     * @param numberOfRecordings the number of recordings for the word before
     *                           deleting the file
     */
    public static void renameAudioFiles(String firstName, String word, int wordIndex, int numberOfRecordings) {
        File userDirectory = new File(SRC_DIR_PATH + firstName + "/audio/" + word);

        if (!userDirectory.exists()) {
            return;
        }

        int diff = 1;
        for (int index = wordIndex + 1; index <= numberOfRecordings; index++) {

            File fileToRename = new File(userDirectory + "/" + word + index + ".wav");
            File newFile = new File(userDirectory + "/" + word + (index - diff) + ".wav");

            if (fileToRename.exists()) {
                fileToRename.renameTo(newFile);
            } else {
                diff++;
            }
        }
    }

    /**
     * To modify an existing user, we need to modify the yaml file and the
     * directory name
     * So, we rewrite the yaml file with data of the new user
     * 
     * @param userToEdit an old user that already exists
     * @param user       the new user that will replace the old one
     */
    public static void editUser(User userToEdit, User user) {
        List<User> allUsers = getUsers();

        for (User currentUser : allUsers) {
            if (currentUser.equals(userToEdit)) {
                writeYamlConfigAtCertainPath(user, userToEdit.yamlConfigPath());

                File oldUserDirectory = new File(userToEdit.audioFilesPath());
                oldUserDirectory.renameTo(new File(user.audioFilesPath()));
            }
        }
    }

    /**
     * Create the user word directory if it does not exist
     */
    public static boolean tryToCreateUserWordDirectory(User user, String word) {
        if (user == null) {
            return false;
        }

        boolean atLeastOneDirectoryWasCreated = false;

        File userAudioDirectory = new File(user.audioPath());

        if (!userAudioDirectory.exists()) {
            userAudioDirectory.mkdir();
        }

        if (!doesFileExistInDirectory(userAudioDirectory, user.audioPath() + word)) {
            new File(user.audioPath() + word).mkdir();
            atLeastOneDirectoryWasCreated = true;
        }

        atLeastOneDirectoryWasCreated = ModelManager.tryToCreateModelDirectoryOfWord(user, word)
                || atLeastOneDirectoryWasCreated;

        return atLeastOneDirectoryWasCreated;
    }

    /**
     * Return true if file is inside directory
     */
    public static boolean doesFileExistInDirectory(File directory, String file) {
        List<File> files = getFilesVerifyingPredicate(directory, File::isDirectory);

        return files.contains(new File(file));
    }

    public static float getTotalDurationOfAllAudioFiles() {
        return getTotalDurationOfAllAudioFiles(getAllAudioFiles());
    }

    private static List<File> getAllAudioFiles() {
        return getFilesVerifyingPredicate(SRC_DIRECTORY, AudioFileManager::isAudioFile, true);
    }

    /**
     * @param audioFiles
     * @return the total duration of all audio files in seconds
     */
    public static float getTotalDurationOfAllAudioFiles(List<File> audioFiles) {
        float durationOfAllFilesInSeconds = 0;

        if (audioFiles == null) {
            return durationOfAllFilesInSeconds;
        }

        for (File file : audioFiles) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = audioInputStream.getFormat();
                long audioFileLength = file.length();
                int frameSize = format.getFrameSize();
                float frameRate = format.getFrameRate();
                durationOfAllFilesInSeconds += (audioFileLength / (frameSize * frameRate));
            } catch (UnsupportedAudioFileException | IOException e) {
            }
        }
        return durationOfAllFilesInSeconds;
    }
}
