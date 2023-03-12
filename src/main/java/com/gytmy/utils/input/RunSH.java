package com.gytmy.utils.input;

import java.io.IOException;
import java.util.Arrays;

public class RunSH {

    public static void run(String path, String[] args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(path);
            processBuilder.command().addAll(Arrays.asList(args));
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
