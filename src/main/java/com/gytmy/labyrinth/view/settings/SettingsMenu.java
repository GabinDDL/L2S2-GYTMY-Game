package com.gytmy.labyrinth.view.settings;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gytmy.labyrinth.controller.LabyrinthController;
import com.gytmy.labyrinth.controller.LabyrinthControllerImplementation;
import com.gytmy.labyrinth.model.GameData;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.view.Cell;
import com.gytmy.labyrinth.view.GameFrameToolbox;
import com.gytmy.labyrinth.view.LabyrinthView;
import com.gytmy.labyrinth.view.settings.game_mode.GameMode;
import com.gytmy.labyrinth.view.settings.game_mode.GameModeData;
import com.gytmy.labyrinth.view.settings.game_mode.GameModeSelectionPanel;
import com.gytmy.labyrinth.view.settings.player.PlayerSelectionPanel;

public class SettingsMenu extends JPanel {

    private JFrame frame;

    private PlayerSelectionPanel playerSelectionPanel;
    private GameModeSelectionPanel gameModeSelectionPanel;
    private JLabel gameGifLabel;
    private JButton startGameButton;

    private static final String ANIMATED_GAME_GIF_PATH = "src/resources/images/settings_menu/MAZE_LOGO_ROTATED.gif";
    private static final Color BACKGROUND_COLOR = Cell.WALL_COLOR;

    public SettingsMenu(JFrame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);

        initPlayerSelectionPanel();
        initGameGifLabel();
        initGameSelectionPanel();
        initStartGameButton();

        updateGUI();
    }

    private void initPlayerSelectionPanel() {
        playerSelectionPanel = new PlayerSelectionPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        add(playerSelectionPanel, gbc);
    }

    private void initGameGifLabel() {
        gameGifLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(ANIMATED_GAME_GIF_PATH);
        gameGifLabel.setIcon(imageIcon);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(gameGifLabel, gbc);
    }

    private void initGameSelectionPanel() {
        gameModeSelectionPanel = new GameModeSelectionPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 20, 20);
        add(gameModeSelectionPanel, gbc);
    }

    private void initStartGameButton() {
        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> startGame());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(startGameButton, gbc);
    }

    private void startGame() {

        if (!playerSelectionPanel.arePlayersReady()) {
            // TODO: Add error message
            return;
        }

        // TODO: Handle wrong inputs

        Player[] players = playerSelectionPanel.getSelectedPlayers();
        GameModeData gameModeSettings = gameModeSelectionPanel.getGameModeData();
        GameMode gameMode = gameModeSelectionPanel.getSelectedGameMode();
        GameData gameData = new GameData(gameModeSettings, gameMode, players);

        LabyrinthController labyrinthController = new LabyrinthControllerImplementation(gameData, frame);
        LabyrinthView labyrinthView = labyrinthController.getView();

        frame.setContentPane(labyrinthView);
        GameFrameToolbox.frameUpdate(frame, "View Labyrinth" + gameMode);

    }

    private void updateGUI() {
        revalidate();
        repaint();
    }

}
