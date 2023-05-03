package com.gytmy.maze.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gytmy.maze.view.game.Cell;
import com.gytmy.maze.view.game.MazeView;
import com.gytmy.utils.HotkeyAdder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;

public class PausePanel extends JPanel {

    private MazeView mazeView;
    private JLabel title;
    private JButton resumeButton;
    private JButton quitButton;

    private static PausePanel instance;

    public static PausePanel getInstance() {
        if (instance == null) {
            instance = new PausePanel();
        }
        return instance;
    }

    private PausePanel() {

        setSize(MenuFrameHandler.DEFAULT_DIMENSION);
        setBackground(Cell.WALL_COLOR);
        setLayout(new GridBagLayout());

        initTitle();
        initButtons();
        addConfirmKeyBind();
        addEscapeKeyBind();

        updateGUI();
    }

    private void initTitle() {
        title = new JLabel("PAUSE");

        title.setFont(title.getFont().deriveFont(50f));
        title.setForeground(Cell.PATH_COLOR);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets.bottom = 150;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        add(title, constraints);
    }

    private void initButtons() {
        resumeButton = new JButton("Resume");
        quitButton = new JButton("Restart");

        resumeButton.addActionListener(e -> resume());
        quitButton.addActionListener(e -> MenuFrameHandler.goToSettingsMenu());

        resumeButton.setBackground(Cell.PATH_COLOR);
        resumeButton.setForeground(Cell.WALL_COLOR);

        quitButton.setBackground(Cell.PATH_COLOR);
        quitButton.setForeground(Cell.EXIT_CELL_COLOR);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 2;
        constraints.gridx = 0;
        add(resumeButton, constraints);

        constraints.gridx = 2;
        add(quitButton, constraints);
    }

    private void addConfirmKeyBind() {
        HotkeyAdder.addHotkey(this, KeyEvent.VK_SPACE, MenuFrameHandler::goToSettingsMenu, "Go to Settings Menu");
    }

    private void addEscapeKeyBind() {
        HotkeyAdder.addHotkey(this, KeyEvent.VK_ESCAPE, this::resume, "Resume Game");
    }

    private void resume() {
        JFrame frame = MenuFrameHandler.getMainFrame();

        frame.setContentPane(mazeView);
        frame.setPreferredSize(mazeView.getGamePreferredSize());

        MenuFrameHandler.frameUpdate(mazeView.getGameMode().getDisplayName());

        mazeView.startTimer();
    }

    public void setMazeView(MazeView mazeView) {
        this.mazeView = mazeView;
    }

    private void updateGUI() {
        revalidate();
        repaint();
    }
}
