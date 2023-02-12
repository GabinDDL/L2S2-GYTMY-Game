package com.gytmy;

import javax.swing.JFrame;

public class Toolbox {

  public static void frameUpdate(JFrame frame, String title) {
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.revalidate();
    frame.setTitle(title);
  }

}
