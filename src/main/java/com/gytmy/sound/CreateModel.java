package com.gytmy.sound;

import com.gytmy.utils.input.RunSH;

public class CreateModel {

    public static final String EXE_PATH = "src/main/exe/model/";
    public static final String PARAMETRIZE_SH_PATH = EXE_PATH + "Parametrize.sh";
    public static final String ENERGY_DETECTOR_SH_PATH = EXE_PATH + "EnergyDetector.sh";
    public static final String NORM_FEAT_SH_PATH = EXE_PATH + "NormFeat.sh";
    public static final String TRAIN_WORLD_SH_PATH = EXE_PATH + "TrainWorld.sh";

    public static void createModelRecordedWord(User user, String recordedWord) {
        String[] argsParametrize = {
                "12MFCC",
                user.audioPath() + recordedWord + "/",
                user.modelPath() + recordedWord + "/prm/" };

        String[] argsEnergyDetector = {
                user.modelPath() + recordedWord + "/lst/Liste.lst",
                user.modelPath() + recordedWord + "/prm/",
                user.modelPath() + recordedWord + "/lbl/",
                user.modelPath() + recordedWord + "/gmm/" };

        String[] argsNormFeat = {
                user.modelPath() + recordedWord + "/lst/Liste.lst",
                user.modelPath() + recordedWord + "/prm/",
                user.modelPath() + recordedWord + "/lbl/", };

        String[] argsTrainWorld = {
                user.modelPath() + recordedWord + "/lst/Liste.lst",
                user.modelPath() + recordedWord + "/prm/",
                user.modelPath() + recordedWord + "/lbl/",
                user.modelPath() + recordedWord + "/gmm/" };

        RunSH.run(PARAMETRIZE_SH_PATH, argsParametrize);
        RunSH.run(ENERGY_DETECTOR_SH_PATH, argsEnergyDetector);
        RunSH.run(NORM_FEAT_SH_PATH, argsNormFeat);
        RunSH.run(TRAIN_WORLD_SH_PATH, argsTrainWorld);
    }
}
