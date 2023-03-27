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

public class LabyrinthBlackoutView extends LabyrinthViewImplementation {

    private LabyrinthController controller;

    private JPanel gamePanel;

    private BlackoutLabyrinthPanel blackoutPanel;

    private boolean isFlashing = false;
    private Thread flashThread;

    public static final Color BLACKOUT_COLOR = Cell.WALL_COLOR;
    private static final int BLACKOUT_INITIAL_COUNTDOWN_SECONDS = 3;
    private static final int FLASH_INTERVAL_SECONDS = 20;
    private static final int FLASH_DURATION_SECONDS = 3;

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
        blackoutPanel.update(model.getPlayers()[0]);
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
        flashThread = new Thread(() -> {
            while (isFlashing) {
                try {
                    Thread.sleep(FLASH_INTERVAL_SECONDS * 1000);
                    flash();
                } catch (InterruptedException e) {
                    // We don't care about this exception because we interrupt the thread when the
                    // game is over
                }
            }
        });
        flashThread.start();
    }

    private void flash() {
        switchToGame();
        // The flash is done in a new thread to avoid blocking the main thread
        new Thread(() -> {
            try {
                Thread.sleep(FLASH_DURATION_SECONDS * 1000);
                switchToBlackout();
            } catch (InterruptedException e) {
                // We don't care about this exception because we interrupt the thread when the
                // game is over
            }
        }).start();
    }

    private enum ViewType {
        GAME, BLACKOUT
    }

    private void switchToBlackout() {
        switchView(ViewType.BLACKOUT);
    }

    private void switchToGame() {
        switchView(ViewType.GAME);
    }

    private void switchView(ViewType viewType) {
        switch (viewType) {
            case BLACKOUT:
                gamePanel.remove(labyrinthPanel);
                gamePanel.add(blackoutPanel);
                break;
            case GAME:
                gamePanel.remove(blackoutPanel);
                gamePanel.add(labyrinthPanel);
                break;
        }
        gamePanel.revalidate();
        gamePanel.repaint();
        revalidate();
        repaint();
    }

    public void stopFlash() {
        isFlashing = false;
        flashThread.interrupt();
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
