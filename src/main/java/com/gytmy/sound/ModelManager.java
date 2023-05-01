package com.gytmy.sound;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.gytmy.utils.FileInformationFinder;
import com.gytmy.utils.RunSH;
import com.gytmy.utils.WordsToRecord;

public class ModelManager {

    private ModelManager() {
    }

    public static final String AUDIO_FILES_PATH = "src/resources/audioFiles/";

    public static final String EXE_PATH = "src/main/exe/model/";
    public static final String PARAMETRIZE_SH_PATH = EXE_PATH + "Parametrize.sh";
    public static final String ENERGY_DETECTOR_SH_PATH = EXE_PATH + "EnergyDetector.sh";
    public static final String NORM_FEAT_SH_PATH = EXE_PATH + "NormFeat.sh";
    public static final String TRAIN_WORLD_SH_PATH = EXE_PATH + "TrainWorld.sh";
    public static final String TRAIN_TARGET_SH_PATH = EXE_PATH + "TrainTarget.sh";
    public static final String PRM_PATH = EXE_PATH + "prm/";
    public static final String LBL_PATH = EXE_PATH + "lbl/";
    public static final String GMM_PATH = EXE_PATH + "gmm/";
    public static final String LST_WORLD_PATH = EXE_PATH + "lst/";
    public static final String LIST_LST_WORLD_PATH = LST_WORLD_PATH + "Liste.lst";
    public static final String LST_PATH = "/lst/";
    public static final String NDX_PATH = "/ndx/";
    public static final String LIST_NDX_PATH = NDX_PATH + "ListNDX.ndx";
    public static final String LIST_LST_PATH = LST_PATH + "ListLST.lst";

    /**
     * If the folders of model do not exist,
     * create their and its arborescence
     */
    private static void generateModelDirectoryStructure() {
        createDirectory(PRM_PATH);
        createDirectory(LBL_PATH);
        createDirectory(LST_WORLD_PATH);
        createDirectory(LST_WORLD_PATH);
    }

    protected static void createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * @param user
     * @param word
     * @return true if all directories for the model of a user's word have been
     *         created.
     * 
     *         These directories are :
     * 
     *         - Model directory
     *         - LST directory
     *         - NDX directory
     * 
     *         and these are created in the user's directory in AudioFiles.
     */
    protected static boolean tryToCreateModelDirectoriesOfWord(User user, String word) {
        File userModelDirectory = new File(user.modelPath());
        if (!userModelDirectory.exists()) {
            userModelDirectory.mkdir();
        }
        if (!AudioFileManager.doesFileExistInDirectory(userModelDirectory, user.modelPath() + word)) {
            new File(user.modelPath() + word).mkdir();
            File userNdxDirectory = new File(user.modelPath() + word + NDX_PATH);
            userNdxDirectory.mkdir();
            File userLstDirectory = new File(user.modelPath() + word + LST_PATH);
            userLstDirectory.mkdir();
            return true;
        }
        return false;
    }

    /**
     * Create all models for all users.
     * Those models are necessary for the voice controlled movements.
     * 
     * @param firstNameOfUsers
     */
    public static void recreateModelOfAllUsers() {
        generateModelDirectoryStructure();
        List<User> users = AudioFileManager.getUsers();
        createModelOfWorld(users);
        createModelOfAllUsers(users);
        resetParameter();
    }

    /**
     * Create the world model using the parameters of the users in the list.
     * 
     * @param users
     */
    private static void createModelOfWorld(List<User> users) {
        for (User user : users) {
            createAllParametersOfRecordedWord(user);
        }
        resetModel();
        tryToUpdateWorldLstFile();
        trainWorld();
    }

    /**
     * Creates all parameters for all existing words from the user's data.
     * 
     * @param user
     */
    private static void createAllParametersOfRecordedWord(User user) {
        for (String word : WordsToRecord.getWordsToRecord()) {
            createParametersOfRecordedWord(user, word);
        }
        user.setUpToDate(true);
        YamlReader.write(user.yamlConfigPath(), user);
    }

    /**
     * Initializes the parameters with the updated lst if it is possible.
     * Then it will apply the parameterization for the user's word.
     *
     * @param user
     * @param recordedWord
     */
    private static void createParametersOfRecordedWord(User user, String recordedWord) {
        if (!doesUserHaveDataOfWord(user, recordedWord) || !tryToInitParameterization(user, recordedWord)) {
            return;
        }
        if (!tryToUpdateNdxAndLstFileOfUserWord(user, recordedWord)) {
            return;
        }
        String listPathOfUser = user.modelPath() + recordedWord + LIST_LST_PATH;
        String audioPathOfUser = user.audioPath() + recordedWord + "/";
        parametrize(listPathOfUser, audioPathOfUser, user.getFirstName(), recordedWord);
        energyDetector(listPathOfUser, user.getFirstName(), recordedWord);
        normFeat(listPathOfUser, user.getFirstName(), recordedWord);
    }

    /**
     * @param user
     * @param recordedWord
     * @return true if the user's word file exists.
     */
    public static boolean doesUserHaveDataOfWord(User user, String recordedWord) {
        return new File(user.audioPath() + recordedWord + "/").exists();
    }

    /**
     * Tries to init the ndx and lst files of the user's word.
     * 
     * If the file doesn't exist, it creates it.
     * If it exists, it resets it.
     * 
     * @param user
     * @param recordedWord
     * @return
     */
    private static boolean tryToInitParameterization(User user, String recordedWord) {
        if (!WordsToRecord.exists(recordedWord)) {
            return false;
        }
        if (!doesAudioFilesHaveAGoodLength(user, recordedWord)) {
            return false;
        }
        return tryToInitLstFilesOfUserWord(user, recordedWord)
                && tryToInitNdxFilesOfUserWord(user, recordedWord);
    }

    /**
     * @param user
     * @param recordedWord
     * @return true if the total length of the audio files for an specific word is
     *         greater than {@code upperDuration}(3.5 seconds).
     */
    public static boolean doesAudioFilesHaveAGoodLength(User user, String recordedWord) {
        double upperDuration = 3.5;
        File dataDirectory = new File(user.audioPath() + recordedWord + "/");
        List<File> list = AudioFileManager.getFilesVerifyingPredicate(dataDirectory, ModelManager::isAudioFile);
        return FileInformationFinder.getAudioLength(list) > upperDuration;
    }

    /**
     * Try to create the new lst file if it doesn't exist.
     * Try to reset if it does.
     * 
     * @param user
     * @param recordedWord
     * @return false if there is an error with the initialisation.
     */
    public static boolean tryToInitLstFilesOfUserWord(User user, String recordedWord) {
        if (!WordsToRecord.exists(recordedWord)) {
            return false;
        }
        File lstFile = new File(user.modelPath() + recordedWord + LIST_LST_PATH);
        try {
            if (!lstFile.exists()) {
                return lstFile.createNewFile();
            } else {
                return tryToResetLstFilesOfUserWord(user, recordedWord);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Try to create the new ndx file if it doesn't exist.
     * Try to reset if it does.
     * 
     * @param user
     * @param recordedWord
     * @return false if there is an error with the initialisation.
     */
    public static boolean tryToInitNdxFilesOfUserWord(User user, String recordedWord) {
        if (!WordsToRecord.exists(recordedWord)) {
            return false;
        }
        File ndxFile = new File(user.modelPath() + recordedWord + LIST_NDX_PATH);
        try {
            if (!ndxFile.exists()) {
                boolean wasCreated = ndxFile.createNewFile();
                return wasCreated && tryToResetNdxFilesOfUserWord(user, recordedWord);
            } else {
                return tryToResetNdxFilesOfUserWord(user, recordedWord);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tries to reset the lst file of the user's word.
     * 
     * This file should exist.
     * 
     * @param user
     * @param recordedWord
     * @return false if there is a problem while handling the user's word file.
     * 
     */
    private static boolean tryToResetLstFilesOfUserWord(User user, String recordedWord) {
        return tryToResetLstFile(user.modelPath() + recordedWord + LIST_LST_PATH);
    }

    /**
     * Tries to reset the lst file.
     * 
     * This file should exist.
     * 
     * @param lstPath
     * @return false if there is a problem while handling the file.
     */
    private static boolean tryToResetLstFile(String lstPath) {
        try (FileWriter writer = new FileWriter(lstPath, false);) {
            writer.append("");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Tries to reset the ndx file of the user's word.
     * 
     * This file should exist.
     * 
     * @param user
     * @param recordedWord
     * @return false if there is a problem while handling the user's word file.
     * 
     */
    private static boolean tryToResetNdxFilesOfUserWord(User user, String recordedWord) {
        return tryToResetNdxFile(user.modelPath() + recordedWord + LIST_NDX_PATH,
                user.getFirstName() + "_" + recordedWord);
    }

    /**
     * Tries to reset the ndx file, and adds the word at the start of the file
     * This file should exist.
     * 
     * @param path
     * @param startWord
     * @return false if there is a problem while handling the file
     */
    protected static boolean tryToResetNdxFile(String path, String startWord) {
        try (FileWriter writer = new FileWriter(path, false);) {
            writer.append(startWord);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Try to update the data of the ndx and lst files with the data of the
     * {@code recordedWord} of the {@code User}.
     * 
     * It will append all the names of the files without extension to the ndx and
     * lst files.
     * 
     * @param user
     * @param recordedWord
     * @return false if there is a problem with the file of the user's word.
     */
    private static boolean tryToUpdateNdxAndLstFileOfUserWord(User user, String recordedWord) {
        File dataDirectory = new File(user.audioPath() + recordedWord + "/");
        if (!dataDirectory.exists()) {
            return false;
        }
        List<File> list = AudioFileManager.getFilesVerifyingPredicate(dataDirectory, ModelManager::isAudioFile);
        return tryToAddAudiosToNdxFilesOfUserWord(user, recordedWord, list)
                && tryToAddAudiosToLstFilesOfUserWord(user, recordedWord, list);
    }

    /**
     * Returns {@code true} if the file is a .wav file.
     * 
     * In our case, we only want to record .wav files, any other file will be
     * ignored.
     * 
     * @param file
     * @return
     */
    private static boolean isAudioFile(File file) {
        return file.isFile() && file.getName().endsWith(".wav");
    }

    /**
     * Appends the name of the files to the lst file of the {@code user}
     * {@code recordedWord}.
     * 
     * @param user
     * @param recordedWord
     * @param audioList    List of audio files
     * @return false if there is a problem with the file of the user's word
     */
    private static boolean tryToAddAudiosToLstFilesOfUserWord(User user, String recordedWord, List<File> audioList) {
        return tryToAddListToLstFile(audioList, user.modelPath() + recordedWord + LIST_LST_PATH);
    }

    /**
     * Appends the name of the files to the lst file
     * 
     * @param list
     * @param lstPath
     * @return false if there is a problem with the file
     */
    private static boolean tryToAddListToLstFile(List<File> list, String lstPath) {
        try (FileWriter writer = new FileWriter(lstPath, true);) {
            for (File file : list) {
                writer.append(getFileBasename(file) + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Appends the name of the files to the ndx file of the {@code user}
     * {@code recordedWord}.
     * 
     * @param user
     * @param recordedWord
     * @param audioList    List of audio files
     * @return false if there is a problem with the file of the user's word
     */
    private static boolean tryToAddAudiosToNdxFilesOfUserWord(User user, String recordedWord,
            List<File> audioList) {
        return tryToAddListToNdxFile(audioList, user.modelPath() + recordedWord + LIST_NDX_PATH);
    }

    /**
     * Appends the name of the files to the ndx file
     * 
     * @param list
     * @param ndxPath
     * @return false if there is a problem with the file
     */
    protected static boolean tryToAddListToNdxFile(List<File> list, String ndxPath) {
        try (FileWriter writer = new FileWriter(ndxPath, true);) {
            for (File file : list) {
                writer.append(" " + getFileBasename(file));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * By convention, the name of a file is constituted of the basename
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

    /**
     * @param listPath
     * @param audioPath
     * @param name
     * @param word
     */
    protected static void parametrize(String listPath, String audioPath, String name, String word) {
        String[] argsParametrize = { listPath, audioPath };
        int exitValue = RunSH.run(PARAMETRIZE_SH_PATH, argsParametrize);
        handleErrorProgram("parametrize (sfbcep)", exitValue, name, word);
    }

    /**
     * @param listPath
     * @param name
     * @param word
     */
    protected static void energyDetector(String listPath, String name, String word) {
        String[] argsEnergyDetector = { listPath };
        int exitValue = RunSH.run(ENERGY_DETECTOR_SH_PATH, argsEnergyDetector);
        handleErrorProgram("energyDetector", exitValue, name, word);
    }

    /**
     * @param listPath
     * @param name
     * @param word
     */
    protected static void normFeat(String listPath, String name, String word) {
        String[] argsNormFeat = { listPath };
        int exitValue = RunSH.run(NORM_FEAT_SH_PATH, argsNormFeat);
        handleErrorProgram("normFeat", exitValue, name, word);
    }

    private static void resetModel() {
        if (!tryToResetWorldLstFile()) {
            return;
        }
        clearDirectory(new File(GMM_PATH));
    }

    /**
     * Tries to reset the lst file of the model.
     * 
     * This file should exist.
     * 
     * @return false if there is a problem while handling the lst file
     */
    private static boolean tryToResetWorldLstFile() {
        return tryToResetLstFile(LIST_LST_WORLD_PATH);
    }

    /**
     * Try to update the data of the lst file with the data of all
     * parameters.
     * 
     * It will append all the names of the files without extension to the lst files.
     * 
     * @return false if there is a problem with the file of the user's word
     */
    private static boolean tryToUpdateWorldLstFile() {
        File dataDirectory = new File(PRM_PATH + "/");
        if (!dataDirectory.exists()) {
            return false;
        }
        List<File> normPRMList = AudioFileManager.getFilesVerifyingPredicate(dataDirectory,
                ModelManager::isNormPRMFile);
        return tryToAddWorldLstFile(normPRMList);
    }

    /**
     * Returns {@code true} if the file is a .norm.prm file.
     * 
     * In our case, we only want to update it with parameters which exists.
     * 
     * @param file
     * @return
     */
    private static boolean isNormPRMFile(File file) {
        return file.isFile() && file.getName().endsWith(".norm.prm");
    }

    /**
     * Appends the name of the files to the lst file of the model.
     * 
     * @param normPRMList List of normPRM files
     * @return false if there is a problem with the file of the user's word.
     */
    private static boolean tryToAddWorldLstFile(List<File> normPRMList) {
        return tryToAddListToLstFile(normPRMList, LIST_LST_WORLD_PATH);
    }

    private static void trainWorld() {
        String[] argsTrainWorld = {};
        int exitValue = RunSH.run(TRAIN_WORLD_SH_PATH, argsTrainWorld);
        handleErrorProgram("trainWorld", exitValue);
    }

    /**
     * Create the model of all users in the list.
     * 
     * @param users
     */
    private static void createModelOfAllUsers(List<User> users) {
        for (User u : users) {
            for (String recordedWord : WordsToRecord.getWordsToRecord()) {
                if (!doesUserHaveDataOfWord(u, recordedWord)) {
                    continue;
                }
                trainTarget(u.modelPath() + recordedWord + LIST_NDX_PATH, u.getFirstName(), recordedWord);
            }
        }
    }

    /**
     * @param listPath
     * @param name
     * @param word
     */
    private static void trainTarget(String listPath, String name, String word) {
        String[] argsNormFeat = { listPath };
        int exitValue = RunSH.run(TRAIN_TARGET_SH_PATH, argsNormFeat);
        handleErrorProgram("trainTarget", exitValue, name, word);
    }

    protected static void resetParameter() {
        clearDirectory(new File(PRM_PATH));
        clearDirectory(new File(LBL_PATH));
    }

    /**
     * Delete all files of a directory
     * 
     * @param directory
     */
    private static void clearDirectory(File directory) {
        for (File file : directory.listFiles()) {
            if (file.exists() && file.isDirectory() && file.canWrite()) {
                clearDirectory(file);
            }
            file.delete();
        }
    }

    /**
     * Handle the error of modeling programs with arguments
     * 
     * @param program
     * @param exitValue
     * @param userName
     * @param recordedWord
     */
    protected static void handleErrorProgram(String program, int exitValue, String userName, String recordedWord) {
        if (exitValue != 0) {
            printErrorRun(program, userName, recordedWord);
        }
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
                + "\nThe problem happens when the program tries to model everything\""
                + "\nMaybe try to update the audio of this word"
                + "(The audio must not be empty or too short)");
    }

    /**
     * Print the error message of the program
     * 
     * @param program
     * @param userName
     * @param recordedWord
     */
    protected static void printErrorRun(String program, String userName, String recordedWord) {
        System.out.println("There is a problem with the program : " + program
                + "\nThe problem happens when the program tries to model the word \"" + recordedWord
                + "\" from the user " + userName + "\nMaybe try to update the audio of this word"
                + "(The audio must not be empty or too short)");
    }
}