package com.gytmy.sound.whisper;

import com.gytmy.utils.JsonParser;
import com.gytmy.utils.RunSH;


import java.util.concurrent.CompletableFuture;

public class Whisper {

    public enum Model {
        TINY_EN, BASE_EN, SMALL_EN, MEDIUM_EN;

        public String getModelName() {
            switch (this) {
                case TINY_EN:
                    return "tiny.en";
                case BASE_EN:
                    return "base.en";
                case SMALL_EN:
                    return "small.en";
                case MEDIUM_EN:
                    return "medium.en";
                default:
                    return "tiny.en";
            }
        }
    };

    public static final String WHISPER_PATH = "src/main/exe/whisper/whisper.sh";

    private JsonParser<WhisperResult> whisperJsonParser = new JsonParser<>();

    private Model model;                // The whisper model to use

    public Whisper(Model model) {
        this.model = model;
    }

    /**
     * Asks the user for a command and returns it as a future
     */
    public CompletableFuture<String> ask(String audioPath, String fileName, String outputPath) {
        CompletableFuture<String> futureCommand = new CompletableFuture<>();

        new Thread(() -> {
            try {
                String recognizedCommand = "";
                
                int exitCode = run(audioPath, fileName, outputPath);
                
                if (exitCode == 0) {
                    recognizedCommand = parseJson(outputPath, fileName);
                    recognizedCommand = formatCommand(recognizedCommand);
                    recognizedCommand = mapCommand(recognizedCommand);
                    futureCommand.complete(recognizedCommand);
                } else {
                    futureCommand.completeExceptionally(new Exception("Whisper failed to recognize command"));
                }

            } catch (Exception e) {
                futureCommand.completeExceptionally(e);
            }

        }).start();

        return futureCommand;
    }
    
    /**
     * Runs the whisper command
     * @return the exit code of the command
     */
    private int run(String filePathWithFileName, String fileName, String outputPath) {
        String[] args = {"-m", model.getModelName(), "-a", filePathWithFileName, "-o", outputPath};
        
        int exitCode = RunSH.run(WHISPER_PATH, args);
        
        return exitCode;
    }

    /**
     * Parses the json file and returns the recognized command
     */
    private String parseJson(String jsonDirectoryPath, String fileName) {
        try {
            WhisperResult whisperResult = (WhisperResult) whisperJsonParser.parseJsonFromFile(
                jsonDirectoryPath + "/" + fileName + ".json", WhisperResult.class);

            return whisperResult.getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private String formatCommand(String text) {
        return text.replaceAll("[^a-zA-Z]", "");
    }

    private String mapCommand(String text) {
        if (text.equalsIgnoreCase("UP")) {
            return "UP";
        } else if (text.equalsIgnoreCase("DOWN")) {
            return "DOWN";
        } else if (text.equalsIgnoreCase("LEFT")) {
            return "LEFT";
        } else if (text.equalsIgnoreCase("RIGHT")) {
            return "RIGHT";
        } else {
            return "NO_COMMAND";
        }
    }
}