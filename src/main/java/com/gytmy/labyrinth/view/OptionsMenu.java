package com.gytmy.labyrinth.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.User;
import com.gytmy.utils.FileSystemTreeModel;
import com.gytmy.utils.WordsToRecord;

public class OptionsMenu extends JPanel {
    private JFrame frame;

    private JPanel userPanel;

    private JComboBox<User> userSelector;
    private JComboBox<String> wordSelector = new JComboBox<>();

    private JScrollPane scrollPane;
    private JTree fileNavigator;

    private final String JTREE_ROOT_PATH = "src/resources/audioFiles/";
    private String actualJTreeRootPath = JTREE_ROOT_PATH;

    private final User NO_ONE = new User("ALL", "USERS", 0);

    private JButton deleteUserButton;
    private JButton editUserButton;
    private JButton addUserButton;

    private JLabel totalOfWords;
    private JButton recordButton;

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

        userPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        userSelector = new JComboBox<>();
        addUsersToJComboBox(userSelector);
        userSelector.addActionListener(e -> userHasBeenChanged());
        addComponentToUserPanel(userSelector, c, 0, 0, 0.5, false);

        deleteUserButton = new JButton("Delete");
        deleteUserButton.setToolTipText("This will delete the current user and all his recordings");
        deleteUserButton.setEnabled(false);
        deleteUserButton.addActionListener(e -> deleteUser());
        addComponentToUserPanel(deleteUserButton, c, 1, 0, 0.1, true);

        editUserButton = new JButton("Edit");
        editUserButton.setToolTipText("This will edit the current user");
        addComponentToUserPanel(editUserButton, c, 2, 0, 0.1, true);

        addUserButton = new JButton("Add");
        addUserButton.setToolTipText("This will add a new user");
        addComponentToUserPanel(addUserButton, c, 3, 0, 0.1, true);

        add(userPanel, BorderLayout.NORTH);

    }

    private void addComponentToUserPanel(JComponent component, GridBagConstraints c, int gridx, int gridy,
            double weightx, boolean setPreferredSize) {

        c.gridx = gridx;
        c.gridy = gridy;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = weightx;

        userPanel.add(component, c);

        component.setPreferredSize(
                new Dimension(component.getPreferredSize().height,
                        component.getPreferredSize().height));
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

        recordButton = new JButton("Record");
        recordButton.setToolTipText("Record a new audio for the selected word");
        audioPanel.add(recordButton);

        JButton goBackButton = new JButton("Go back");
        goBackButton.setToolTipText("Go back to start menu");
        goBackButton.addActionListener(e -> goBackToStartMenu());
        audioPanel.add(goBackButton);

        add(audioPanel, BorderLayout.EAST);
    }

    private void loadTotalOfWords() {
        totalOfWords.setText(getTotalOfWords());
    }

    private String getTotalOfWords() {
        if (!(userSelector.getSelectedItem() instanceof User)) {
            return "Not an user";
        }

        String label = "Total of audios : ";

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
