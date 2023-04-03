package com.gytmy.sound;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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

        public static final String PRM_PATH = EXE_PATH + "prm/";
        public static final String LBL_PATH = EXE_PATH + "lbl/";

        public static final String LST_PATH = "/lst/";
        public static final String LIST_PATH = LST_PATH + "List.lst";
        public static final String GMM_PATH = "/gmm/";

        public static boolean tryToCreateModelDirectoryOfWord(User user, String word) {
                File userModelDirectory = new File(user.modelPath());

                if (!userModelDirectory.exists()) {
                        userModelDirectory.mkdir();
                }

                if (!AudioFileManager.doesFileInDirectoryExist(userModelDirectory, user.modelPath() + word)) {
                        new File(user.modelPath() + word).mkdir();

                        File userLstDirectory = new File(user.modelPath() + word + LST_PATH);
                        userLstDirectory.mkdir();

                        File userGmmDirectory = new File(user.modelPath() + word + GMM_PATH);
                        userGmmDirectory.mkdir();

                        return true;
                }
                return false;
        }

        /**
         * Creates all the models for the given users
         * 
         * @param firstNameOfUsers
         */
        public static void createAllModelOfUsers(String[] firstNameOfUsers) {
                if (firstNameOfUsers == null) {
                        return;
                }

                for (String firstName : firstNameOfUsers) {
                        User user = YamlReader.read(AUDIO_FILES_PATH + firstName + "/config.yaml");

                        if (user.getUpToDate()) {
                                createAllModelOfRecordedWord(user);

                                user.setUpToDate(false);
                                YamlReader.write(user.yamlConfigPath(), user);
                        }
                }
        }

        /**
         * Creates a model for every word to record from the user's data
         * 
         * @param user
         */
        public static void createAllModelOfRecordedWord(User user) {
                for (String word : WordsToRecord.getWordsToRecord()) {
                        createModelOfRecordedWord(user, word);
                }

                user.setUpToDate(false);
                YamlReader.write(user.yamlConfigPath(), user);
        }

        /**
         * Verifies that the word exists in order to reset the world and init
         * the lstFiles
         * 
         * @param user
         * @param recordedWord
         * @return
         */
        private static boolean tryToInitModelOfRecordedWord(User user, String recordedWord) {
                if (!WordsToRecord.exists(recordedWord)) {
                        return false;
                }

                if (!doesAudioFilesHaveAGoodLength(user, recordedWord)) {
                        return false;
                }

                resetWorldOfWord(user, recordedWord);
                return tryToInitLstFile(user, recordedWord);
        }

        /**
         * @param user
         * @param recordedWord
         * @return true if the total length of audios word is upper to upperLength
         */
        public static boolean doesAudioFilesHaveAGoodLength(User user, String recordedWord) {
                double upperLength = 3.5;

                File dataDirectory = new File(user.audioPath() + recordedWord + "/");
                List<File> list = AudioFileManager.getFilesVerifyingPredicate(dataDirectory, ModelManager::isAudioFile);

                return AudioFileManager.getTotalOfAudiosLength(list) >= upperLength;
        }

        /**
         * If it can, create the model :
         * 
         * Init the model with the update of lst and reset if
         * 
         * @param user
         * @param recordedWord
         */
        public static void createModelOfRecordedWord(User user, String recordedWord) {
                if (!doesUserHaveDataOfWord(user, recordedWord) || !tryToInitModelOfRecordedWord(user, recordedWord)) {
                        return;
                }

                if (!tryToUpdateLstFile(user, recordedWord)) {
                        return;
                }

                parametrize(user, recordedWord);
                energyDetector(user, recordedWord);
                normFeat(user, recordedWord);
                trainWorld(user, recordedWord);

                resetModeler();
        }

        /**
         * @param user
         * @param recordedWord
         * @return true if the user's word file exists
         */
        public static boolean doesUserHaveDataOfWord(User user, String recordedWord) {
                return new File(user.audioPath() + recordedWord + "/").exists();
        }

        /**
         * Try to create the new file if it doesn't exist
         * Try to reset if it does
         * 
         * @param user
         * @param recordedWord
         * @return false if there is an error with the initialisation
         */
        public static boolean tryToInitLstFile(User user, String recordedWord) {
                if (!WordsToRecord.exists(recordedWord)) {
                        return false;
                }
                File lstFile = new File(user.modelPath() + recordedWord + LIST_PATH);
                try {
                        if (!lstFile.exists()) {
                                return lstFile.createNewFile();
                        } else {
                                return tryToResetLstFile(user, recordedWord);
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return false;
        }

        /**
         * Try to clear the lst file (it should exist)
         * 
         * @param user
         * @param recordedWord
         * @return false if there is a problem with the file of the word of the user
         */
        public static boolean tryToResetLstFile(User user, String recordedWord) {
                try {
                        FileWriter writer = new FileWriter(user.modelPath() + recordedWord + LIST_PATH, false);
                        writer.append("");
                        writer.close();
                        return true;
                } catch (IOException e) {
                        return false;
                }
        }

        /**
         * Try to update the data of lst file with the data of the recordedWord of the
         * User
         * (append all the files name without extension in the lst file)
         * 
         * @param user
         * @param recordedWord
         * @return false if there is a problem with the file of the user's word
         */
        public static boolean tryToUpdateLstFile(User user, String recordedWord) {
                File dataDirectory = new File(user.audioPath() + recordedWord + "/");

                if (!dataDirectory.exists()) {
                        return false;
                }
                List<File> list = AudioFileManager.getFilesVerifyingPredicate(dataDirectory, ModelManager::isAudioFile);

                return tryToAddAudiosToLst(user, recordedWord, list);
        }

        /**
         * An audio file, in our project, is a .wav file
         * 
         * @param file
         * @return
         */
        private static boolean isAudioFile(File file) {
                return file.isFile() && file.getName().endsWith(".wav");
        }

        /**
         * Append all the audio files name into the lst file
         * 
         * @param user
         * @param recordedWord
         * @param audioList    List of audio files
         * @return
         */
        public static boolean tryToAddAudiosToLst(User user, String recordedWord, List<File> audioList) {
                try {
                        FileWriter writer = new FileWriter(user.modelPath() + recordedWord + "/lst/List.lst", true);

                        for (File file : audioList) {
                                writer.append(getNameOfFileWithoutExtension(file) + "\n");
                        }
                        writer.close();
                        return true;
                } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                }
        }

        /**
         * Give the name of the file without the exemple :
         * for exemple with a file named "test.wav", return "test"
         * 
         * @param file
         * @return
         */
        private static String getNameOfFileWithoutExtension(File file) {
                for (int i = 0; i < file.getName().length(); ++i) {
                        if (file.getName().charAt(i) == '.') {
                                return file.getName().substring(0, i);
                        }
                }
                return file.getName();
        }

        /**
         * @param user
         * @param recordedWord
         */
        private static void parametrize(User user, String recordedWord) {
                String[] argsParametrize = { user.modelPath() + recordedWord + LIST_PATH,
                                user.audioPath() + recordedWord + "/" };

                int exitValue = RunSH.run(PARAMETRIZE_SH_PATH, argsParametrize);
                handleErrorProgram("parametrize (sfbcep)", exitValue, user.getFirstName(), recordedWord);
        }

        /**
         * @param user
         * @param recordedWord
         */
        private static void energyDetector(User user, String recordedWord) {
                String[] argsEnergyDetector = {
                                user.modelPath() + recordedWord + LIST_PATH
                };

                int exitValue = RunSH.run(ENERGY_DETECTOR_SH_PATH, argsEnergyDetector);
                handleErrorProgram("energyDetector", exitValue, user.getFirstName(), recordedWord);
        }

        /**
         * @param user
         * @param recordedWord
         */
        private static void normFeat(User user, String recordedWord) {
                String[] argsNormFeat = {
                                user.modelPath() + recordedWord + LIST_PATH
                };

                int exitValue = RunSH.run(NORM_FEAT_SH_PATH, argsNormFeat);
                handleErrorProgram("normFeat", exitValue, user.getFirstName(), recordedWord);
        }

        /**
         * @param user
         * @param recordedWord
         */
        private static void trainWorld(User user, String recordedWord) {
                String[] argsTrainWorld = {
                                user.modelPath() + recordedWord + LIST_PATH,
                                user.modelPath() + recordedWord + GMM_PATH
                };

                int exitValue = RunSH.run(TRAIN_WORLD_SH_PATH, argsTrainWorld);
                handleErrorProgram("trainWorld", exitValue, user.getFirstName(), recordedWord);
        }

        /**
         * Handle the error of modeling programs
         * 
         * @param program
         * @param exitValue
         * @param userName
         * @param recoredWord
         */
        private static void handleErrorProgram(String program, int exitValue, String userName, String recoredWord) {
                if (exitValue != 0) {
                        printErrorRun(program, userName, recoredWord);
                }
        }

        /**
         * Print the error message of the program
         * 
         * @param program
         * @param userName
         * @param recordedWord
         */
        private static void printErrorRun(String program, String userName, String recordedWord) {
                System.out.println("There is a problem with the program : " + program +
                                "\nThe problem happens when the program try to modeling " + recordedWord
                                + " of the user " + userName +
                                "\nMaybe try to update the audio of this word" +
                                "(there must be no empty or too short audio)");
        }

        private static void resetModeler() {
                clearDirectory(new File(PRM_PATH));
                clearDirectory(new File(LBL_PATH));
        }

        /**
         * Reset the gmm file of a user's word
         * 
         * @param user
         * @param word
         */
        private static void resetWorldOfWord(User user, String word) {
                if (WordsToRecord.exists(word) &&
                                new File(user.modelPath() + word + "/").exists()) {
                        clearDirectory(new File(user.modelPath() + word + GMM_PATH));
                }
        }

        /**
         * Delete all files of a directory
         * 
         * @param directory
         */
        private static void clearDirectory(File directory) {
                for (File file : directory.listFiles()) {
                        if (file.exists() && file.isDirectory() && file.canWrite())
                                clearDirectory(file);
                        file.delete();
                }
        }
}