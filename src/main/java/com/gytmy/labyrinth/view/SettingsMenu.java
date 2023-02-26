package com.gytmy.labyrinth.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gytmy.labyrinth.controller.LabyrinthController;
import com.gytmy.labyrinth.controller.LabyrinthControllerImplementation;
import com.gytmy.labyrinth.model.GameData;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.player.PlayerImplementation;
import com.gytmy.utils.Coordinates;
import com.gytmy.utils.input.InputField;
import com.gytmy.utils.input.UserInputField;
import com.gytmy.utils.input.UserInputFieldNumberInBounds;

public class SettingsMenu extends JPanel {

    private JFrame frame;
    private int nbPlayers;
    private Player[] arrayPlayers;

    private JPanel playersPanel;
    private JPanel buttonsPanel;
    private Color[] colors = new Color[] {
            Color.decode("#b13e53"),
            Color.decode("#ffcd75"),
            Color.decode("#38b764"),
            Color.decode("#41a6f6"),
            Color.decode("#29366f")
    };

    private UserInputFieldNumberInBounds[] arrayUserInputFields;

    private int selectedDimension;
    private GameData gameData;

    public SettingsMenu(JFrame frame, int nbPlayers) {
        this.frame = frame;
        this.nbPlayers = nbPlayers;
        arrayPlayers = new Player[nbPlayers];

        setLayout(new BorderLayout());

        initPlayersPanel();
        initPlayButtons();
    }

    private void initPlayersPanel() {
        playersPanel = new JPanel(new GridLayout(1, nbPlayers));

        for (int playerID = 0; playerID < nbPlayers; ++playerID) {
            JPanel playerPanel = createPlayerPanel(playerID);
            playersPanel.add(playerPanel);
        }

        add(playersPanel, BorderLayout.CENTER);
    }

    private JPanel createPlayerPanel(int playerID) {
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
        UserInputField nameField = new UserInputField("Player n°" + (playerID + 1));
        initNameFieldFocusListener(nameField, playerID);

        nameSection.add(nameField.getTextField());

        playerPanel.add(nameSection);
    }

    private void initNameFieldFocusListener(UserInputField nameField, int playerID) {
        nameField.getTextField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("Player n°" + (playerID + 1)))
                    nameField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameField.getText().isEmpty())
                    nameField.setText("Player n°" + (playerID + 1));
            }
        });
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
        initValidateButtonActionListener(validateButton, playerID);
    }

    private void initValidateButtonActionListener(JButton validateButton, int playerID) {
        validateButton.addActionListener(event -> lockPlayerSettings(playerID));
    }

    private void lockPlayerSettings(int playerID) {
        disableNameSection(playerID);
        disableValidateSection(playerID);
        initPlayer(playerID);
    }

    private void disableNameSection(int playerID) {
        JTextField nameField = getNameField(playerID);
        nameField.setEnabled(false);
    }

    private JTextField getNameField(int playerID) {
        JPanel playerPanel = (JPanel) playersPanel.getComponent(playerID);
        JPanel nameSection = (JPanel) playerPanel.getComponent(0);
        JTextField nameField = (JTextField) nameSection.getComponent(1);

        return nameField;
    }

    private void disableValidateSection(int playerID) {

        JPanel playerPanel = (JPanel) playersPanel.getComponent(playerID);
        JButton validateButton = (JButton) playerPanel.getComponent(2);
        validateButton.setEnabled(false);
    }

    private void initPlayer(int playerID) {
        Coordinates coordinates = new Coordinates(
                Coordinates.UNINITIALIZED_COORDINATE,
                Coordinates.UNINITIALIZED_COORDINATE);
        String name = getNameField(playerID).getText();
        Color color = getPlayerColor(playerID);
        boolean ready = true;

        Player player = new PlayerImplementation(
                coordinates,
                name,
                color,
                ready);

        arrayPlayers[playerID] = player;
    }

    private Color getPlayerColor(int playerID) {
        return colors[playerID];
    }

    private void initPlayButtons() {
        buttonsPanel = new JPanel(new GridLayout(1, 2));

        initPlayButtonDimension(1);
        initPlayButtonDimension(2);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void initPlayButtonDimension(int dimension) {
        JButton playButton = new JButton("Play Labyrinth " + dimension + "D");
        buttonsPanel.add(playButton);
        addActionListenerToPlayButton(playButton, dimension);
    }

    private void addActionListenerToPlayButton(JButton playButton, int dimension) {
        playButton.addActionListener(e -> {
            selectedDimension = dimension;
            if (Player.areAllPlayersReady(arrayPlayers))
                initDimensionPicker();

        });
    }

    private void initDimensionPicker() {
        JPanel settingsPanel = new JPanel(new BorderLayout());
        initInputPanel(settingsPanel);
        initValidateButtonDimensionPicker(settingsPanel);

        frame.setContentPane(settingsPanel);
        GameFrameToolbox.frameUpdate(frame, "Size Picker");
    }

    private void initInputPanel(JPanel settingsPanel) {
        arrayUserInputFields = new UserInputFieldNumberInBounds[selectedDimension];
        JPanel textPanel = new JPanel(new GridLayout(selectedDimension, 2));

        // capped upperBound to 40 to limit the size of the labyrinth
        UserInputFieldNumberInBounds widthLabyrinthInput = new UserInputFieldNumberInBounds(2, 40);
        arrayUserInputFields[0] = widthLabyrinthInput;
        addInputFieldInPanel(widthLabyrinthInput, textPanel, "Enter the width of the labyrinth: ");

        if (selectedDimension == 2) {
            // capped upperBound to 40 to limit the size of the labyrinth
            UserInputFieldNumberInBounds heightLabyrinthInput = new UserInputFieldNumberInBounds(2, 40);
            arrayUserInputFields[1] = heightLabyrinthInput;
            addInputFieldInPanel(heightLabyrinthInput, textPanel, "Enter the height of the labyrinth: ");
        }

        settingsPanel.add(textPanel, BorderLayout.CENTER);
    }

    private void addInputFieldInPanel(InputField inputField, JPanel panel, String instructions) {
        JLabel label = new JLabel(instructions);
        panel.add(label);

        JTextField textField = inputField.getTextField();
        panel.add(textField);
    }

    private void initValidateButtonDimensionPicker(JPanel settingsPanel) {
        JButton validateButton = new JButton("Validate");
        validateButton.addActionListener(e -> {
            if (InputField.areAllValidInputs(arrayUserInputFields)) {
                int[] size = getSizeValues();
                initGameData(size);
                startGame();
            }
        });
        settingsPanel.add(validateButton, BorderLayout.SOUTH);
    }

    private int[] getSizeValues() {
        int[] size = new int[selectedDimension];
        for (int i = 0; i < size.length; i++) {
            size[i] = arrayUserInputFields[i].getValue();
        }
        return size;
    }

    private void initGameData(int[] size) {
        if (selectedDimension == 1) {
            gameData = new GameData(size[0], arrayPlayers);
        } else {
            gameData = new GameData(size[0], size[1], arrayPlayers);
        }
    }

    private void startGame() {
        LabyrinthController labyrinthController = new LabyrinthControllerImplementation(gameData);
        LabyrinthView labyrinthView = labyrinthController.getView();

        frame.setContentPane(labyrinthView);
        GameFrameToolbox.frameUpdate(frame, "View Labyrinth" + selectedDimension + "D");
    }
}
