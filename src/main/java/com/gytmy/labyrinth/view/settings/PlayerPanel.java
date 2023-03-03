package com.gytmy.labyrinth.view.settings;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.player.PlayerImplementation;
import com.gytmy.labyrinth.view.Cell;

public class PlayerPanel extends JPanel {
    private static final Color[] COLORS = new Color[] {
            Color.decode("#b13e53"),
            Color.decode("#ffcd75"),
            Color.decode("#38b764"),
            Color.decode("#41a6f6"),
            Color.decode("#29366f")
    };
    private static final Color DEFAULT_BACKGROUND = Cell.WALL_COLOR;
    private static final String ADD_PLAYER_IMAGE_PATH = "src/resources/images/settings_menu/add_player.png";

    private static int playerCount = 0;
    private int id;

    private UserSelector userSelector;

    private boolean isPlayerReady = false;

    public PlayerPanel() {
        id = playerCount;
        playerCount++;
        GridBagConstraints gbc = new GridBagConstraints();
        userSelector = null;
        initEmpty(gbc);
    }

    private void initEmpty(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        setBackground(DEFAULT_BACKGROUND);
        ImageIcon icon = new ImageIcon(ADD_PLAYER_IMAGE_PATH);
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setLayout(new GridBagLayout());
        add(imageLabel);
        revalidate();
        repaint();

        addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        if (userSelector == null) {
                            initNonEmpty();
                            initUserSelector(gbc);
                        } else {
                            updateReadyStatus();
                        }
                        revalidate();
                        repaint();
                        break;
                    case MouseEvent.BUTTON3:
                        if (userSelector == null) {
                            break;
                        }
                        remove(userSelector);
                        userSelector.unlockChoice();
                        userSelector.cleanData();
                        userSelector = null;
                        initEmpty(gbc);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initUserSelector(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        userSelector = new UserSelector();
        add(userSelector);
    }

    private void initNonEmpty() {
        removeAll();
        setBackground(COLORS[id]);
        setLayout(new GridBagLayout());
    }

    private void updateReadyStatus() {
        isPlayerReady = !isPlayerReady;
        if (isPlayerReady) {
            setBackground(Color.decode("#ffffff"));
            userSelector.lockChoice();
        } else {
            setBackground(COLORS[id]);
            userSelector.unlockChoice();
        }
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
