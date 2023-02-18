package com.gytmy.utils;

import java.util.ArrayList;

public class WordsToRecord {

    private enum Words {
        HAUT,
        BAS,
        GAUCHE,
        DROITE
    }

    public static ArrayList<String> getWordsToRecord() {
        ArrayList<String> values = new ArrayList<String>();

        for (Words word : Words.values()) {
            values.add(word.name());
        }

        return values;
    }
}