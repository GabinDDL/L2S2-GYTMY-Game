package com.gytmy.maze.view.game;

import java.awt.Color;

import javax.swing.Icon;

public enum GameplayStatus {
    COUNTDOWN("BE READY!"),
    PLAYING("PLAYING"),
    RECORDING("RECORDING..."),
    RECOGNIZING("RECOGNIZING...");

    private final String displayName;
    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    private static final Color FOREGROUND_COLOR = Color.WHITE;

    private GameplayStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
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

    public Color getBackgroundColor() {
        switch (this) {
            case COUNTDOWN:
                return Color.decode("#FEBE8C");
            case RECORDING:
                return Color.decode("#F7A4A4");
            case RECOGNIZING:
                return Color.decode("#A6D0DD");
            default:
                return BACKGROUND_COLOR;
        }
    }

    public Color getTextColor() {
        switch (this) {
            case PLAYING:
                return FOREGROUND_COLOR;
            default:
                return Color.DARK_GRAY;
        }
    }

    public Icon getIcon() {
        return null;
    }
}
