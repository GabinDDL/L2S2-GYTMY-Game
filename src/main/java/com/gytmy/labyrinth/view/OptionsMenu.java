package com.gytmy.labyrinth.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import java.io.File;

import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.User;
import com.gytmy.utils.FileSystemTreeModel;
import com.gytmy.utils.WordsToRecord;

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
        TreeModel model = new FileSystemTreeModel(new File("src/resources/audioFiles"));
        JTree fileNavigator = new JTree(model);
        fileNavigator.setScrollsOnExpand(true);

        add(fileNavigator, BorderLayout.CENTER);
    }

    private void initWordSelector() {
        JPanel audioPanel = new JPanel(new GridLayout(4, 1));

        JComboBox<WordsToRecord> wordSelector = new JComboBox<>();
        addWordsToJComboBox(wordSelector);
        audioPanel.add(wordSelector);

        JLabel totalOfWords = new JLabel("Total of words: " + WordsToRecord.values().length);
        audioPanel.add(totalOfWords);

        JButton recordButton = new JButton("Record");
        audioPanel.add(recordButton);

        JButton goBackButton = new JButton("Go back");
        goBackButton.addActionListener(e -> goBackToStartMenu());
        audioPanel.add(goBackButton);

        add(audioPanel, BorderLayout.EAST);
    }

    private void addWordsToJComboBox(JComboBox<WordsToRecord> wordSelector) {
        WordsToRecord[] words = WordsToRecord.values();
        for (WordsToRecord word : words) {
            wordSelector.addItem(word);
        }
    }

    public void goBackToStartMenu() {
        frame.setContentPane(new StartMenu(frame));
        GameFrameToolbox.frameUpdate(frame, "Menu");
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 500);
        frame.revalidate();
    }
}
