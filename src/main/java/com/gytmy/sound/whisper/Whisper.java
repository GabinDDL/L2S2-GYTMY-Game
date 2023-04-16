package com.gytmy.sound.whisper;

import com.gytmy.utils.JsonParser;
import com.gytmy.utils.RunSH;

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

    public static final String WHISPER_PATH = System.getProperty("user.dir")+ "/src/main/exe/whisper/whisper.sh";

    private JsonParser<WhisperResult> whisperJsonParser = new JsonParser<>();

    private boolean isGPU;
    private Model model;                // The whisper model to use

    public Whisper(boolean isGPU, Model model) {
        this.isGPU = isGPU;
        this.model = model;
    }
    
    /**
     * Runs the whisper.sh script with the given parameters
     * @param directoryPath The path to the directory containing the audio file
     * @param fileName      The name of the audio file
     * @param outputPath    The path to the directory where the output file will be saved
     * @return The text extracted from the audio file
     */
    public String run(String directoryPath, String fileName, String outputPath) {

        System.out.println(WHISPER_PATH);
        System.out.println(getFullFilePath(directoryPath, fileName));
        System.out.println(getFullOutputPath(outputPath));
        
        String[] args = { "-d", getDeviceName(isGPU), "-m", model.getModelName(), "-a", getFullFilePath(directoryPath, fileName),
                "-o", getFullOutputPath(outputPath)};

        int exitCode = RunSH.run(WHISPER_PATH, args);

        if (exitCode == 0) {
            try {
                WhisperResult whisperResult = (WhisperResult) whisperJsonParser.parseJsonFromFile(outputPath + "/" + fileName + ".json", WhisperResult.class);
                return whisperResult.getText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Whisper failed with exit code: " + exitCode);
        }

        return "";
    }

    /**
     * Returns the name of the device to use
     * @param isGPU Whether to use the GPU or not
     * @return The name of the device to use
     */
    private String getDeviceName(boolean isGPU) {
        return isGPU ? "cuda" : "cpu";
    }

    /**
     * Returns the full path to the file from the project base directory
     * @param directoryPath The path to the directory containing the file
     * @param fileName      The name of the file
     * @return The full path to the file
     */
    private String getFullFilePath(String directoryPath, String fileName) {
        String projectPathBase = System.getProperty("user.dir");
        return projectPathBase + "/" + directoryPath + "/" + fileName + ".wav";
    }

    /**
     * Returns the full path to the output directory from the project base directory
     * @param directoryPath The path to the output directory
     * @return The full path to the output directory
     */
    private String getFullOutputPath(String directoryPath) {
        String projectPathBase = System.getProperty("user.dir");
        return projectPathBase + "/" + directoryPath + "/";
    }
}
