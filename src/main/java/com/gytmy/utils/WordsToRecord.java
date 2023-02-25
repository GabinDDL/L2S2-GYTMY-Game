package com.gytmy.utils;

import java.util.ArrayList;
import java.util.List;

public enum WordsToRecord {

    HAUT,
    BAS,
    GAUCHE,
    DROITE;

    public static List<String> getWordsToRecord() {
        List<String> values = new ArrayList<>();

        for (WordsToRecord word : WordsToRecord.values()) {
            values.add(word.name());
        }

        return values;
    }

    public static boolean exists(String word) {
        return getWordsToRecord().contains(word);
    }
}