package com.gytmy.maze.view.game;

import javax.swing.Icon;

import com.gytmy.utils.ImageManipulator;

public enum GameplayStatus {
    COUNTDOWN("src/resources/images/game/timer_on.png", 27, 33),
    PLAYING("src/resources/images/game/mic_off.png", 21, 33),
    RECORDING("src/resources/images/game/mic_on.png", 21, 33),
    RECOGNIZING("src/resources/images/game/computing_on.png", 30, 33);

    private final String iconPath;
    private final Icon icon;

    private GameplayStatus(String iconPath, int iconWidth, int iconHeight) {
        this.iconPath = iconPath;

        if (iconPath == null) {
            this.icon = null;
            return;
        }

        this.icon = ImageManipulator.resizeImage(iconPath, iconWidth, iconHeight);
    }

    public String getIconPath() {
        return iconPath;
    }

    public Icon getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public static GameplayStatus getStatusAccordingToGameplay(boolean hasCountdownEnded, boolean isAudioRecording,
            boolean isRecordingEnabled) {

        if (!hasCountdownEnded) {
            return COUNTDOWN;
        }

        if (isAudioRecording) {
            return RECORDING;
        }

        if (isRecordingEnabled) {
            return PLAYING;
        }

        return RECOGNIZING;
    }
}
