package com.gytmy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunSH {

    private RunSH() {
    }

    public static int run(String path, String[] args) {
        try {
            Process process = Runtime.getRuntime().exec(getCommand(path, args));

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) { // Clean the BufferReader
                // System.out.println(line);
            }
            reader.close();

            int exitCode = process.waitFor();

            return exitCode;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static String getCommand(String path, String[] args) {
        String command = path;

        for (String arg : args) {
            command += " " + arg;
        }

        return command;
    }
}
