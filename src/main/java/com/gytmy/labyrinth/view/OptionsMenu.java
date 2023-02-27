package com.gytmy.labyrinth.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

    private JComboBox<User> userSelector;
    private JComboBox<String> wordSelector = new JComboBox<>();

    private JScrollPane scrollPane;
    private JTree fileNavigator;

    private final String JTREE_ROOT_PATH = "src/resources/audioFiles/";
    private String actualJTreeRootPath = JTREE_ROOT_PATH;

    private final User NO_ONE = new User("ALL", "USERS", 0);

    private JLabel totalOfWords;
    private JButton deleteUserButton;

    public OptionsMenu(JFrame frame) {
        this.frame = frame;

        setLayout(new BorderLayout());

        initMenu();
    }

    private void initMenu() {
        initUserSelector();
        loadFileNavigator();
        initWordSelector();
    }

    private void initUserSelector() {
        JPanel userPanel = new JPanel(new GridLayout(1, 4));

        userSelector = new JComboBox<>();
        addUsersToJComboBox(userSelector);
        userSelector.addActionListener(e -> userHasBeenChanged());
        userPanel.add(userSelector);

        deleteUserButton = new JButton("Delete user");
        deleteUserButton.setEnabled(false);
        deleteUserButton.addActionListener(e -> deleteUser());
        userPanel.add(deleteUserButton);

        JButton editUserButton = new JButton("Edit user");
        userPanel.add(editUserButton);

        JButton addUserButton = new JButton("Add user");
        userPanel.add(addUserButton);

        add(userPanel, BorderLayout.NORTH);
    }

    private void deleteUser() {

        String confirmationDialog = "Are you sure you want to delete this user? Everything will be lost.";
        int userIsDeleted = JOptionPane.showConfirmDialog(frame, confirmationDialog, "DELETE USER ?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (userIsDeleted == JOptionPane.YES_OPTION) {
            User user = (User) userSelector.getSelectedItem();
            AudioFileManager.removeUser(user);
            userSelector.removeItem(user);
        }
    }

    private void userHasBeenChanged() {
        if (!(userSelector.getSelectedItem() instanceof User)) {
            return;
        }

        User user = (User) userSelector.getSelectedItem();

        if (user == NO_ONE) {
            actualJTreeRootPath = JTREE_ROOT_PATH;
            deleteUserButton.setEnabled(false);
        } else {
            actualJTreeRootPath = JTREE_ROOT_PATH + user.getFirstName();
            deleteUserButton.setEnabled(true);
        }
        loadFileNavigator();
        loadTotalOfWords();
        revalidate();
    }

    private void addUsersToJComboBox(JComboBox<User> userSelector) {
        List<User> users = AudioFileManager.getUsers();
        userSelector.addItem(NO_ONE);

        for (User user : users) {
            userSelector.addItem(user);
        }
    }

    public void loadFileNavigator() {

        if (scrollPane != null) {
            remove(scrollPane);
        }

        TreeModel model = new FileSystemTreeModel(new File(actualJTreeRootPath));
        fileNavigator = new JTree(model);
        fileNavigator.setScrollsOnExpand(true);
        fileNavigator.setShowsRootHandles(true);

        scrollPane = new JScrollPane(fileNavigator);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void initWordSelector() {
        JPanel audioPanel = new JPanel(new GridLayout(4, 1));

        addWordsToJComboBox(wordSelector);
        wordSelector.addActionListener(e -> loadTotalOfWords());
        audioPanel.add(wordSelector);

        totalOfWords = new JLabel(getTotalOfWords());
        audioPanel.add(totalOfWords);

        JButton recordButton = new JButton("Record");
        audioPanel.add(recordButton);

        JButton goBackButton = new JButton("Go back");
        goBackButton.addActionListener(e -> goBackToStartMenu());
        audioPanel.add(goBackButton);

        add(audioPanel, BorderLayout.EAST);
    }

    private void loadTotalOfWords() {
        totalOfWords.setText(getTotalOfWords());
    }

    private String getTotalOfWords() {
        String label = "Total of words: ";

        if (!(userSelector.getSelectedItem() instanceof User)) {
            return label + 0;
        }

        User user = (User) userSelector.getSelectedItem();
        String word = wordSelector == null ? "ALL" : (String) wordSelector.getSelectedItem();

        if (user == NO_ONE) {
            return label + getTotalOfWordsForAllUsers(user, word);
        }

        if (word.equals("ALL")) {
            return label + AudioFileManager.totalNumberOfAudioFilesForUser(user.getFirstName());
        }

        return label + AudioFileManager.numberOfRecordings(user.getFirstName(), word);
    }

    private int getTotalOfWordsForAllUsers(User user, String word) {
        if (word.equals("ALL")) {
            return AudioFileManager.totalNumberOfAudioFiles();
        }

        int totalForASpecificWord = 0;
        for (User usr : AudioFileManager.getUsers()) {
            totalForASpecificWord += AudioFileManager.numberOfRecordings(usr.getFirstName(), word);
        }
        return totalForASpecificWord;
    }

    private void addWordsToJComboBox(JComboBox<String> wordSelector) {

        wordSelector.addItem("ALL");

        WordsToRecord[] words = WordsToRecord.values();
        for (WordsToRecord word : words) {
            wordSelector.addItem(word.name());
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
