package com.gytmy.labyrinth.view.game;

import java.awt.Color;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gytmy.labyrinth.controller.LabyrinthController;
import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.view.TimerPanel;

//TODO: Show labyrinth at the end of the game

public class LabyrinthBlackoutView extends LabyrinthViewImplementation {

    private LabyrinthController controller;

    private JPanel gamePanel;

    private BlackoutLabyrinthPanel blackoutPanel;

    private boolean isFlashing = false;

    public static final Color BLACKOUT_COLOR = Cell.WALL_COLOR;
    private static final int BLACKOUT_INITIAL_COUNTDOWN_SECONDS = 3; // TODO: Make the right configuration
    private static final int FLASH_INTERVAL_SECONDS = 20;
    private static final int FLASH_DURATION_SECONDS = 5;

    public LabyrinthBlackoutView(LabyrinthModel model, JFrame frame, LabyrinthController controller) {
        super(model, frame);
        this.controller = controller;
        initComponents();

    }

    private void initComponents() {
        GridBagConstraints c = new GridBagConstraints();
        timerPanel = new TimerPanel(BLACKOUT_INITIAL_COUNTDOWN_SECONDS, controller);
        c.gridx = 0;
        c.gridy = 0;
        add(timerPanel, c);
        startTimer();

        gamePanel = new JPanel();
        gamePanel.setBackground(BLACKOUT_COLOR);
        c.gridx = 0;
        c.gridy = 1;
        add(gamePanel, c);

        blackoutPanel = new BlackoutLabyrinthPanel(labyrinthPanel);

        gamePanel.add(labyrinthPanel);
    }

    @Override
    public void notifyGameStarted() {
        super.notifyGameStarted();
        switchToBlackout();
        startFlash();
    }

    public void startFlash() {
        isFlashing = true;
        // This thread is used to count the time between flashes
        new Thread(() -> {
            while (isFlashing) {
                try {
                    Thread.sleep(FLASH_INTERVAL_SECONDS * 1000);
                    flash();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void flash() {
        switchToGame();
        // The flash is done in a new thread to avoid blocking the main thread
        new Thread(() -> {
            try {
                Thread.sleep(FLASH_DURATION_SECONDS * 1000);
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

    public void stopFlash() {
        isFlashing = false;
    }

    @Override
    public void update(Player player, Direction direction) {
        super.update(player, direction);
        blackoutPanel.update(player);

        revalidate();
        repaint();
    }

    @Override
    public void notifyGameOver() {
        stopFlash();
        switchToGame();
        super.notifyGameOver();
    }

}
