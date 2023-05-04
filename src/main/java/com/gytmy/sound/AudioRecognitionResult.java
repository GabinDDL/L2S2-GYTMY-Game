package com.gytmy.sound;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.gytmy.sound.AlizeRecognitionResultParser.IncorrectFileFormatException;
import com.gytmy.sound.AlizeRecognitionResultParser.AlizeRecognitionResult;
import com.gytmy.utils.RunSH;

public class AudioRecognitionResult {

    private AudioRecognitionResult() {
    }

    public static final String EXE_PATH = "src/main/exe/comparison/";
    public static final String COMPUTE_TEST_SH_PATH = EXE_PATH + "ComputeTest.sh";
    public static final String NDX_PATH = EXE_PATH + "ndx/";
    public static final String NDX_LIST_PATH = NDX_PATH + "Liste.ndx";

    public static final String CLIENT_PATH = "src/resources/audioFiles/client/";
    public static final String CLIENT_AUDIO_PATH = CLIENT_PATH + "audio/";
    public static final String CLIENT_MODEL_PATH = CLIENT_PATH + "model/";
    public static final String CLIENT_RESULT_PATH = CLIENT_MODEL_PATH + "RecognitionResult.txt";
    public static final String CLIENT_LST_LIST_PATH = CLIENT_MODEL_PATH + "lst/Liste.lst";

    /**
     * @return the result of comparison
     *         null otherwise
     */
    public static AlizeRecognitionResult getRecognitionResult() {
        generateComparisonDirectoryStructure();

        if (!manageComparison()) {
            return null;
        }
        try {
            return AlizeRecognitionResultParser
                    .parseFile(new File(CLIENT_RESULT_PATH));
        } catch (IncorrectFileFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void generateComparisonDirectoryStructure() {
        ModelManager.createDirectory(NDX_PATH);
    }

    /**
     * Initialize and run ComputeTest program
     * 
     * @return true if there is no error during the initialization of the
     *         ComputeTest
     */
    private static boolean manageComparison() {
        if (!initComparison()) {
            return false;
        }

        computeTest();

        ModelManager.resetParameter();
        return true;
    }

    /**
     * 
     * @return true if the initialization of the comparison went well
     */
    private static boolean initComparison() {
        if (!(tryToResetComputeTestNdxFile() && tryToUpdateComputeTestNdxFile())) {
            return false;
        }
        createPrmOfCurrentAudio();
        return initRecognitionResultTxt();
    }

    private static boolean tryToResetComputeTestNdxFile() {
        return ModelManager.tryToResetNdxFile(NDX_LIST_PATH, "currentGameAudio");
    }

    private static boolean tryToUpdateComputeTestNdxFile() {
        File dataDirectory = new File(ModelManager.GMM_PATH);

        if (!dataDirectory.exists()) {
            return false;
        }
        List<File> gmmList = AudioFileManager.getFilesVerifyingPredicate(dataDirectory,
                AudioRecognitionResult::isGmmModelFile);

        return tryToAddComputeTestNdxFile(gmmList);
    }

    private static boolean tryToAddComputeTestNdxFile(List<File> gmmList) {
        return ModelManager.tryToAddListToNdxFile(gmmList, NDX_LIST_PATH);
    }

    /**
     * Returns {@code true} if the file is a .gmm file (and not wld).
     * 
     * In our case, we only want to update it with models which exists.
     * 
     * @param file
     * @return
     */
    private static boolean isGmmModelFile(File file) {
        return file.isFile() && file.getName().endsWith(".gmm") && !file.getName().startsWith("wld");
    }

    private static void createPrmOfCurrentAudio() {
        String client = "client";
        String other = "other";
        ModelManager.resetParameter();
        ModelManager.parametrize(CLIENT_LST_LIST_PATH, CLIENT_AUDIO_PATH, client, other);
        ModelManager.energyDetector(CLIENT_LST_LIST_PATH, client, other);
        ModelManager.normFeat(CLIENT_LST_LIST_PATH, client, other);
    }

    /**
     * @return true if the file RecognitionResult.txt exists and it is reset
     *         or if it doesn't exists and it success to be created
     */
    private static boolean initRecognitionResultTxt() {
        File txtFile = new File(CLIENT_RESULT_PATH);
        try {
            if (!txtFile.exists()) {
                return txtFile.createNewFile();
            } else {
                return resetRecognitionResultTxt();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return true if the file RecognitionResult.txt is reset and doesn't fail
     */
    private static boolean resetRecognitionResultTxt() {
        try (FileWriter writer = new FileWriter(CLIENT_RESULT_PATH, false);) {
            writer.append("");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void computeTest() {
        String[] argsTrainWorld = {};

        int exitValue = RunSH.run(COMPUTE_TEST_SH_PATH, argsTrainWorld);
        handleErrorProgram("computeTest", exitValue);

    }

    /**
     * Handle the error of modeling programs
     * 
     */

    private static void handleErrorProgram(String program, int exitValue) {
        if (exitValue != 0) {
            printErrorRun(program);
        }
    }

    /**
     * Print the error message of the program
     * 
     */
    private static void printErrorRun(String program) {
        System.out.println("There is a problem with the program : " + program
                + "\nThe problem happens when the program tries to compare models with the current Audio"
                + "\nMaybe try to update the models.\n");
    }

}
