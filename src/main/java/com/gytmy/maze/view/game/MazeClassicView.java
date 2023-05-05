package com.gytmy.maze.view.game;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gytmy.maze.controller.MazeController;
import com.gytmy.maze.model.MazeModel;
import com.gytmy.maze.model.gamemode.GameMode;
import com.gytmy.maze.view.TimerPanel;

public class MazeClassicView extends MazeViewImplementation {

    private MazeController controller;
    private JPanel audioRecordPanel;
    private JLabel audioRecordStatus;
    private JPanel keyboardPanel;
    private JLabel keyboardMovement;

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

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        // add(statusFeedbackPanel, c);
    }

    private void initTopPanel() {

        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setBorder(new EmptyBorder(20, 20, 0, 20));

        initAudioRecordPanel();

        initTimerPanel();

        keyboardPanel();
    }

    private void initAudioRecordPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;

        audioRecordPanel = new JPanel(new BorderLayout());
        audioRecordPanel.setBackground(BACKGROUND_COLOR);
        audioRecordPanel.setPreferredSize(ICON_DIMENSION);

        audioRecordStatus = new JLabel();
        // TODO: add audio record icon
        // audioRecordStatus.setIcon(DISABLED_AUDIO_RECORD_ICON);
        audioRecordStatus.setIcon(DISABLED_KEYBOARD_MOVEMENT_ICON);

        audioRecordPanel.add(audioRecordStatus, BorderLayout.WEST);

        topPanel.add(audioRecordPanel, c);
    }

    private void initTimerPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 2.0;
        c.fill = GridBagConstraints.HORIZONTAL;

        timerPanel = new TimerPanel(controller);
        topPanel.add(timerPanel, c);
        startTimer();
    }

    private void keyboardPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;

        keyboardPanel = new JPanel(new BorderLayout());
        keyboardPanel.setBackground(BACKGROUND_COLOR);
        keyboardPanel.setPreferredSize(ICON_DIMENSION);

        keyboardMovement = new JLabel();
        keyboardMovement.setIcon(DISABLED_KEYBOARD_MOVEMENT_ICON);

        keyboardPanel.add(keyboardMovement, BorderLayout.EAST);

        topPanel.add(keyboardPanel, c);
    }

    @Override
    public void toggleKeyboardMovement(boolean enabled) {
        keyboardMovement.setIcon(enabled ? ENABLED_KEYBOARD_MOVEMENT_ICON : DISABLED_KEYBOARD_MOVEMENT_ICON);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.CLASSIC;
    }

    @Override
    public JPanel getKeyboardMovementSwitchPanel() {
        return keyboardPanel;
    }
}
