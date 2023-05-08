package com.gytmy.maze.view.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gytmy.maze.model.Direction;
import com.gytmy.maze.model.MazeModel;
import com.gytmy.maze.model.gamemode.GameMode;
import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.view.GameOverPanel;
import com.gytmy.maze.view.MenuFrameHandler;
import com.gytmy.maze.view.PausePanel;
import com.gytmy.maze.view.TimerPanel;
import com.gytmy.maze.view.game.EndTransition.Transition;
import com.gytmy.utils.ImageManipulator;
import com.gytmy.utils.HotkeyAdder;

public class MazeViewImplementation extends MazeView {
    protected MazeModel model;
    protected MazePanel mazePanel;
    protected TimerPanel timerPanel;
    protected JPanel topPanel;
    protected PausePanel pausePanel;
    private JFrame frame;
    private Dimension preferredSize;

    private GameOverPanel gameOverPanel;
    protected JPanel audioRecordPanel;
    protected JLabel audioRecordStatus;
    protected JPanel keyboardPanel;
    protected JLabel keyboardMovement;

    protected static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;

    protected static final String ENABLED_KEYBOARD_MOVEMENT = "src/resources/images/game/directional_arrows_enabled.png";
    protected static final String DISABLED_KEYBOARD_MOVEMENT = "src/resources/images/game/directional_arrows_disabled.png";
    protected static final int ICON_WIDTH = 51;
    protected static final int ICON_HEIGHT = 33;
    protected static final Dimension ICON_DIMENSION = new Dimension(ICON_WIDTH, ICON_HEIGHT);
    protected static final Icon ENABLED_KEYBOARD_MOVEMENT_ICON = ImageManipulator.resizeImage(
            ENABLED_KEYBOARD_MOVEMENT, ICON_WIDTH, ICON_HEIGHT);
    protected static final Icon DISABLED_KEYBOARD_MOVEMENT_ICON = ImageManipulator.resizeImage(
            DISABLED_KEYBOARD_MOVEMENT, ICON_WIDTH, ICON_HEIGHT);

    protected MazeViewImplementation(MazeModel model, JFrame frame) {
        this.model = model;
        this.frame = frame;

        this.pausePanel = PausePanel.getInstance();
        pausePanel.setMazeView(this);
        addPauseKeyBind();

        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);
        mazePanel = new MazePanel(model);
    }

    private void addPauseKeyBind() {
        HotkeyAdder.addHotkey(this, KeyEvent.VK_ESCAPE, this::showPausePanel, "Pause Panel");
    }

    private void showPausePanel() {
        stopTimer();

        frame.setContentPane(pausePanel);
        frame.setPreferredSize(MenuFrameHandler.DEFAULT_DIMENSION);
        MenuFrameHandler.frameUpdate("Take a break !");
    }

    public void startTimer() {
        timerPanel.start();
    }

    @Override
    public void stopTimer() {
        timerPanel.stop();
    }

    public int getTimerCounterInSeconds() {
        return timerPanel.getCounterInSeconds();
    }

    @Override
    public void update(Player player, Direction direction) {
        mazePanel.update(player, direction);
    }

    @Override
    public MazePanel getMazePanel() {
        return mazePanel;
    }

    @Override
    public boolean isTimerCounting() {
        return timerPanel.isCounting();
    }

    @Override
    public void showGameOverPanel() {
        frame.setContentPane(gameOverPanel);
        MenuFrameHandler.frameUpdate("Game Over");
    }

    @Override
    public void notifyGameStarted() {
        // For this view, nothing needs to be done.
    }

    @Override
    public void notifyGameOver() {

        this.gameOverPanel = new GameOverPanel(model);
        frame.setContentPane(new Transition(this, gameOverPanel, this::showGameOverPanel).getGlassPane());
    }

    @Override
    public void toggleKeyboardMovement(boolean enabled) {
        keyboardMovement.setIcon(enabled ? ENABLED_KEYBOARD_MOVEMENT_ICON : DISABLED_KEYBOARD_MOVEMENT_ICON);
    }

    public GameMode getGameMode() {
        return null;
    }

    @Override

    public Dimension getGamePreferredSize() {
        return preferredSize;
    }

    @Override
    public void setGamePreferredSize(Dimension dimension) {
        preferredSize = dimension;
    }

    @Override
    public void updateRecordStatus(GameplayStatus status) {
        audioRecordStatus.setIcon(status.getIcon());
        repaint();
    }

    @Override
    public JComponent getKeyboardMovementSwitchPanel() {
        return keyboardPanel;
    }

    @Override
    public JComponent getRecordStatusPanel() {
        return audioRecordPanel;
    }

    protected void initComponents() {
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

    protected void initTopPanel() {

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
        audioRecordStatus.setIcon(DISABLED_KEYBOARD_MOVEMENT_ICON);

        audioRecordPanel.add(audioRecordStatus, BorderLayout.WEST);

        topPanel.add(audioRecordPanel, c);
    }

    protected void initTimerPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;

        timerPanel = new TimerPanel();
        timerPanel.setBackground(BACKGROUND_COLOR);
        timerPanel.setPreferredSize(ICON_DIMENSION);

        topPanel.add(timerPanel, c);
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
}
