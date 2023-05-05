package com.gytmy.maze.view.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gytmy.maze.controller.MazeController;
import com.gytmy.maze.model.Direction;
import com.gytmy.maze.model.MazeModel;
import com.gytmy.maze.model.gamemode.GameMode;
import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.view.TimerPanel;

public class MazeBlackoutView extends MazeViewImplementation {

    private MazeController controller;

    private JPanel gamePanel;
    private BlackoutMazePanel blackoutPanel;

    private JPanel audioRecordPanel;
    private JLabel audioRecordStatus;
    private JPanel keyboardPanel;
    private JLabel keyboardMovement;

    private boolean isFlashing = false;
    private Thread flashThread;

    public static final Color BLACKOUT_COLOR = Cell.WALL_COLOR;
    private static final int BLACKOUT_INITIAL_COUNTDOWN_SECONDS = 3;
    private static final int FLASH_INTERVAL_SECONDS = 20;
    private static final int FLASH_DURATION_SECONDS = 3;

    public MazeBlackoutView(MazeModel model, JFrame frame, MazeController controller) {
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

        gamePanel = new JPanel();
        gamePanel.setBackground(BLACKOUT_COLOR);
        c.gridx = 0;
        c.gridy = 1;
        add(gamePanel, c);

        blackoutPanel = new BlackoutMazePanel(mazePanel);
        blackoutPanel.update(model.getPlayers()[0]);
        gamePanel.add(mazePanel);

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

        timerPanel = new TimerPanel(BLACKOUT_INITIAL_COUNTDOWN_SECONDS, controller);
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
                gamePanel.remove(mazePanel);
                gamePanel.add(blackoutPanel);
                break;
            case GAME:
                gamePanel.remove(blackoutPanel);
                gamePanel.add(mazePanel);
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

    @Override
    public void toggleKeyboardMovement(boolean enabled) {
        keyboardMovement.setIcon(enabled ? ENABLED_KEYBOARD_MOVEMENT_ICON : DISABLED_KEYBOARD_MOVEMENT_ICON);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.BLACKOUT;
    }

    @Override
    public JPanel getKeyboardMovementSwitchPanel() {
        return keyboardPanel;
    }
}
