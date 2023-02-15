package com.gytmy.launcher;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.gytmy.utils.Toolbox;

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
    String className = this.getClass().getSimpleName();
    Toolbox.frameUpdate(frame, className);
    frame.setVisible(true);
    frame.setResizable(false);
  }

}
