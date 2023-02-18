package com.gytmy.utils;

import java.util.ArrayList;

public enum WordsToRecord {

    HAUT,
    BAS,
    GAUCHE,
    DROITE;

    public static ArrayList<String> getWordsToRecord() {
        ArrayList<String> values = new ArrayList<String>();

        for (WordsToRecord word : WordsToRecord.values()) {
            values.add(word.name());
        }

        return values;
    }

    public static boolean exists(String word) {
        return getWordsToRecord().contains(word);
    }
}