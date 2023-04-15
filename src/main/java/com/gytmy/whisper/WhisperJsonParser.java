package com.gytmy.whisper;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WhisperJsonParser {
    
    private ObjectMapper objectMapper;
    
    public WhisperJsonParser() {
        objectMapper = new ObjectMapper();
    }
    
    public WhisperResult parseJsonFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        WhisperResult whisperResult = objectMapper.readValue(file, WhisperResult.class);
        return whisperResult;
    } 
}
