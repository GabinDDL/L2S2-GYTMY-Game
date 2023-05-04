package com.gytmy.maze.view.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gytmy.maze.controller.MazeController;
import com.gytmy.maze.model.MazeModel;
import com.gytmy.maze.view.TimerPanel;
import com.gytmy.utils.ImageManipulator;

public class MazeClassicView extends MazeViewImplementation {

    private MazeController controller;
    JPanel emptyPanel = new JPanel();
    JPanel keyboardPanel = new JPanel();

    public MazeClassicView(MazeModel model, JFrame frame, MazeController controller) {
        super(model, frame);
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints c = new GridBagConstraints();

        initTopPanel();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(topPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        add(mazePanel, c);
    }

    private void initTopPanel() {

        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setBorder(new EmptyBorder(20, 20, 0, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;

        // emptyPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        c.gridx = 0;
        c.weightx = 1.0;
        topPanel.add(emptyPanel, c);

        timerPanel = new TimerPanel(controller);
        // timerPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
        c.gridx = 1;
        c.weightx = 2.0;
        topPanel.add(timerPanel, c);
        startTimer();

        keyboardPanel.setLayout(new BorderLayout());
        keyboardPanel.setBackground(BACKGROUND_COLOR);
        // keyboardPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));

        JLabel keyboardMovement = new JLabel();
        keyboardMovement.setIcon(ImageManipulator.resizeImage(DISABLED_KEYBOARD_MOVEMENT, 51, 33));
        // keyboardMovement.setBorder(BorderFactory.createLineBorder(Color.PINK, 1));

        keyboardPanel.add(keyboardMovement, BorderLayout.EAST);
        keyboardPanel.setPreferredSize(
                new Dimension(emptyPanel.getPreferredSize().width, keyboardPanel.getPreferredSize().height));

        c.gridx = 2;
        c.weightx = 1.0;
        topPanel.add(keyboardPanel, c);
    }

    // @Override
    // public void notifyGameStarted() {
    // System.out.println("EmptyPanel size = " + emptyPanel.getSize());
    // System.out.println("EmptyPanel preferred size = " +
    // emptyPanel.getPreferredSize());
    // System.out.println("TimerPanel size = " + timerPanel.getSize());
    // System.out.println("Keys Panel size = " + keyboardPanel.getSize());
    // }
}
