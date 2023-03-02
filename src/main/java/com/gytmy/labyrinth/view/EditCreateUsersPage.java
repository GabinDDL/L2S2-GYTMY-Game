package com.gytmy.labyrinth.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import com.gytmy.sound.User;
import com.gytmy.utils.input.UserInputField;
import com.gytmy.utils.input.UserInputFieldNumberInBounds;

public class EditCreateUsersPage extends JPanel {
    private JFrame frame;

    private JButton save;
    private JButton cancel;
    private UserInputField firstName;
    private UserInputField lastName;
    private UserInputFieldNumberInBounds studentNumber;

    EditCreateUsersPage(JFrame frame) {
        this(frame, null);
    }

    EditCreateUsersPage(JFrame frame, User userToEdit) {
        this.frame = frame;

        setLayout(new GridBagLayout());
        setBackground(Cell.WALL_COLOR);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(15, 0, 0, 0);
        constraints.ipady = 20;

        initFirstNameInput(constraints);
        initLastNameInput(constraints);
        initStudentNumberInput(constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.ipady = 0;

        constraints.insets = new Insets(0, 5, 10, 0);
        initSaveButton(constraints);
        initCancelButton(constraints);
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

    private void initCancelButton(GridBagConstraints constraints) {
        cancel = new JButton("Cancel");
        cancel.setBackground(Cell.INITIAL_CELL_COLOR);

        cancel.addActionListener(e -> {
            frame.setContentPane(new AudioMenu(frame));
            frame.revalidate();
        });

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx = 0.2;

        add(cancel, constraints);
    }

    private void initSaveButton(GridBagConstraints constraints) {
        save = new JButton("Save");
        save.setBackground(Cell.EXIT_CELL_COLOR);
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.weightx = 0.2;

        add(save, constraints);
    }
}
