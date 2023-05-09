package com.gytmy;

import java.awt.EventQueue;

import com.gytmy.launcher.GraphicalLauncher;
import com.gytmy.utils.ThreadedQueue;

public class Main {
    public static void main(String[] args) {

        ThreadedQueue.initialize();

        EventQueue.invokeLater(new GraphicalLauncher());
    }
}
