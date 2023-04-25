package com.gytmy.labyrinth.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;

import com.gytmy.labyrinth.view.game.Cell;
import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.AudioPlayer;
import com.gytmy.sound.PlayingTimer;
import com.gytmy.sound.User;
import com.gytmy.utils.FileTree;
import com.gytmy.utils.ImageManipulator;
import com.gytmy.utils.HotkeyAdder;
import com.gytmy.utils.WordsToRecord;

public class AudioMenu extends JPanel {

    private JFrame mainFrame = MenuFrameHandler.getMainFrame();

    private JPanel userPanel;
    private JComboBox<User> userSelector;
    private JComboBox<String> wordSelector = new JComboBox<>();

    // User Panel components
    private JButton deleteUserButton;
    private JButton editUserButton;
    private JButton addUserButton;

    // File Tree Panel Components
    private JScrollPane scrollPane;
    private JTree fileNavigator;
    private static final String JTREE_ROOT_PATH = "src/resources/audioFiles/";
    private String actualJTreeRootPath = JTREE_ROOT_PATH;
    private static final User ALL_USERS = new User("ALL", "USERS", 0, "EVERYONE");

    // Word Panel Components
    private JLabel totalOfWords;
    private JLabel totalAudioLength;
    private JButton recordButton;
    private JButton deleteRecord;

    private JProgressBar timeProgress;
    private JLabel labelDuration = new JLabel("00:00");
    private AudioPlayer player = new AudioPlayer();
    private Thread playbackThread;
    private PlayingTimer timer;
    private boolean isPlaying = false;

    private static final int ICON_HEIGHT = 20;
    private static final int ICON_WIDTH = 20;

    private static final ImageIcon deleteUserIcon = ImageManipulator.resizeImage(
            "src/resources/images/audio_menu/delete-button.png",
            ICON_WIDTH, ICON_HEIGHT);

    private static final ImageIcon editUserIcon = ImageManipulator.resizeImage(
            "src/resources/images/audio_menu/edit.png",
            ICON_WIDTH, ICON_HEIGHT);

    private static final ImageIcon addUserIcon = ImageManipulator.resizeImage(
            "src/resources/images/audio_menu/add-user.png",
            ICON_WIDTH, ICON_HEIGHT);

    private static final ImageIcon recordIcon = ImageManipulator.resizeImage(
            "src/resources/images/audio_menu/rec-button.png",
            ICON_WIDTH, ICON_HEIGHT);

    private static final ImageIcon deleteIcon = ImageManipulator.resizeImage(
            "src/resources/images/audio_menu/bin.png",
            ICON_WIDTH, ICON_HEIGHT);

    private static final ImageIcon playIcon = ImageManipulator.resizeImage(
            "src/resources/images/audio_menu/play-button.png",
            ICON_WIDTH, ICON_HEIGHT);
    private static final ImageIcon pauseIcon = ImageManipulator.resizeImage(
            "src/resources/images/audio_menu/pause-button.png",
            ICON_WIDTH, ICON_HEIGHT);
    private JButton playAndStopButton;

    private static final ImageIcon goBackIcon = ImageManipulator.resizeImage(
            "src/resources/images/audio_menu/go-back-arrow.png",
            ICON_WIDTH, ICON_HEIGHT);

    private String audioToLoad = "";

    // Colors
    private static final Color BUTTON_COLOR = Cell.WALL_COLOR;
    private static final Color TEXT_COLOR = Cell.PATH_COLOR;
    private static final Color BACK_BUTTON_COLOR = Cell.EXIT_CELL_COLOR;

    private static AudioMenu instance = null;

    public static AudioMenu getInstance() {
        if (instance == null) {
            instance = new AudioMenu();
        }
        return instance;
    }

    public AudioMenu() {
        setLayout(new BorderLayout());

        initUserPanel();
        loadFileNavigator();
        initWordPanel();

        HotkeyAdder.addHotkey(this, KeyEvent.VK_ESCAPE, MenuFrameHandler::goToStartMenu, "Go to Start Menu");
    }

    /**
     * User Panel is the top panel of the OptionsMenu.
     * It's used to select a user, add a new one, or edit / delete an existing one.
     */
    private void initUserPanel() {

        userPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        initUserSelector(c);
        initDeleteButton(c);
        initEditButton(c);
        initAddButton(c);

        add(userPanel, BorderLayout.NORTH);
    }

    private void initUserSelector(GridBagConstraints c) {
        userSelector = new JComboBox<>();
        addUsersToJComboBox(userSelector);
        userSelector.addActionListener(e -> userHasBeenChanged());
        userSelector.setBackground(BUTTON_COLOR);
        userSelector.setForeground(TEXT_COLOR);
        addComponentToUserPanel(userSelector, c, 0, 0, 0.46, false);
    }

    private void addUsersToJComboBox(JComboBox<User> userSelector) {
        List<User> users = AudioFileManager.getUsers();
        userSelector.addItem(ALL_USERS);

        for (User user : users) {
            userSelector.addItem(user);
        }
    }

    public void updateUserSelector() {
        User selectedUser = (User) userSelector.getSelectedItem();
        userSelector.removeAllItems();
        addUsersToJComboBox(userSelector);
        if (selectedUser != null) {
            userSelector.setSelectedItem(selectedUser);
        }
    }

    private void initDeleteButton(GridBagConstraints c) {
        deleteUserButton = new JButton(deleteUserIcon);

        deleteUserButton.setToolTipText("This will delete the current user and all his recordings");
        deleteUserButton.setEnabled(false);
        deleteUserButton.addActionListener(e -> deleteUser());
        initColors(deleteUserButton);
        addComponentToUserPanel(deleteUserButton, c, 1, 0, 0.1, true);
    }

    private void initEditButton(GridBagConstraints c) {
        editUserButton = new JButton(editUserIcon);
        editUserButton.setToolTipText("This will edit the current user");
        editUserButton.setEnabled(false);
        editUserButton.addActionListener(
                e -> editOrAddUser("Edit User",
                        new EditCreateUsersPage((User) userSelector.getSelectedItem())));
        initColors(editUserButton);
        addComponentToUserPanel(editUserButton, c, 2, 0, 0.1, true);
    }

    private void initAddButton(GridBagConstraints c) {
        addUserButton = new JButton(addUserIcon);
        addUserButton.setToolTipText("This will add a new user");
        addUserButton.addActionListener(e -> editOrAddUser("Add New User", new EditCreateUsersPage()));
        initColors(addUserButton);
        addComponentToUserPanel(addUserButton, c, 3, 0, 0.1, true);
    }

    private void editOrAddUser(String title, EditCreateUsersPage page) {
        mainFrame.setContentPane(page);
        mainFrame.revalidate();
        mainFrame.setTitle(title);
    }

    private void initColors(JComponent component) {
        component.setBackground(BUTTON_COLOR);
        component.setForeground(TEXT_COLOR);
    }

    private void addComponentToUserPanel(JComponent component, GridBagConstraints c, int gridx, int gridy,
            double weightx, boolean setPreferredSize) {

        c.gridx = gridx;
        c.gridy = gridy;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = weightx;

        userPanel.add(component, c);

        if (setPreferredSize) {
            component.setPreferredSize(
                    new Dimension(component.getPreferredSize().height, component.getPreferredSize().height));
        }
    }

    private void userHasBeenChanged() {
        if (!(userSelector.getSelectedItem() instanceof User)) {
            return;
        }

        User user = (User) userSelector.getSelectedItem();

        actualJTreeRootPath = JTREE_ROOT_PATH;
        if (user == ALL_USERS) {
            deleteUserButton.setEnabled(false);
            editUserButton.setEnabled(false);
            disableRecordButton();
        } else {
            actualJTreeRootPath += user.getFirstName();
            deleteUserButton.setEnabled(true);
            editUserButton.setEnabled(true);

            if (!wordSelector.getSelectedItem().equals("ALL")) {
                enableRecordButton();
            }
        }

        updateGUI();
    }

    private void enableRecordButton() {
        changeButtonState(recordButton, "Record", recordIcon, true);
    }

    private void disableRecordButton() {
        changeButtonState(recordButton, "R̶e̶c̶o̶r̶d̶", null, false);
    }

    private void changeButtonState(JButton button, String text, Icon icon, boolean enabled) {
        button.setEnabled(enabled);
        button.setText(text);
        button.setIcon(icon);
    }

    private void deleteUser() {

        String confirmationDialog = "Are you sure you want to delete this user? Everything will be lost.";
        int userIsDeleted = JOptionPane.showConfirmDialog(mainFrame, confirmationDialog, "DELETE USER ?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (userIsDeleted == JOptionPane.YES_OPTION) {
            User user = (User) userSelector.getSelectedItem();
            AudioFileManager.removeUser(user);
            userSelector.removeItem(user);
        }

        userSelector.setSelectedItem(ALL_USERS);

        updateGUI();
    }

    /**
     * File Navigator Panel is the center panel of the OptionsMenu.
     * It's used to navigate through the user's audio files.
     */
    public void loadFileNavigator() {

        if (scrollPane != null) {
            remove(scrollPane);
        }

        audioToLoad = "";

        if (deleteRecord != null) {
            disableDeleteButton();
        }

        fileNavigator = new FileTree(actualJTreeRootPath);
        fileNavigator.addTreeSelectionListener(e -> {
            if (isPlaying) {
                stop();
            }
            audioToLoad = ((FileTree) fileNavigator).getSelectedFilePath();
            if (audioToLoad.endsWith(".wav")) {
                playAndStopButton.setEnabled(true);
                enableDeleteButton();
            } else {
                playAndStopButton.setEnabled(false);
                disableDeleteButton();
            }
        });

        scrollPane = new JScrollPane(fileNavigator);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void loadTotalAudioLength() {
        totalAudioLength.setText(getTotalAudioLength());
    }

    private String getTotalAudioLength() {
        User selectedUser = (User) userSelector.getSelectedItem();
        String selectedWord = (String) wordSelector.getSelectedItem();

        if (selectedUser == ALL_USERS && selectedWord.equals("ALL")) {
            return Math.floor(AudioFileManager.getTotalDurationOfAllAudioFiles() * 10) / 10 + " seconds";
        }
        if (selectedUser == ALL_USERS) {
            return Math
                    .floor(AudioFileManager
                            .getTotalDurationOfAllAudioFilesForSpecificWord(selectedWord) * 10)
                    / 10
                    + " seconds";
        }

        if (selectedWord.equals("ALL")) {
            return Math.floor(AudioFileManager.getTotalDurationOfAllAudioFilesOfUser(selectedUser) * 10) / 10
                    + " seconds";
        }

        return Math.floor(
                AudioFileManager.getTotalDurationOfAllAudioFilesOfUserForSpecificWord(selectedUser, selectedWord) * 10)
                / 10
                + " seconds";
    }

    private void enableDeleteButton() {
        changeButtonState(deleteRecord, "Delete", deleteIcon, true);
    }

    private void disableDeleteButton() {
        changeButtonState(deleteRecord, "D̶e̶l̶e̶t̶e̶", null, false);
    }

    /**
     * Word Selector Panel is the panel at the right of the OptionsMenu. It's used
     * to select the word among the list to record. It displays the number of
     * records existing for the selected word and the selected user.
     * 
     * You can also go back to the main menu from it.
     */
    private void initWordPanel() {
        JPanel audioPanel = new JPanel(new GridLayout(9, 1));
        audioPanel.setBackground(BUTTON_COLOR);

        initWordSelector(audioPanel);
        initCountOfWords(audioPanel);
        initTotalAudioLength(audioPanel);
        initDeleteRecordButton(audioPanel);
        initRecordButton(audioPanel);
        initLabelDuration(audioPanel);
        initProgressBar(audioPanel);
        initMediaPlayer(audioPanel);
        initBackButton(audioPanel);

        add(audioPanel, BorderLayout.EAST);
    }

    private void initWordSelector(JComponent parentComponent) {
        addWordsToJComboBox(wordSelector);
        wordSelector.addActionListener(e -> wordHasBeenChanged());
        initColors(wordSelector);
        ((JLabel) wordSelector.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        parentComponent.add(wordSelector);
    }

    private void initCountOfWords(JComponent parenComponent) {
        totalOfWords = new JLabel(getTotalOfWords());
        initColors(totalOfWords);
        totalOfWords.setHorizontalAlignment(SwingConstants.CENTER);
        parenComponent.add(totalOfWords);
    }

    private void initTotalAudioLength(JComponent parentComponent) {
        totalAudioLength = new JLabel();
        loadTotalAudioLength();
        initColors(totalAudioLength);
        totalAudioLength.setHorizontalAlignment(SwingConstants.CENTER);
        parentComponent.add(totalAudioLength);
    }

    private void initRecordButton(JComponent parentComponent) {
        recordButton = new JButton("R̶e̶c̶o̶r̶d̶");
        recordButton.setToolTipText("Record a new audio for the selected word");
        recordButton.addActionListener(e -> recordAudio());
        recordButton.setEnabled(false);
        initColors(recordButton);
        parentComponent.add(recordButton);
    }

    private void recordAudio() {
        mainFrame.setContentPane(
                new RecordPage(this, (User) userSelector.getSelectedItem(),
                        (String) wordSelector.getSelectedItem()));
        mainFrame.revalidate();
        mainFrame.setTitle("RECORD STUDIO");
    }

    private void addWordsToJComboBox(JComboBox<String> wordSelector) {

        wordSelector.addItem("ALL");

        WordsToRecord[] words = WordsToRecord.values();
        for (WordsToRecord word : words) {
            wordSelector.addItem(word.name());
        }
    }

    private void wordHasBeenChanged() {

        disableRecordButton();
        if (wordSelector.getSelectedItem().equals("ALL")) {
            recordButton.setEnabled(false);
        } else if ((User) userSelector.getSelectedItem() != ALL_USERS) {
            enableRecordButton();
        }

        updateGUI();
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

        if (user == ALL_USERS) {
            return label + getTotalOfWordsForAllUsers(word);
        }

        if (word.equals("ALL")) {
            return label + AudioFileManager.totalNumberOfAudioFilesForUser(user.getFirstName());
        }

        return label + AudioFileManager.numberOfRecordings(user.getFirstName(), word);
    }

    private int getTotalOfWordsForAllUsers(String word) {
        if (word.equals("ALL")) {
            return AudioFileManager.totalNumberOfAudioFiles();
        }

        int totalForASpecificWord = 0;
        for (User usr : AudioFileManager.getUsers()) {
            totalForASpecificWord += AudioFileManager.numberOfRecordings(usr.getFirstName(), word);
        }
        return totalForASpecificWord;
    }

    private void initDeleteRecordButton(JComponent parentComponent) {
        deleteRecord = new JButton("D̶e̶l̶e̶t̶e̶");
        deleteRecord.setToolTipText("Delete the selected audio");
        deleteRecord.setEnabled(false);
        deleteRecord.addActionListener(e -> deleteWAV());
        initColors(deleteRecord);
        parentComponent.add(deleteRecord);
    }

    private void deleteWAV() {
        if (audioToLoad != null) {

            String[] path = audioToLoad.split("/");
            String userFirstName = path[3];
            String word = path[5];

            String wordIndex = extractNumberFromWord(path[6]);

            int totalRecordsBeforeDelete = AudioFileManager.numberOfRecordings(
                    userFirstName, word);
            AudioFileManager.deleteRecording(audioToLoad);

            AudioFileManager.renameAudioFiles(userFirstName, word, Integer.valueOf(wordIndex),
                    totalRecordsBeforeDelete);

            updateGUI();

            playAndStopButton.setEnabled(false);
        }
    }

    private String extractNumberFromWord(String string) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                builder.append(string.charAt(i));
            }
        }
        return builder.toString();
    }

    private void initLabelDuration(JComponent parentComponent) {
        initColors(labelDuration);
        labelDuration.setHorizontalAlignment(SwingConstants.CENTER);
        parentComponent.add(labelDuration);
    }

    private void initProgressBar(JComponent parentComponent) {
        timeProgress = new JProgressBar();
        timeProgress.setEnabled(false);
        timeProgress.setValue(0);
        parentComponent.add(timeProgress);
    }

    private void initMediaPlayer(JComponent parentComponent) {
        JPanel playPausePanel = new JPanel(new GridLayout(1, 1));
        playAndStopButton = new JButton(playIcon);

        initColors(playAndStopButton);

        playAndStopButton.setEnabled(false);
        playAndStopButton.addActionListener(e -> {
            if (!isPlaying) {
                play();
            } else {
                stop();
            }
        });

        playPausePanel.add(playAndStopButton);

        parentComponent.add(playPausePanel);
    }

    private void play() {
        timer = new PlayingTimer(labelDuration, timeProgress);
        timer.start();
        isPlaying = true;
        playbackThread = new Thread(() -> {
            try {
                initAudioPlaying();

            } catch (UnsupportedAudioFileException ex) {
                displayExceptionMessage("The audio format is unsupported!");
            } catch (LineUnavailableException ex) {
                displayExceptionMessage("Could not play the audio file because line is unavailable!");
            } catch (IOException ex) {
                displayExceptionMessage("I/O error while playing the audio file!");
            } finally {
                stop();
            }
        });

        playbackThread.start();
    }

    private void initAudioPlaying()
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        playAndStopButton.setIcon(pauseIcon);
        playAndStopButton.setEnabled(true);

        player.load(audioToLoad);
        timer.setAudioClip(player.getAudioClip());
        timeProgress.setMaximum((int) player.getClipSecondLength());

        player.play();
    }

    private void displayExceptionMessage(String errorMessage) {
        JOptionPane.showMessageDialog(AudioMenu.this,
                errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void stop() {
        timer.reset();
        timer.interrupt();

        playAndStopButton.setIcon(playIcon);
        isPlaying = false;

        player.stop();
        playbackThread.interrupt();
    }

    private void initBackButton(JComponent parentComponent) {
        JButton goBackButton = new JButton("Go back");
        goBackButton.setIcon(goBackIcon);
        goBackButton.setToolTipText("Go back to start menu");
        goBackButton.addActionListener(e -> MenuFrameHandler.goToStartMenu());
        goBackButton.setBackground(BACK_BUTTON_COLOR);
        goBackButton.setForeground(TEXT_COLOR);
        parentComponent.add(goBackButton);
    }

    public void setSelectorsToDefaultValue() {
        setRootToAllUsers();
        setWordToAllWords();
    }

    public void setRootToAllUsers() {
        userSelector.setSelectedItem(ALL_USERS);
    }

    private void setWordToAllWords() {
        wordSelector.setSelectedItem("ALL");
    }

    public void updateGUI() {
        updateUserSelector();
        loadFileNavigator();
        loadTotalOfWords();
        loadTotalAudioLength();
        revalidate();
        repaint();
    }
}
