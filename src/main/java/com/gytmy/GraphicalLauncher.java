package com.gytmy;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class GraphicalLauncher implements Runnable {

    public static void main(String[] args) {
        EventQueue.invokeLater(new GraphicalLauncher());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StartMenu menu = new StartMenu(frame);
        frame.add(menu);
        // TODO: Refactor frame packing-centering-revalidation in one method in a
        // tooblox class
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Be AMazed (StartMenu)");
    }

}
