package com.gytmy;

import java.awt.EventQueue;

import com.gytmy.launcher.GraphicalLauncher;
import com.gytmy.whisper.Whisper;

public class Main {

    public static void main(String[] args) {

        // TODO: REMOVE THIS
        Whisper whisper = new Whisper(true, Whisper.Model.SMALL_EN);
        String text = whisper.run("src/resources/audioFiles/MATHUSAN/audio/UP", "UP1", "src/resources/");
        System.out.println(text);
        // -----------------
        
        EventQueue.invokeLater(new GraphicalLauncher());

    }

}
