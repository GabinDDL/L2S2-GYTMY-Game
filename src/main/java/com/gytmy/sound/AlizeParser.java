package com.gytmy.sound;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class AlizeParser {

    private AlizeParser() {
    }

    public static class AlizeResult {
        public static final AlizeResult DEFAULT_ALIZE_RESULT = new AlizeResult();
        private String name;
        private String word;
        private double value;

        public AlizeResult() {
            this.name = "";
            this.word = "";
            this.value = Double.NEGATIVE_INFINITY;
        }

        public AlizeResult(String name, String word, double value) {
            this.name = name;
            this.word = word;
            this.value = value;
        }

        public void update(String name, String word, double value) {
            this.name = name;
            this.word = word;
            this.value = value;
        }

        public AlizeResult copy() {
            return new AlizeResult(name, word, value);
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Word: " + word + ", Value: " + value;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof AlizeResult)) {
                return false;
            }
            AlizeResult other = (AlizeResult) o;
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
    public static AlizeResult parseFile(File inputFile) {

        AlizeResult result = new AlizeResult();

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