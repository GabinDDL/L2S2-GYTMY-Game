package com.gytmy.maze.view.game;

import java.awt.Color;

public enum GameplayStatus {
    COUNTDOWN("BE READY!"),
    PLAYING("PLAYING"),
    RECORDING("RECORDING..."),
    COMPARING("COMPARING...");

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

    public static GameplayStatus getStatusAccordingToGameplay(boolean hasCountdownEnded, boolean isAudioRecorded,
            boolean isRecordingEnabled) {

        if (!hasCountdownEnded) {
            return COUNTDOWN;
        }

        if (isAudioRecorded) {
            return RECORDING;
        }

        if (isRecordingEnabled) {
            return PLAYING;
        }

        return COMPARING;
    }

    public Color getBackgroundColor() {
        switch (this) {
            case COUNTDOWN:
                return Color.decode("#FEBE8C");
            case RECORDING:
                return Color.decode("#F7A4A4");
            case COMPARING:
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
}
