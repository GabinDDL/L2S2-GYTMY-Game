package com.gytmy.sound;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.gytmy.utils.RunSH;

public class AudioRecognitionResult {

    private AudioRecognitionResult() {
    }

    public static final String EXE_PATH = "src/main/exe/comparaison/";
    public static final String COMPUTE_TEST_SH_PATH = EXE_PATH + "ComputeTest.sh";
    public static final String NDX_LIST_PATH = EXE_PATH + "ndx/Liste.ndx";

    public static final String CLIENT_MODEL_PATH = "src/ressources/audioFiles/client/model/";
    public static final String CLIENT_RESULT_PATH = CLIENT_MODEL_PATH + "RecognitionResult.txt";

    private static boolean tryToResetComputeTestNdxFile() {
        try (FileWriter writer = new FileWriter(NDX_LIST_PATH, false);) {
            writer.append("currentAudio");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
        try (FileWriter writer = new FileWriter(NDX_LIST_PATH, true);) {
            for (File file : gmmList) {
                writer.append(getFileBasename(file) + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isGmmModelFile(File file) {
        return file.isFile() && file.getName().endsWith(".gmm") && !file.getName().startsWith("wld");
    }

    /**
     * By convention, the name of a file is constitued of the basename
     * and its extensions, _e.g._ basename.extension1.extension2...
     *
     * @example getFileBasename("test.wav") --> "test"
     * @example getFileBasename("newTest.tar.gz") --> "newTest"
     * @param file The file from which we want the basename
     * @return The basename of the file
     */
    private static String getFileBasename(File file) {
        String name = file.getName();
        int index = file.getName().indexOf('.');

        if (index == -1) {
            return name;
        }
        return name.substring(0, index);
    }

    private static void ComputeTest() {
        String[] argsTrainWorld = {};

        int exitValue = RunSH.run(COMPUTE_TEST_SH_PATH, argsTrainWorld);
        handleErrorProgram("trainWorld", exitValue);

    }

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
