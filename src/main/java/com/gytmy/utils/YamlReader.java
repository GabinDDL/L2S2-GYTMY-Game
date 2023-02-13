package com.gytmy.utils;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import com.gytmy.sound.ApplicationConfig;
import com.gytmy.sound.User;

public class YamlReader {

    private static String dataStorageFilePath = "src/main/java/com/gytmy/config.yaml";

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

    public static void main(String[] args) throws Exception {
    }
}
