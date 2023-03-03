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
            setLayout(new GridBagLayout());
        }

    }

    /**
     * This class is used to select a user. It is part of a Observer pattern. It is
     * the Observer.
     */
    public class UserSelector extends JComboBox<User> {

        public UserSelector() {
            AvailableUsers.getInstance().addObserver(this);
            updateUserList();
        }

        private void updateUserList() {
            List<User> availableUsers = AvailableUsers.getInstance().getUsers();
            availableUsers.forEach(user -> addItem(new UserNameUser(user)));
        }

        public void update() {
            removeAllItems();
            updateUserList();
        }

        public void lockChoice() {
            setEnabled(false);
            AvailableUsers.getInstance().removeUser((User) getSelectedItem());
        }

        public void unlockChoice() {
            setEnabled(true);
            AvailableUsers.getInstance().addUser((User) getSelectedItem());
        }

    }

    private static class UserNameUser extends User {

        public UserNameUser(User user) {
            super(user);
        }

        @Override
        public String toString() {
            return getUserName();
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
            users = AudioFileManager.getUsers();
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

        public void removeUser(User user) {
            users.remove(user);
            notifyObservers();
        }

        public void addUser(User user) {
            users.add(user);
            notifyObservers();
        }

        public void notifyObservers() {
            observers.forEach(UserSelector::update);
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

        // AudioFileManager.addUser(new User("Yago", "Iglesias", 12345, "Grep"));
        // AudioFileManager.addUser(new User("Yago1", "Iglesias", 12345, "Grep1"));
        // AudioFileManager.addUser(new User("Yago2", "Iglesias", 12345, "Grep2"));
        // AudioFileManager.addUser(new User("Yago3", "Iglesias", 12345, "Grep3"));
        // AudioFileManager.addUser(new User("Yago4", "Iglesias", 12345, "Grep4"));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // PlayerPanel panel = new PlayerPanel();
        // Player player = new PlayerImplementation(new Coordinates(1, 1), "Player 1",
        // Color.RED, true);
        // PlayerPanel panel = new PlayerPanel(player);

        UserSelector panel1 = new PlayerSelectorPanel().new UserSelector();
        UserSelector panel2 = new PlayerSelectorPanel().new UserSelector();
        UserSelector panel3 = new PlayerSelectorPanel().new UserSelector();
        UserSelector panel4 = new PlayerSelectorPanel().new UserSelector();

        UserSelector[] panels = { panel1, panel2, panel3, panel4 };

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);

        frame.add(panel);
        frame.setVisible(true);

    }

}
