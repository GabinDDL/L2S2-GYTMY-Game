package com.gytmy.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Predicate;

import com.gytmy.sound.User;

/**
 * AudioFileManager is a class that manages the audio files
 * regarding the user
 */
public class AudioFileManager {

    private static final File directory = new File("src/resources/audioFiles/");

    /**
     * Get the number of files valid for a predicate
     */
    public static int numberOfFilesVerifyingPredicate(File directory, Predicate<File> predicate) {
        int numberOfUsers = 0;
        for (File file : directory.listFiles()) {
            if (predicate.test(file)) {
                numberOfUsers++;
            }
        }
        return numberOfUsers;
    }

    /**
     * Get the number of users of the global directory
     */
    public static int numberOfUsers() {
        return numberOfFilesVerifyingPredicate(directory, File::isDirectory);
    }

    /**
     * Get the list of files verifying a predicate passed as parameter
     * 
     * @param directory
     * @param predicate
     * @return
     */
    public static ArrayList<File> filesVerifyingPredicate(File directory, Predicate<File> predicate) {
        ArrayList<File> files = new ArrayList<File>();
        for (File file : directory.listFiles()) {
            if (predicate.test(file)) {
                files.add(file);
            }
        }
        return files;
    }

    /**
     * Get the list of users
     */
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                users.add(new User(file.getName()));
            }
        }
        return users;
    }

    /**
     * Get the total number of audio files
     */
    public static int totalNumberOfAudioFiles() {
        int totalNumberOfAudioFiles = 0;
        for (User user : getUsers()) {
            totalNumberOfAudioFiles += numberOfAudioFiles(user.getFirstname());
        }
        return totalNumberOfAudioFiles;
    }

    /**
     * Get the number of audio files for a user
     */
    public static int numberOfAudioFiles(String userName) {
        File userDirectory = new File(directory + "/" + userName);

        if (!userDirectory.exists()) {
            return 0;
        }

        return numberOfFilesVerifyingPredicate(userDirectory, ((file) -> isAudioFile(file)));
    }

    /**
     * An audio file in our structure is a .wav file
     */
    private static boolean isAudioFile(File file) {
        return file.isFile() && file.getName().endsWith(".wav");
    }

    public static void addUser(User user) {

        if (new File(directory + "/" + user.getFirstname()).exists()) {
            throw new IllegalArgumentException("User already exists");
        }

        File userDirectory = new File(directory + "/" + user.getFirstname());
        userDirectory.mkdir();

        // TO DO: create his yaml file
        try {
            YamlReader.write(directory.getAbsolutePath() + "/" + user.getFirstname() + "/data.yaml", (User) user,
                    false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error while creating the yaml file for the user " + user);
        }
    }

    public static void main(String[] args) {
        System.out.println(numberOfUsers());
        System.out.println(numberOfAudioFiles("GABIN"));

        User yago = new User("Yago", "iglesias-vazquez");
        User mathusan = new User("MaThusAn", "SILVAKUMAR");
        User gabin = new User("GABIN", "DudilliEU");

        try {
            addUser(yago);
        } catch (IllegalArgumentException e) {
            System.out.println("User already exists");
        }
        try {
            addUser(mathusan);
        } catch (IllegalArgumentException e) {
            System.out.println("User already exists");
        }
        try {
            addUser(gabin);
        } catch (IllegalArgumentException e) {
            System.out.println("User already exists");
        }
    }
}
