package com.gytmy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.io.InputStreamReader;

public class RunSH {

    public static int run(String path, String[] args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(path);
            processBuilder.command().addAll(Arrays.asList(args));
            Process process = processBuilder.start();

            // Read output from the process if needed
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();

            return exitCode;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
