package com.gytmy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class RunSH {

    private RunSH() {
    }

    public static int run(String path, String[] args) {
        ArrayList<String> command = new ArrayList<>();
        command.add(path);

        command.addAll(Arrays.asList(args));

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            while (line != null) { // Clean the BufferReader
                line = reader.readLine();
            }
            reader.close();
            return process.waitFor(); // Exit code

        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 1;
        }

    }
}
