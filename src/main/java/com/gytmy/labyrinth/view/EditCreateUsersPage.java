package com.gytmy.labyrinth.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import com.gytmy.sound.User;
import com.gytmy.utils.input.UserInputField;
import com.gytmy.utils.input.UserInputFieldNumberInBounds;

public class EditCreateUsersPage extends JPanel {
    private JFrame frame;

    private User userToEdit;

    private JButton save;
    private JButton cancel;
    private UserInputField firstName;
    private UserInputField lastName;
    private UserInputFieldNumberInBounds studentNumber;
    private UserInputField userName;

    EditCreateUsersPage(JFrame frame) {
        this(frame, null);
    }

    EditCreateUsersPage(JFrame frame, User userToEdit) {
        this.frame = frame;

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
        initSaveButton(constraints);
        initCancelButton(constraints);
    }

    private void setTextValues() {
        if (userToEdit != null) {
            firstName.setText(userToEdit.getFirstName());
            lastName.setText(userToEdit.getLastName());
            studentNumber.setValue(userToEdit.getStudentNumber());
        }
    }

    private void addListenerToButtons() {
        firstName.getTextField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (firstName.getText().equals("First name")) {
                    firstName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (firstName.getText().equals("")) {
                    firstName.setText("First name");
                }
            }
        });

        lastName.getTextField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (lastName.getText().equals("Last name")) {
                    lastName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (lastName.getText().equals("")) {
                    lastName.setText("Last name");
                }
            }
        });

        studentNumber.getTextField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Integer.valueOf(studentNumber.getText()) == 0) {
                    studentNumber.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (studentNumber.getText().equals("") || Integer.valueOf(studentNumber.getText()) == 0) {
                    studentNumber.setText("0");
                }

                studentNumber.setValue(Integer.valueOf(studentNumber.getText()));
            }
        });

        userName.getTextField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userName.getText().equals("Username")) {
                    userName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userName.getText().equals("")) {
                    userName.setText("Username");
                }
            }
        });
    }

    private void initFirstNameInput(GridBagConstraints constraints) {
        firstName = new UserInputField("First name");
        firstName.setBackground(Cell.PATH_COLOR);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.7;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(firstName.getTextField(), constraints);
    }

    private void initLastNameInput(GridBagConstraints constraints) {
        lastName = new UserInputField("Last name");
        lastName.setBackground(Cell.PATH_COLOR);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 0.7;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(lastName.getTextField(), constraints);
    }

    private void initStudentNumberInput(GridBagConstraints constraints) {
        studentNumber = new UserInputFieldNumberInBounds(0, 99999999);
        studentNumber.setBackground(Cell.PATH_COLOR);
        studentNumber.setValue(0);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.weightx = 0.7;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(studentNumber.getTextField(), constraints);
    }

    private void initUserNameInput(GridBagConstraints constraints) {
        userName = new UserInputField("Username");
        userName.setBackground(Cell.PATH_COLOR);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.weightx = 0.7;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(userName.getTextField(), constraints);
    }

    private void initCancelButton(GridBagConstraints constraints) {
        cancel = new JButton("Cancel");
        cancel.setBackground(Cell.INITIAL_CELL_COLOR);

        cancel.addActionListener(e -> {
            frame.setContentPane(new AudioMenu(frame));
            frame.revalidate();
        });

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.weightx = 0.2;

        add(cancel, constraints);
    }

    private void initSaveButton(GridBagConstraints constraints) {
        save = new JButton("Save");
        save.setBackground(Cell.EXIT_CELL_COLOR);
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.weightx = 0.2;

        add(save, constraints);
    }
}
