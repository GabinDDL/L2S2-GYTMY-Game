package com.gytmy.labyrinth.view.game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gytmy.labyrinth.controller.LabyrinthController;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.view.TimerPanel;

public class LabyrinthBlackoutView extends LabyrinthViewImplementation {

    private LabyrinthController controller;

    private JPanel gamePanel;

    private JPanel blackoutPanel;

    private boolean isFlashing = false;

    private static final Color BLACKOUT_COLOR = Cell.WALL_COLOR;
    private static final int BLACKOUT_INITIAL_COUNTDOWN = 3;
    private static final int FLASH_INTERVAL = 10;
    private static final int FLASH_DURATION = 2;

    // TODO: solve unit for the time constants (BLACKOUT_INITIAL_COUNTDOWN,
    // FLASH_INTERVAL, FLASH_DURATION)

    public LabyrinthBlackoutView(LabyrinthModel model, JFrame frame, LabyrinthController controller) {
        super(model, frame);
        this.controller = controller;
        initComponents();

    }

    private void initComponents() {
        GridBagConstraints c = new GridBagConstraints();
        timerPanel = new TimerPanel(BLACKOUT_INITIAL_COUNTDOWN, controller);
        c.gridx = 0;
        c.gridy = 0;
        add(timerPanel, c);
        startTimer();

        gamePanel = new JPanel();
        gamePanel.setBackground(BLACKOUT_COLOR);
        c.gridx = 0;
        c.gridy = 1;
        add(gamePanel, c);

        blackoutPanel = new JPanel();
        blackoutPanel.setBackground(BLACKOUT_COLOR);
        blackoutPanel.setPreferredSize(labyrinthPanel.getPreferredSize());

        gamePanel.add(labyrinthPanel);
    }

    public void startFlash() {
        isFlashing = true;
        new Thread(() -> {
            while (isFlashing) {
                try {
                    Thread.sleep(FLASH_INTERVAL * 1000);
                    flash();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stopFlash() {
        isFlashing = false;
    }

    private void flash() {
        switchToGame();
        new Thread(() -> {
            try {
                Thread.sleep(FLASH_DURATION * 1000);
                switchToBlackout();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void switchToBlackout() {
        gamePanel.remove(labyrinthPanel);
        gamePanel.add(blackoutPanel);
        gamePanel.revalidate();
        gamePanel.repaint();
        this.revalidate();
        this.repaint();
    }

    private void switchToGame() {
        gamePanel.remove(blackoutPanel);
        gamePanel.add(labyrinthPanel);
        gamePanel.revalidate();
        gamePanel.repaint();
        this.revalidate();
        this.repaint();
    }

    @Override
    public void notifyGameStarted() {
        super.notifyGameStarted();
        switchToBlackout();
        startFlash();
    }

}
