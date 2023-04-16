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

                if (!AudioFileManager.doesFileExistInDirectory(userModelDirectory, user.modelPath() + word)) {
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

                        if (!user.getUpToDate()) {
                                createAllModelOfRecordedWord(user);
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

                user.setUpToDate(true);
                YamlReader.write(user.yamlConfigPath(), user);
        }

        /**
         * Tries to init the lst file of the user's word. If the file doesn't exist, it
         * creates it. If it exists, it resets it.
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
         * @return true if the total length of the audio files for an specific word is
         *         greater than {@code upperDuration}(3.5 seconds)
         */
        public static boolean doesAudioFilesHaveAGoodLength(User user, String recordedWord) {
                double upperDuration = 3.5;

                File dataDirectory = new File(user.audioPath() + recordedWord + "/");
                List<File> list = AudioFileManager.getFilesVerifyingPredicate(dataDirectory, ModelManager::isAudioFile);

                return FileInformationFinder.getAudioLength(list) > upperDuration;
        }

        /**
         * Initializes the model with the updated lst if it is possible. Then it will
         * apply the modeling and end by resetting the modeler.
         * 
         * (the modeler is the repertory used to create one model, it needs to be
         * cleared after each model creation)
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
         * Tries to reset the lst file of the user's word. This file should exist.
         * 
         * @param user
         * @param recordedWord
         * @return false if there is a problem while handling the user's word file
         * 
         */
        public static boolean tryToResetLstFile(User user, String recordedWord) {
                try (FileWriter writer = new FileWriter(user.modelPath() + recordedWord + LIST_PATH, false);) {
                        writer.append("");
                        return true;
                } catch (IOException e) {
                        return false;
                }
        }

        /**
         * Try to update the data of the lst file with the data of the
         * {@code recordedWord} of the {@code User}. It will append all the names of the
         * files without extension to the lst file.
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
         * Returns {@code true} if the file is a .wav file. In our case, we only want to
         * record .wav files, any other file will be ignored.
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
        public static boolean tryToAddAudiosToLst(User user, String recordedWord, List<File> audioList) {
                try (FileWriter writer = new FileWriter(user.modelPath() + recordedWord + "/lst/List.lst", true);) {
                        for (File file : audioList) {
                                writer.append(getFileBasename(file) + "\n");
                        }
                        return true;
                } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                }
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
         * @param recordedWord
         */
        private static void handleErrorProgram(String program, int exitValue, String userName, String recordedWord) {
                if (exitValue != 0) {
                        printErrorRun(program, userName, recordedWord);
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
                System.out.println("There is a problem with the program : " + program
                                + "\nThe problem happens when the program tries modeling the word \"" + recordedWord
                                + "\" from the user " + userName + "\nMaybe try to update the audio of this word"
                                + "(The audio must not be empty or too short)");
        }

        protected static void resetModeler() {
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
                        if (file.exists() && file.isDirectory() && file.canWrite()) {
                                clearDirectory(file);
                        }
                        file.delete();
                }
        }

        protected static void createPrmOfCurrentAudio() {
                resetModeler();
                parametrizeClient();
                energyDetectorClient();
                normFeatClient();
        }

        /**
         * @param user
         * @param recordedWord
         */
        private static void parametrizeClient() {
                String[] argsParametrize = { AudioRecognitionResult.CLIENT_LST_LIST_PATH,
                                AudioRecognitionResult.CLIENT_AUDIO_PATH };

                int exitValue = RunSH.run(PARAMETRIZE_SH_PATH, argsParametrize);
                handleErrorProgram("parametrize (sfbcep)", exitValue);
        }

        /**
         * @param user
         * @param recordedWord
         */
        private static void energyDetectorClient() {
                String[] argsEnergyDetector = {
                                AudioRecognitionResult.CLIENT_LST_LIST_PATH
                };

                int exitValue = RunSH.run(ENERGY_DETECTOR_SH_PATH, argsEnergyDetector);
                handleErrorProgram("energyDetector", exitValue);
        }

        /**
         * @param user
         * @param recordedWord
         */
        private static void normFeatClient() {
                String[] argsNormFeat = {
                                AudioRecognitionResult.CLIENT_LST_LIST_PATH
                };

                int exitValue = RunSH.run(NORM_FEAT_SH_PATH, argsNormFeat);
                handleErrorProgram("normFeat", exitValue);
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
                                + "\nThe problem happens when the program tries to model everything\""
                                + "\nMaybe try to update the audio of this word"
                                + "(The audio must not be empty or too short)");
        }
}