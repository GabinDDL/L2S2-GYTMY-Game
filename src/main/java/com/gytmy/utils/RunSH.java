package com.gytmy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;

public class RunSH {

    private RunSH() {
    }

    public static int run(String path, String[] args) {
        try {
            Process process = Runtime.getRuntime().exec(getCommand(path, args));

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            while (line != null) { // Clean the BufferReader
                line = reader.readLine();
            }
            reader.close();

            return process.waitFor(); // Exit code

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static String getCommand(String path, String[] args) {
        StringJoiner joiner = new StringJoiner(" ");

        for (String arg : args) {
            joiner.add(arg);
        }
        return path + " " + joiner.toString();
    }
}
