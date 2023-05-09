package com.gytmy.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpWindow {

    private HelpWindow() {
    }

    public static void showHelp(JComponent component, String windowTitle, boolean scrollBar) {
        String message = getHelpMessage(windowTitle);

        Object toDisplay;

        if (scrollBar) {
            toDisplay = createJScrollPane(message);
        } else {
            toDisplay = message;
        }

        JOptionPane.showMessageDialog(component, toDisplay);
    }

    private static Object createJScrollPane(String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        return scrollPane;
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
