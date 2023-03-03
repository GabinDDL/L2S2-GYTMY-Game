package com.gytmy.labyrinth.view.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.player.PlayerImplementation;
import com.gytmy.labyrinth.view.Cell;
import com.gytmy.utils.ImageManipulator;

public class PlayerPanel extends JPanel {
    private static final Color[] COLORS = new Color[] {
            Color.decode("#b13e53"),
            Color.decode("#ffcd75"),
            Color.decode("#38b764"),
            Color.decode("#41a6f6"),
            Color.decode("#29366f")
    };
    private static final Color DEFAULT_BACKGROUND = Cell.WALL_COLOR;
    private static final Color BORDE_COLOR = Cell.PATH_COLOR;
    private static final String ADD_PLAYER_IMAGE_PATH = "src/resources/images/settings_menu/add_player.png";
    private static final Dimension PANEL_DIMENSION = new Dimension(165, 165);

    private static int playerCount = 0;
    private int id;
    private UserSelector userSelector;
    private boolean isPlayerReady = false;

    public PlayerPanel() {
        id = playerCount;
        playerCount++;

        setBorder(BorderFactory.createLineBorder(BORDE_COLOR));
        setPreferredSize(PANEL_DIMENSION);
        setLayout(new GridBagLayout());

        userSelector = null;
        initializeMouseBehavior();
        initEmpty();
    }

    private void initializeMouseBehavior() {
        MouseInputAdapter adapter = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        handleLeftClick();
                        break;
                    case MouseEvent.BUTTON3:
                        handleRightClick();
                        break;
                    default:
                        break;
                }
            }

            private void handleLeftClick() {
                if (userSelector == null) {
                    initNonEmpty();
                    initUserSelector();
                } else {
                    updateReadyStatus();
                }
            }

            private void handleRightClick() {
                if (userSelector == null) {
                    return;
                }
                remove(userSelector);
                userSelector.unlockChoice();
                userSelector.cleanData();
                userSelector = null;
                initEmpty();
            }
        };

        addMouseListener(adapter);
    }

    private void initNonEmpty() {
        removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel panel = new JPanel();
        panel.setBackground(COLORS[id]);
        add(panel, gbc);

        updateGUI();
    }

    private void initUserSelector() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        userSelector = new UserSelector();
        add(userSelector, gbc);

        updateGUI();
    }

    private void updateReadyStatus() {
        // TODO: Temporal fix waiting for a better solution
        // @lacenne should be able to fix this
        isPlayerReady = !isPlayerReady;
        if (isPlayerReady) {
            userSelector.lockChoice();
        } else {
            userSelector.unlockChoice();
        }
        updateGUI();

    }

    private void initEmpty() {
        removeAll();
        setBackground(DEFAULT_BACKGROUND);
        JLabel label = new JLabel();
        int imageWidth = getPreferredSize().width / 2;
        int imageHeight = getPreferredSize().height / 2;
        label.setIcon(ImageManipulator.resizeImage(ADD_PLAYER_IMAGE_PATH, new Dimension(imageWidth, imageHeight)));
        add(label);
        updateGUI();
    }

    private void updateGUI() {
        revalidate();
        repaint();
    }

    public Player getPlayer() {
        if (userSelector == null || !isPlayerReady) {
            return null;
        }
        String userName = userSelector.getSelectedItem().toString();
        Color color = COLORS[id];
        return new PlayerImplementation(userName, color);

    }
}
