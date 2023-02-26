package com.gytmy.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class HelpWindow {

    private HelpWindow() {
    }

    public static void showHelp(JComponent component, String windowTitle) {
        String message = getHelpMessage(windowTitle);
        JOptionPane.showMessageDialog(component, message);
    }

    private static String getHelpMessage(String fileName) {
        Path filePath = Path.of(fileName);
        String content = "";
        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            System.out.println("Error while reading the file: " + fileName);
        }

        return content;
    }
}
