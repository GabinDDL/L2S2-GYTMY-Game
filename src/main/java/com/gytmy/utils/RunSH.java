package com.gytmy.utils;

import java.io.IOException;
import java.util.Arrays;

public class RunSH {

    public static int run(String path, String[] args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(path);
            processBuilder.command().addAll(Arrays.asList(args));
            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            return exitCode;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
