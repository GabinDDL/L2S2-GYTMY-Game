package com.gytmy;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Settings extends JPanel {
    private JFrame frame;
    private int nbPlayers;

    private JPanel playersPanel;
    private JPanel buttonsPanel;
    private Color[] colors = new Color[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW };

    public Settings(JFrame frame, int nbPlayers) {
        this.frame = frame;
        this.nbPlayers = nbPlayers;

        setLayout(new GridLayout(2, 1));

        initPlayersPanel(nbPlayers);
        initPlayButtons();
    }

    public void initPlayersPanel(int nbPlayers) {
        // FIXME: PlayersPanel is not initialized
        playersPanel = new JPanel(new GridLayout(1, 4));

        for (int playerID = 0; playerID < nbPlayers; ++playerID) {
            JPanel playerPanel = createPlayerPanel(playerID);
            playersPanel.add(playerPanel);
        }

    }

    public JPanel createPlayerPanel(int playerID) {
        JPanel playerPanel = new JPanel(new GridLayout(3, 1));
        addNameSectionToPanel(playerPanel, playerID);
        addColorSectionToPanel(playerPanel, playerID);
        addValidateSectionToPanel(playerPanel, playerID);

        return playerPanel;
    }

    private void addNameSectionToPanel(JPanel playerPanel, int playerID) {

        JPanel nameSection = new JPanel(new GridLayout(1, 2));

        JLabel nameLabel = new JLabel("Name : ");
        nameSection.add(nameLabel);
        JTextField nameField = new JTextField("Player n°" + (playerID + 1));
        nameSection.add(nameField);

        playerPanel.add(nameSection);
    }

    private void addColorSectionToPanel(JPanel playerPanel, int playerID) {
        JPanel colorSection = new JPanel(new GridLayout(1, 2));

        JLabel colorLabel = new JLabel("Color : ");
        colorSection.add(colorLabel);
        JPanel colorTile = new JPanel();
        colorTile.setBackground(colors[playerID]);
        colorSection.add(colorTile);

        playerPanel.add(colorSection);
    }

    private void addValidateSectionToPanel(JPanel playerPanel, int playerID) {
        JButton validateButton = new JButton("Validate");
        playerPanel.add(validateButton);

        validateButton.addActionListener(event -> {
            lockPlayerSettings(playerID);
        });
    }

    private void lockPlayerSettings(int playerID) {
        disableNameSection(playerID);
        disableValidateSection(playerID);
    }

    private void disableNameSection(int playerID) {
        JPanel playerPanel = (JPanel) playersPanel.getComponent(playerID);

        JPanel nameSection = (JPanel) playerPanel.getComponent(0);
        JTextField nameField = (JTextField) nameSection.getComponent(1);
        nameField.setEnabled(false);
    }

    private void disableValidateSection(int playerID) {
        JPanel playerPanel = (JPanel) playersPanel.getComponent(playerID);

        JPanel validateSection = (JPanel) playerPanel.getComponent(2);
        JButton validateButton = (JButton) validateSection.getComponent(0);
        validateButton.setEnabled(false);
    }

    private void initPlayButtons() {
        buttonsPanel = new JPanel(new GridLayout(1, 2));

        initPlayButtonLabyrinth1D();
        initPlayButtonLabyrinth2D();

        add(buttonsPanel);
    }

    private void initPlayButtonLabyrinth1D() {

        JButton playLabyrinth1DButton = new JButton("Play Labyrinth 1D");
        buttonsPanel.add(playLabyrinth1DButton);
        playLabyrinth1DButton.addActionListener(e -> {
            initDimensionPickerLabyrinth1D();
        });

    }

    private void initDimensionPickerLabyrinth1D() {
        JPanel settingsPanel = new JPanel(new GridLayout(2, 1));

        JPanel textPanel = new JPanel();
        JTextField lengthPathInputField = createInputFieldInPanel(
                textPanel, "Enter the length of the path: ");
        settingsPanel.add(textPanel);

        JPanel buttonPanel = new JPanel();
        JButton validateButton = new JButton("Validate");
        validateButton.addActionListener(e -> {
            if (isValidInput1D(lengthPathInputField))
                startGame1D();
        });
        settingsPanel.add(buttonPanel);

        frame.setContentPane(settingsPanel);
        frame.revalidate();
    }

    private boolean isValidInput1D(JTextField field) {
        String strippedString = field.getText().strip();

        if (strippedString.equals(""))
            return false;

        int value = Integer.valueOf(strippedString);
        return 2 <= value && value <= 60;
    }

    private void startGame1D() {
        // TODO: implement startGame1D
    }

    private void initPlayButtonLabyrinth2D() {

        JButton playLabyrinth2DButton = new JButton("Play Labyrinth 2D");
        buttonsPanel.add(playLabyrinth2DButton);
        playLabyrinth2DButton.addActionListener(e -> {
            initDimensionPickerLabyrinth2D();
        });

    }

    private void initDimensionPickerLabyrinth2D() {
        JPanel settingsPanel = new JPanel(new GridLayout(2, 1));

        JPanel textPanel = new JPanel(new GridLayout(2, 2));
        JTextField heightLabyrinthField = createInputFieldInPanel(
                textPanel, "Enter the height of the labyrinth: ");
        JTextField widthLabyrinthField = createInputFieldInPanel(
                textPanel, "Enter the width of the labyrinth: ");
        settingsPanel.add(textPanel);

        JPanel buttonPanel = new JPanel();
        JButton validateButton = new JButton("Validate");
        validateButton.addActionListener(e -> {
            if (isValidInputDimension(heightLabyrinthField,
                    widthLabyrinthField))
                startGame2D();
        });
        settingsPanel.add(buttonPanel);

        frame.setContentPane(settingsPanel);
        frame.revalidate();
    }

    private boolean isValidInputDimension(JTextField height, JTextField width) {
        return isValidInput2D(height) &&
                isValidInput2D(width);
    }

    private boolean isValidInput2D(JTextField field) {
        String strippedString = field.getText().strip();

        if (strippedString.equals(""))
            return false;

        int value = Integer.valueOf(strippedString);
        return 4 <= value && value <= 20;
    }

    public void startGame2D() {
        // TODO: implement startGame2D
    }

    private JTextField createInputFieldInPanel(JPanel panel, String instructions) {
        JLabel label = new JLabel(instructions);
        panel.add(label);
        JTextField textField = new JTextField(5);
        panel.add(textField);

        return textField;
    }

}
