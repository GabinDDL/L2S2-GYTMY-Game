package com.gytmy;

import java.awt.EventQueue;

import com.gytmy.launcher.GraphicalLauncher;
import com.gytmy.sound.whisper.Whisper;

public class Main {

    public static void main(String[] args) {

        // Run Whisper
        Whisper whisper = new Whisper(true, Whisper.Model.TINY_EN);
        String text = whisper.run("src/test/resources/audio", "yesmymaster", "src/test/resources/json");
        System.out.println(text);
        
        EventQueue.invokeLater(new GraphicalLauncher());

    }

}
