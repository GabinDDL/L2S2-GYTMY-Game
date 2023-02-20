package com.gytmy.sound;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlReader {

    private YamlReader() {
    }

    /**
     * Read the .yaml config file
     * 
     * @return ApplicationConfig associated
     * @throws Exception
     */
    public static User read(String filePath) {
        handleInvalidFilePath(filePath);

        File file = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        try {
            User user = objectMapper.readValue(file, User.class);
            return user;
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not read file : " + filePath);
        }
    }

    /**
     * Write the .yaml config file the user
     */
    public static void write(String filePath, User user) {
        handleInvalidFilePath(filePath);

        File yamlFile = new File(filePath);

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            if (fileDoesNotExist(filePath)) {
                yamlFile.createNewFile();
            }

            objectMapper.writeValue(yamlFile, user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not write to file : " + filePath);
        }
    }

    /**
     * Handle invalid exceptions
     */
    private static void handleInvalidFilePath(String filePath) {
        if (filePath == null || filePath.isEmpty() || filePath.isBlank()) {
            throw new IllegalArgumentException("Invalid file path : " + filePath);
        }

        if (!filePath.endsWith(".yaml")) {
            throw new IllegalArgumentException("Invalid file extension : " + filePath);
        }
    }

    /**
     * Check if the file exists
     */
    private static boolean fileDoesNotExist(String filePath) {
        return !(new File(filePath)).exists();
    }
}
