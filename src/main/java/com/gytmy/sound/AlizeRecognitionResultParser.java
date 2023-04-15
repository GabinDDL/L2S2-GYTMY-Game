package com.gytmy.sound;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class AlizeRecognitionResultParser {

    private AlizeRecognitionResultParser() {
    }

    public static class AlizeRecognitionResult {
        public static final AlizeRecognitionResult DEFAULT_ALIZE_RESULT = new AlizeRecognitionResult();
        private String name;
        private String word;
        private double value;

        public AlizeRecognitionResult() {
            this.name = "";
            this.word = "";
            this.value = Double.NEGATIVE_INFINITY;
        }

        public AlizeRecognitionResult(String name, String word, double value) {
            this.name = name;
            this.word = word;
            this.value = value;
        }

        public void update(String name, String word, double value) {
            this.name = name;
            this.word = word;
            this.value = value;
        }

        public AlizeRecognitionResult copy() {
            return new AlizeRecognitionResult(name, word, value);
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Word: " + word + ", Value: " + value;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof AlizeRecognitionResult)) {
                return false;
            }
            AlizeRecognitionResult other = (AlizeRecognitionResult) object;
            return this.name.equals(other.name) && this.word.equals(other.word) && this.value == other.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, word, value);
        }

        public String getName() {
            return name;
        }

        public String getWord() {
            return word;
        }

        public double getValue() {
            return value;
        }
    }

    /**
     * Parses the given file and returns the AlizeResult with the highest value.
     * The path to the file must be relative to the project root.
     */
    public static AlizeRecognitionResult parseFile(File inputFile) {

        AlizeRecognitionResult result = new AlizeRecognitionResult();

        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.isBlank()) {
                    // Skip empty lines
                    continue;
                }

                String[] parts = line.split(" ");
                String currentName = parts[1].substring(0, parts[1].lastIndexOf("_"));
                String currentWord = parts[1].substring(parts[1].lastIndexOf("_") + 1);
                double currentVal = Double.parseDouble(parts[4]);

                if (currentVal > result.value) {
                    result.update(currentName, currentWord, currentVal);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}