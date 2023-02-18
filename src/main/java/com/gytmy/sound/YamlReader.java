package com.gytmy.sound;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlReader {

    /**
     * Read the .yaml config file
     * 
     * @return ApplicationConfig associatesd
     * @throws Exception
     */
    public static User read(String filePath) throws Exception {
        handleInvalidFilePath(filePath, true);

        File file = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        User user = objectMapper.readValue(file,
                User.class);
        return user;
    }

    /**
     * Write the .yaml config file the user
     */
    public static void write(String filePath, User user, boolean shouldExists) throws Exception {
        handleInvalidFilePath(filePath, shouldExists);

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.writeValue(new File(filePath), user);
    }

    /**
     * Handle invalid exceptions
     */
    private static void handleInvalidFilePath(String filePath, boolean shouldExists) {
        if (filePath == null || filePath.isEmpty() || filePath.isBlank()) {
            throw new IllegalArgumentException("Invalid file path : " + filePath);
        }

        if (!filePath.endsWith(".yaml")) {
            throw new IllegalArgumentException("Invalid file extension : " + filePath);
        }

        if (shouldExists && fileDoesNotExists(filePath)) {
            throw new IllegalArgumentException("File does not exists : " + filePath);
        }
    }

    /**
     * Check if the file exists
     */
    private static boolean fileDoesNotExists(String filePath) {
        return !(new File(filePath)).exists();
    }
}
