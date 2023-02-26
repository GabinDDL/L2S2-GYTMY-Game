package com.gytmy.labyrinth.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.User;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

public class OptionsMenu extends JPanel {
    private JFrame frame;

    public OptionsMenu(JFrame frame) {
        this.frame = frame;

        setLayout(new BorderLayout());

        initMenu();
    }

    private void initMenu() {
        initUserSelector();
        initFileNavigator();
        initWordSelector();
    }

    private void initUserSelector() {
        JPanel userPanel = new JPanel(new GridLayout(1, 4));

        JComboBox<User> userSelector = new JComboBox<>();
        addUsersToJComboBox(userSelector);
        userPanel.add(userSelector);

        JButton deleteUserButton = new JButton("Delete user");
        userPanel.add(deleteUserButton);

        JButton editUserButton = new JButton("Edit user");
        userPanel.add(editUserButton);

        JButton addUserButton = new JButton("Add user");
        userPanel.add(addUserButton);

        add(userPanel, BorderLayout.NORTH);
    }

    private void addUsersToJComboBox(JComboBox<User> userSelector) {
        List<User> users = AudioFileManager.getUsers();
        for (User user : users) {
            userSelector.addItem(user);
        }
        userSelector.addItem(new User("User 1", "Name 1", 2121));
    }

    private void initFileNavigator() {
    }

    private void initWordSelector() {
    }

    public void goBackToStartMenu() {
        frame.setContentPane(new StartMenu(frame));
        GameFrameToolbox.frameUpdate(frame, "Menu");
    }
}
