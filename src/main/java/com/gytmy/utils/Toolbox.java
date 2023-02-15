package com.gytmy.utils;

import javax.swing.JFrame;

public class Toolbox {

  private static final String GAME_TITLE = "Be AMazed";

  public static void frameUpdate(JFrame frame, String className) {
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.revalidate();
    frame.setTitle(GAME_TITLE + "\t(" + className + ")");
  }

}
