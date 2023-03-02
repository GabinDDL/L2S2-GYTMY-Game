package com.gytmy.labyrinth.view.settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.player.PlayerImplementation;
import com.gytmy.labyrinth.view.Cell;
import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.User;
import com.gytmy.utils.Coordinates;

public class PlayerSelectorPanel extends JPanel {

    public PlayerSelectorPanel() {
        super();
    }

    public static class PlayerPanel extends JPanel {

        private static final Color DEFAULT_BACKGROUND = Cell.WALL_COLOR;
        private static final Color LETTER_COLOR = Color.WHITE;
        private static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 30);
        private static final String ADD_PLAYER_IMAGE_PATH = "src/resources/images/settings_menu/add_player.png";

        private Player player;

        public PlayerPanel() {
            initEmpty();
        }

        public PlayerPanel(Player player) {
            this.player = player;
            init();
        }

        private void initEmpty() {
            setBackground(DEFAULT_BACKGROUND);
            ImageIcon icon = new ImageIcon(ADD_PLAYER_IMAGE_PATH);
            JLabel label = new JLabel(icon);
            label.setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
        }

        private void init() {
            setBackground(player.getColor());
            JLabel playerNameLabel = new JLabel(player.getName());
            playerNameLabel.setFont(DEFAULT_FONT);
            playerNameLabel.setForeground(LETTER_COLOR);
            setLayout(new GridBagLayout());
            add(playerNameLabel);
        }

    }

    /**
     * This class is used to select a user. It is part of a Observer pattern. It is
     * the Observer.
     */
    public class UserSelector {

        private JComboBox<User> userSelector;

        public UserSelector() {
            AvailableUsers.getInstance().addObserver(this);
            updateUserList();
        }

        private void updateUserList() {
            for (User user : AvailableUsers.getInstance().getUsers()) {
                userSelector.addItem(user);
            }
        }
    }

    /**
     * This class is used to store the available users in the system. It uses the
     * Singleton pattern and is part of a Observer pattern. It is the subject.
     */
    private static class AvailableUsers {

        private List<User> users;
        private List<UserSelector> observers = new ArrayList<>();
        private static AvailableUsers instance = null;

        private AvailableUsers() {
            users = new ArrayList<>();
        }

        public static AvailableUsers getInstance() {
            if (instance == null) {
                instance = new AvailableUsers();
            }
            return instance;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

        public void addObserver(UserSelector observer) {
            observers.add(observer);
        }

        public void removeObserver(UserSelector observer) {
            observers.remove(observer);
        }

    }

    // TODO:Remove this

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // PlayerPanel panel = new PlayerPanel();
        // Player player = new PlayerImplementation(new Coordinates(1, 1), "Player 1",
        // Color.RED, true);
        // PlayerPanel panel = new PlayerPanel(player);

        UserSelector panel = new PlayerSelectorPanel().new UserSelector();

        frame.add(panel);

        frame.setVisible(true);

    }

}
