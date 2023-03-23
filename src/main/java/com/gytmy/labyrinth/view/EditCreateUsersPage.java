package com.gytmy.labyrinth.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import com.gytmy.sound.AudioFileManager;
import com.gytmy.sound.User;
import com.gytmy.utils.input.UserInputField;
import com.gytmy.utils.input.UserInputFieldNumberInBounds;

public class EditCreateUsersPage extends JPanel {
    private JFrame frame;
    private AudioMenu audioMenu;

    private User userToEdit;

    private JButton saveOrCreate;
    private JButton cancel;
    private UserInputField firstName;
    private UserInputField lastName;
    private UserInputFieldNumberInBounds studentNumber;
    private UserInputField userName;

    private static final String DEFAULT_FIRST_NAME_TEXT = "First name";
    private static final String DEFAULT_LAST_NAME_TEXT = "Last name";
    private static final int DEFAULT_STUDENT_NUMBER_VALUE = 0;
    private static final String DEFAULT_USER_NAME_TEXT = "User name";

    EditCreateUsersPage(JFrame frame, AudioMenu audioMenu) {
        this(frame, audioMenu, null);
    }

    EditCreateUsersPage(JFrame frame, AudioMenu audioMenu, User userToEdit) {
        this.frame = frame;
        this.audioMenu = audioMenu;

        this.userToEdit = userToEdit;

        setLayout(new GridBagLayout());
        setBackground(Cell.WALL_COLOR);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(15, 0, 0, 0);
        constraints.ipady = 20;

        initFirstNameInput(constraints);
        initLastNameInput(constraints);
        initStudentNumberInput(constraints);
        initUserNameInput(constraints);

        setTextValues();
        addListenerToButtons();

        constraints.fill = GridBagConstraints.NONE;
        constraints.ipady = 0;

        constraints.insets = new Insets(0, 5, 10, 0);
        initSaveCreateButton(constraints);
        initCancelButton(constraints);
    }

    private void setTextValues() {
        if (userToEdit != null) {
            firstName.setText(userToEdit.getFirstName());
            lastName.setText(userToEdit.getLastName());
            studentNumber.setValue(userToEdit.getStudentNumber());
            userName.setText(userToEdit.getUserName());
        }
    }

    private void addListenerToButtons() {
        addFocusListenerTo(firstName, DEFAULT_FIRST_NAME_TEXT);

        addFocusListenerTo(lastName, DEFAULT_LAST_NAME_TEXT);

        addNumberFocusListenerTo(studentNumber, DEFAULT_STUDENT_NUMBER_VALUE);

        addFocusListenerTo(userName, DEFAULT_USER_NAME_TEXT);
    }

    private void addFocusListenerTo(UserInputField field, String defaultText) {
        field.getTextField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(defaultText)) {
                    field.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (isTextFieldInvalid(field.getText())) {
                    field.setText(defaultText);
                }
            }
        });
    }

    private void addNumberFocusListenerTo(UserInputFieldNumberInBounds field, int defaultValue) {
        field.getTextField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getValue() == defaultValue) {
                    field.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (isTextFieldInvalid(field.getText())) {
                    field.setValue(defaultValue);
                } else if (field.getValue() > field.getUpperBound()) {
                    field.setValue(field.getUpperBound());
                }
            }
        });
    }

    protected boolean isTextFieldInvalid(String text) {
        return text.isBlank();
    }

    private void initFirstNameInput(GridBagConstraints constraints) {
        firstName = new UserInputField(DEFAULT_FIRST_NAME_TEXT);
        firstName.setBackground(Cell.PATH_COLOR);
        setConstraints(constraints, 1, 1, 0.7, 0.5);
        add(firstName.getTextField(), constraints);
    }

    private void initLastNameInput(GridBagConstraints constraints) {
        lastName = new UserInputField(DEFAULT_LAST_NAME_TEXT);
        lastName.setBackground(Cell.PATH_COLOR);
        setConstraints(constraints, 1, 2, 0.7, 0.5);
        add(lastName.getTextField(), constraints);
    }

    private void initStudentNumberInput(GridBagConstraints constraints) {
        studentNumber = new UserInputFieldNumberInBounds(DEFAULT_STUDENT_NUMBER_VALUE, 99999999);
        studentNumber.setBackground(Cell.PATH_COLOR);
        studentNumber.setValue(0);
        setConstraints(constraints, 1, 3, 0.7, 0.5);
        add(studentNumber.getTextField(), constraints);
    }

    private void initUserNameInput(GridBagConstraints constraints) {
        userName = new UserInputField(DEFAULT_USER_NAME_TEXT);
        userName.setBackground(Cell.PATH_COLOR);
        setConstraints(constraints, 1, 4, 0.7, 0.5);
        add(userName.getTextField(), constraints);
    }

    private void setConstraints(GridBagConstraints constraints, int x, int y, double weightX, double weightY) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.weightx = weightX;
        constraints.weighty = weightY;
        constraints.fill = GridBagConstraints.HORIZONTAL;
    }

    private void initCancelButton(GridBagConstraints constraints) {
        cancel = new JButton("Cancel");
        cancel.setBackground(Cell.INITIAL_CELL_COLOR);

        cancel.addActionListener(e -> MenuFrameHandler.goToAudioMenu());

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.weightx = 0.2;

        add(cancel, constraints);
    }

    private void initSaveCreateButton(GridBagConstraints constraints) {
        saveOrCreate = new JButton(userToEdit == null ? "Create" : "Save");
        saveOrCreate.setBackground(Cell.EXIT_CELL_COLOR);

        saveOrCreate.addActionListener(e -> {

            if (!inputsAreValid()) {
                JOptionPane.showMessageDialog(this, "Please fill correctly all fields", "Attention",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            User user = createUser();

            if (AudioFileManager.getUsers().contains(user)) {
                JOptionPane.showMessageDialog(this, "User already exists", "Attention",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (userToEdit == null) {
                AudioFileManager.addUser(user);
            } else {
                AudioFileManager.editUser(userToEdit, user);
            }

            MenuFrameHandler.goToAudioMenu();
        });

        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.weightx = 0.2;

        add(saveOrCreate, constraints);
    }

    private User createUser() {
        return new User(firstName.getText(), lastName.getText(), Integer.valueOf(studentNumber.getText()),
                userName.getText());
    }

    private boolean inputsAreValid() {
        return !firstName.getText().equals("First name") && !lastName.getText().equals("Last name")
                && Integer.valueOf(studentNumber.getText()) != 0 && !userName.getText().equals("Username");
    }
}
