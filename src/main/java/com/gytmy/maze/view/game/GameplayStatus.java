package com.gytmy.maze.view.game;

import javax.swing.Icon;

import com.gytmy.utils.ImageManipulator;

public enum GameplayStatus {
    // TODO: add audio record icons paths
    COUNTDOWN("TIMER-ICON"),
    PLAYING("NO-ICON"),
    RECORDING("MIC-ENABLED-ICON"),
    RECOGNIZING("MIC-DISABLED-ICON");

    private static final int ICON_WIDTH = MazeViewImplementation.ICON_WIDTH;
    private static final int ICON_HEIGHT = MazeViewImplementation.ICON_HEIGHT;
    private final String iconPath;
    private final Icon icon;

    private GameplayStatus(String iconPath) {
        this.iconPath = iconPath;
        this.icon = ImageManipulator.resizeImage(iconPath, ICON_WIDTH, ICON_HEIGHT);
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
