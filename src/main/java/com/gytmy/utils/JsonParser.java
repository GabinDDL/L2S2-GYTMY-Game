package com.gytmy.utils;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser<T> {

    private ObjectMapper objectMapper;

    public JsonParser() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Parses a JSON file into a Java object
     * 
     * @param filePath The path to the JSON file
     * @param type     The type of the Java object to parse the JSON file into
     * @return The Java object
     * @throws IOException
     */
    public T parseJsonFromFile(String filePath, Class<T> type) throws IOException {
        File file = new File(filePath);
        T result = objectMapper.readValue(file, type);
        return result;
    }
}
