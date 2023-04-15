package com.gytmy.whisper;

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

    public static String WHISPER_PATH = "src/main/exe/whisper/whisper.sh";

    private JsonParser<WhisperResult> whisperJsonParser = new JsonParser<WhisperResult>();

    private boolean isGPU;
    private Model model;

    public Whisper(boolean isGPU, Model model) {
        this.isGPU = isGPU;
        this.model = model;
    }

    public String run(String directoryPath, String fileName, String outputPath) {

        String[] args = { "-d", getDeviceName(isGPU), "-m", model.getModelName(), "-a", directoryPath + "/" + fileName + ".wav",
                "-o", outputPath };

        int exitCode = RunSH.run(WHISPER_PATH, args);

        if (exitCode != 0) {
            System.out.println("Whisper failed with exit code: " + exitCode);
        } else {
            try {
                WhisperResult whisperResult = (WhisperResult) whisperJsonParser.parseJsonFromFile(outputPath + "/" + fileName + ".json", WhisperResult.class);
                return whisperResult.getText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    private String getDeviceName(boolean isGPU) {
        return isGPU ? "cuda" : "cpu";
    }
}
