package com.gytmy.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Predicate;

import com.gytmy.sound.SampledUser;

public class AudioFileManager {

    private static File directory = new File("src/resources/audioFiles/");

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
    public static ArrayList<SampledUser> getUsers() {
        ArrayList<SampledUser> users = new ArrayList<SampledUser>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                users.add(new SampledUser(file.getName()));
            }
        }
        return users;
    }

    /**
     * Get the number of audio files for a user
     */
    public static int numberOfAudioFiles(String userName) {
        File userDirectory = new File(directory + "/" + userName);
        return numberOfFilesVerifyingPredicate(userDirectory, ((file) -> isAudioFile(file)));
    }

    /**
     * An audio file in our structure is a .wav file
     */
    private static boolean isAudioFile(File file) {
        return file.isFile() && file.getName().endsWith(".wav");
    }

    public static void main(String[] args) {
        System.out.println(numberOfUsers());
        System.out.println(numberOfAudioFiles("Yago"));
    }
}
