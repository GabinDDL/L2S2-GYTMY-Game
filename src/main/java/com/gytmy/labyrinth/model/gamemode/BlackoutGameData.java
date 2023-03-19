package com.gytmy.labyrinth.model.gamemode;

public class BlackoutGameData implements GameModeData {

    public enum Difficulty {
        EASY(7), MEDIUM(11), HARD(15);

        private int size;

        Difficulty(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

    }

    private Difficulty difficulty;

    public BlackoutGameData(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

}
