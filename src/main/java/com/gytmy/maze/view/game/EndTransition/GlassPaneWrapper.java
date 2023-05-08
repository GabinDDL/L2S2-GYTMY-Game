package com.gytmy.maze.view.game.EndTransition;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import com.gytmy.maze.view.MenuFrameHandler;

public class GlassPaneWrapper extends JLayeredPane implements AnimationObserver {

    private DrawShapes glassPanel;
    private JComponent startC;
    private JComponent endC;
    private Runnable endFunction;

    private boolean isEndAnimationRunned = false;

    public GlassPaneWrapper(JComponent startComponent, JComponent endComponent, Runnable endFunction) {

        this.startC = startComponent;
        this.endC = endComponent;
        this.endFunction = endFunction;

        glassPanel = new DrawShapes(startComponent.getPreferredSize());
        glassPanel.addAnimationObserver(this);

        glassPanel.setOpaque(false);
        glassPanel.setVisible(true);
        glassPanel.setFocusable(true);

        startC.setSize(startC.getPreferredSize());

        add(startC, JLayeredPane.DEFAULT_LAYER);
        add(glassPanel, JLayeredPane.PALETTE_LAYER);

        glassPanel.setPreferredSize(MenuFrameHandler.getMainFrame().getPreferredSize());
        glassPanel.setSize(MenuFrameHandler.getMainFrame().getSize());

        setPreferredSize(startC.getPreferredSize());
        setVisible(true);
    }

    @Override
    public void endAnimationUpdate() {

        if (isEndAnimationRunned) {
            endFunction.run();
            return;
        }

        isEndAnimationRunned = true;

        remove(startC);
        endC.setSize(MenuFrameHandler.DEFAULT_DIMENSION);
        endC.setVisible(true);
        add(endC, JLayeredPane.DEFAULT_LAYER);

        glassPanel.setSize(MenuFrameHandler.DEFAULT_DIMENSION);
        glassPanel.setPreferredSize(MenuFrameHandler.DEFAULT_DIMENSION);

        MenuFrameHandler.getMainFrame().setSize(MenuFrameHandler.DEFAULT_DIMENSION);
        MenuFrameHandler.getMainFrame().setPreferredSize(MenuFrameHandler.DEFAULT_DIMENSION);

        glassPanel.clearAnimation();
    }
}
