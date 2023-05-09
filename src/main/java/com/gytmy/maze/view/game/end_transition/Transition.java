package com.gytmy.maze.view.game.end_transition;

import javax.swing.JComponent;

public class Transition {

    private GlassPaneWrapper glassPane;

    public Transition(JComponent startComponent, JComponent endComponent, Runnable endFunction) {
        start(startComponent, endComponent, endFunction);
    }

    public void start(JComponent startComponent, JComponent endComponent, Runnable endFunction) {
        glassPane = new GlassPaneWrapper(startComponent, endComponent, endFunction);
    }

    public GlassPaneWrapper getGlassPane() {
        return glassPane;
    }
}