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

    public static final String WHISPER_PATH = "src/main/exe/whisper/whisper.sh";

    private JsonParser<WhisperResult> whisperJsonParser = new JsonParser<>();

    private Model model;                // The whisper model to use

    public Whisper(Model model) {
        this.model = model;
    }
    
    /**
     * Runs the whisper.sh script with the given parameters
     * @param directoryPath The path to the directory containing the audio file
     * @param fileName      The name of the audio file
     * @param outputPath    The path to the directory where the output file will be saved
     * @return The text extracted from the audio file
     */
    public String run(String filePathWithFileName, String fileName, String outputPath) {
        
        String[] args = {"-m", model.getModelName(), "-a", filePathWithFileName, "-o", outputPath};

        int exitCode = RunSH.run(WHISPER_PATH, args);

        if (exitCode == 0) {
            try {
                WhisperResult whisperResult = (WhisperResult) whisperJsonParser.parseJsonFromFile(outputPath + "/" + fileName + ".json", WhisperResult.class);
                return removeNonAlphabet(whisperResult.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Whisper failed with exit code: " + exitCode);
        }

        return "";
    }

    /**
     * Remove non-alphanumeric characters from the given string
     */
    public static String removeNonAlphabet(String text) {
        return text.replaceAll("[^a-zA-Z]", "");
    }
}
