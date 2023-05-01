package com.gytmy.maze.view.settings;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.gytmy.maze.controller.MazeController;
import com.gytmy.maze.controller.MazeControllerImplementation;
import com.gytmy.maze.model.GameData;
import com.gytmy.maze.model.gamemode.GameMode;
import com.gytmy.maze.model.gamemode.GameModeData;
import com.gytmy.maze.model.player.Player;
import com.gytmy.maze.view.MenuFrameHandler;
import com.gytmy.maze.view.game.Cell;
import com.gytmy.maze.view.game.MazeView;
import com.gytmy.maze.view.settings.gamemode.SelectionPanel;
import com.gytmy.maze.view.settings.player.PlayerSelectionPanel;
import com.gytmy.sound.ModelManager;
import com.gytmy.utils.HotkeyAdder;
import com.gytmy.utils.ImageManipulator;

/**
 * This class is used to display the settings menu. It is a singleton.
 */
public class SettingsMenu extends JPanel {

    private PlayerSelectionPanel playerSelectionPanel;
    private SelectionPanel gameModeSelectionPanel;
    private GameGIFLabel gameGifLabel;
    private JLabel startGameButton;

    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;
    private static final String START_GAME_BUTTON_IMAGE_PATH = "src/resources/images/settings_menu/StartButton.png";

    private static SettingsMenu instance = null;

    public static SettingsMenu getInstance() {
        if (instance == null) {
            instance = new SettingsMenu();
        }
        return instance;
    }

    private SettingsMenu() {

        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);

        initPlayerSelectionPanel();
        initGameGifLabel();
        initGameSelectionPanel();
        initStartGameButton();

        addEscapeKeyBind();

        updateGUI();
    }

    private void initPlayerSelectionPanel() {
        playerSelectionPanel = PlayerSelectionPanel.getInstance();
        GridBagConstraints gbc = getDefaultConstraints(0, 0);
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        add(playerSelectionPanel, gbc);
    }

    private void initGameGifLabel() {
        gameGifLabel = GameGIFLabel.getInstance();
        GridBagConstraints gbc = getDefaultConstraints(0, 1);
        gbc.gridheight = 2;
        add(gameGifLabel, gbc);
    }

    private void initGameSelectionPanel() {
        gameModeSelectionPanel = new SelectionPanel();
        GridBagConstraints gbc = getDefaultConstraints(1, 1);
        gbc.weightx = 0.7;
        gbc.insets = new Insets(20, 20, 20, 20);
        add(gameModeSelectionPanel, gbc);
    }

    private void initStartGameButton() {

        startGameButton = new JLabel();
        startGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                startGame();
            }
        });

        startGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                SettingsMenu.getInstance().setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                SettingsMenu.getInstance().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        startGameButton
                .setIcon(ImageManipulator.resizeImage(START_GAME_BUTTON_IMAGE_PATH, 128, 56));

        GridBagConstraints gbc = getStartButtonGridBagConstraints();
        add(startGameButton, gbc);
    }

    public void updateStartButtonPosition() {
        GridBagConstraints gbc = getStartButtonGridBagConstraints();
        remove(startGameButton);
        add(startGameButton, gbc);
        updateGUI();
    }

    private GridBagConstraints getStartButtonGridBagConstraints() {
        GridBagConstraints gbc = getDefaultConstraints(1, 2);
        gbc.weightx = 0.7;
        gbc.insets = new Insets(20,
                (MenuFrameHandler.getMainFrame().getWidth() - gameGifLabel.getIcon().getIconWidth() - 128) / 2, 20, 0);
        return gbc;
    }

    private GridBagConstraints getDefaultConstraints(int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    private void startGame() {
        if (!playerSelectionPanel.arePlayersReady()) {
            JOptionPane.showMessageDialog(this, "Not all players are ready", "", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Player[] players = playerSelectionPanel.getSelectedPlayers();

        // Create all datas of user's models
        ModelManager.tryToCreateModels(playerSelectionPanel.getFirstNameUsers());

        GameModeData gameModeSettings = gameModeSelectionPanel.getGameModeData();
        GameMode gameMode = gameModeSelectionPanel.getSelectedGameMode();
        GameData gameData = new GameData(gameModeSettings, gameMode, players);

        JFrame frame = MenuFrameHandler.getMainFrame();
        MazeController mazeController = new MazeControllerImplementation(gameData, frame);
        MazeView mazeView = mazeController.getView();

        playerSelectionPanel.setPlayersToUnready();

        frame.setContentPane(mazeView);

        MenuFrameHandler.frameUpdate(gameMode.toString());
    }

    private void addEscapeKeyBind() {
        HotkeyAdder.addHotkey(this, KeyEvent.VK_ESCAPE, MenuFrameHandler::goToStartMenu, "Go to Start Menu");
    }

    private void updateGUI() {
        revalidate();
        repaint();
    }
}