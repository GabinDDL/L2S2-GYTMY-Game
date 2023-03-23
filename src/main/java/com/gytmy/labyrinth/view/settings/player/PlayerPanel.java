package com.gytmy.labyrinth.view.settings.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.player.PlayerImplementation;
import com.gytmy.labyrinth.view.Cell;
import com.gytmy.labyrinth.view.MenuFrameHandler;
import com.gytmy.utils.ImageManipulator;

/**
 * This class represents a panel that allows the user to select a player.
 */
public class PlayerPanel extends JPanel {
    private static final Color[] COLORS = new Color[] {
            Color.decode("#b13e53"),
            Color.decode("#ffcd75"),
            Color.decode("#38b764"),
            Color.decode("#41a6f6"),
            Color.decode("#29366f")
    };

    private static final Color DEFAULT_BACKGROUND = Cell.WALL_COLOR;
    private static final Color BORDER_COLOR = Cell.PATH_COLOR;
    private static final int ICON_HEIGHT = 40;
    private static final int ICON_WIDTH = 40;

    private static final String ADD_PLAYER_IMAGE_PATH = "src/resources/images/settings_menu/add_player.png";
    private static final ImageIcon READY_PLAYER_MARK_ICON = ImageManipulator
            .resizeImage("src/resources/images/settings_menu/check-mark.png", ICON_WIDTH, ICON_HEIGHT);
    private static final ImageIcon NOT_READY_PLAYER_MARK_ICON = ImageManipulator
            .resizeImage("src/resources/images/settings_menu/cross-mark.png", ICON_WIDTH, ICON_HEIGHT);

    private int id;
    private UserSelector userSelector;
    private boolean isPlayerReady;
    private JLabel isPlayerReadyLabel;

    public PlayerPanel(int id) {
        this.id = id;

        setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        setLayout(new GridBagLayout());

        initEmpty();
        addMouseListener(new PanelMouseListener());
    }

    private void initEmpty() {
        removeAll();
        setBackground(DEFAULT_BACKGROUND);

        // Setting the user selector to null is important
        // because it allows the garbage collector to free the memory
        userSelector = null;
        isPlayerReady = false;

        JLabel label = new JLabel();
        // This size allows the image to be square and to fit in the panel
        int imageWidth = getPreferredSize().width / 2;
        int imageHeight = getPreferredSize().height / 2;
        label.setIcon(ImageManipulator.resizeImage(ADD_PLAYER_IMAGE_PATH, new Dimension(imageWidth, imageHeight)));
        add(label);

        updateGUI();
    }

    @Override
    public Dimension getPreferredSize() {
        // This size allows the panel to be square and to fit in the frame
        int frameWidth = MenuFrameHandler.getMainFrame().getWidth();
        int size = frameWidth / PlayerSelectionPanel.MAX_OF_PLAYERS;
        return new Dimension(size, size);
    }

    private class PanelMouseListener extends MouseInputAdapter {
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

            initEmpty();
        }

        private void initNonEmpty() {
            removeAll();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;

            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(COLORS[id]);
            add(panel, gbc);

            isPlayerReadyLabel = new JLabel(NOT_READY_PLAYER_MARK_ICON);
            isPlayerReadyLabel.setBackground(COLORS[id]);
            panel.add(isPlayerReadyLabel, BorderLayout.CENTER);

            updateGUI();
        }

        private void initUserSelector() {
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 1;
            gbc.gridy = 1;

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.PAGE_END;
            gbc.weightx = 1;

            userSelector = new UserSelector();
            add(userSelector, gbc);

            updateGUI();
        }

        private void updateReadyStatus() {

            if (isPlayerReady) {
                isPlayerReadyLabel.setIcon(NOT_READY_PLAYER_MARK_ICON);
            } else {
                isPlayerReadyLabel.setIcon(READY_PLAYER_MARK_ICON);
            }

            isPlayerReady = !isPlayerReady;
            if (isPlayerReady) {
                userSelector.lockChoice();
            } else {
                userSelector.unlockChoice();
            }

            updateGUI();
        }
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

    public boolean isReady() {
        return isPlayerReady;
    }

    public boolean isActivated() {
        return userSelector != null;
    }
}
