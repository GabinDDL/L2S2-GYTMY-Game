package com.gytmy;

import java.awt.EventQueue;

import com.gytmy.launcher.GraphicalLauncher;
import com.gytmy.sound.ModelManager;
import com.gytmy.sound.User;
import com.gytmy.sound.YamlReader;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(new GraphicalLauncher());

        // User user = YamlReader.read("src/resources/audioFiles/YANIS/config.yaml");
        // ModelManager.createModelOfRecordedWord(user, "BAS");

    }

}
