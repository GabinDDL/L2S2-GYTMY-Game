package com.gytmy.utils;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import com.gytmy.sound.User;

public class YamlReader {

    /****************************** READ ***************************/
    // public static void main(String[] args) throws Exception {
    // File file = new File(dataStorageFilePath);
    // ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    // ApplicationConfig config = objectMapper.readValue(file,
    // ApplicationConfig.class);
    // System.out.println("Application config info " + config.toString());
    // }
    /****************************** READ ***************************/

    /****************************** WRITE ***************************/
    // public static void main(String[] args) throws Exception {

    // ArrayList<User> users = new ArrayList<User>();

    // User newUser = new User("Yago");
    // users.add(newUser);

    // ApplicationConfig appConfig = new ApplicationConfig();

    // ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    // objectMapper.writeValue(new
    // File(dataStorageFilePath), appConfig);
    // }
    /****************************** WRITE ***************************/

    /**
     * Read the .yaml config file
     * 
     * @return ApplicationConfig associatesd
     * @throws Exception
     */
    public static User read(String filePath) throws Exception {
        handleInvalidFilePath(filePath);

        File file = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        User user = objectMapper.readValue(file,
                User.class);
        return user;
    }

    /**
     * Write the .yaml config file the user
     */
    public static void write(String filePath, User user) throws Exception {
        handleInvalidFilePath(filePath);

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.writeValue(new File(filePath), user);
    }

    /**
     * Handle invalid exceptions
     */
    private static void handleInvalidFilePath(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Invalid file path");
        }

        if (!filePath.endsWith(".yaml")) {
            throw new IllegalArgumentException("Invalid file path");
        }

        if (fileDoesNotExists(filePath)) {
            throw new IllegalArgumentException("File does not exists");
        }
    }

    /**
     * Check if the file exists
     */
    private static boolean fileDoesNotExists(String filePath) {
        return !(new File(filePath)).exists();
    }

    public static void main(String[] args) throws Exception {

        String pathExample = "src/resources/audioFiles/Yago/config.yaml";

        User user = read(pathExample);
        System.out.println("User's Info :" + user.toString());
    }
}
