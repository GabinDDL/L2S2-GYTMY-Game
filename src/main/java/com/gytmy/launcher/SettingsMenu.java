package com.gytmy.launcher;

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

import com.gytmy.labyrinth.LabyrinthController;
import com.gytmy.labyrinth.LabyrinthPanel;
import com.gytmy.labyrinth.LabyrinthView;
import com.gytmy.labyrinth.Player;
import com.gytmy.labyrinth.PlayerImplementation;
import com.gytmy.utils.Toolbox;
import com.gytmy.utils.Vector2;
import com.gytmy.utils.input.InputField;
import com.gytmy.utils.input.UserInputField;
import com.gytmy.utils.input.UserInputFieldRange;
import com.gytmy.utils.launcher.GameData;

public class SettingsMenu extends JPanel {
  private JFrame frame;
  private int nbPlayers;
  private Player[] arrayPlayers;

  private JPanel playersPanel;
  private JPanel buttonsPanel;
  private Color[] colors = new Color[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PINK };

  private GameData gameData;
  private LabyrinthController labyrinthControllerImplementation;

  // TODO: Refactor buttons with actionListeners should call controllers
  // TODO: Create submethods when adding Listeners
  public SettingsMenu(JFrame frame, int nbPlayers) {
    this.frame = frame;
    this.nbPlayers = nbPlayers;
    arrayPlayers = new Player[nbPlayers];

    setLayout(new BorderLayout());

    initPlayersPanel();
    initPlayButtons();
  }

  public void initPlayersPanel() {
    playersPanel = new JPanel(new GridLayout(1, 4));

    for (int playerID = 0; playerID < nbPlayers; ++playerID) {
      JPanel playerPanel = createPlayerPanel(playerID);
      playersPanel.add(playerPanel);
    }

    add(playersPanel, BorderLayout.CENTER);
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

    validateButton.addActionListener(event -> {
      lockPlayerSettings(playerID);
    });
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

  public void initPlayer(int playerID) {

    // TODO: Instanciate startCell coordinates after instanciation of Labyrinth
    Vector2 coordinates = new Vector2(
        Vector2.UNINITIALIZED_COORDINATE,
        Vector2.UNINITIALIZED_COORDINATE);
    String name = getNameField(playerID).getText();
    Color color = getPlayerColorFromPanel(playerID);
    boolean ready = true;

    Player player = new PlayerImplementation(
        coordinates,
        name,
        color,
        ready);

    arrayPlayers[playerID] = player;
  }

  private Color getPlayerColorFromPanel(int playerID) {
    JPanel playerPanel = (JPanel) playersPanel.getComponent(playerID);
    JPanel colorSection = (JPanel) playerPanel.getComponent(1);
    JPanel colorPanel = (JPanel) colorSection.getComponent(1);
    Color color = colorPanel.getBackground();

    return color;
  }

  private void initPlayButtons() {
    buttonsPanel = new JPanel(new GridLayout(1, 2));

    initPlayButtonLabyrinth1D();
    initPlayButtonLabyrinth2D();

    add(buttonsPanel, BorderLayout.SOUTH);
  }

  private void initPlayButtonLabyrinth1D() {

    JButton playLabyrinth1DButton = new JButton("Play Labyrinth 1D");
    buttonsPanel.add(playLabyrinth1DButton);
    playLabyrinth1DButton.addActionListener(e -> {
      if (Player.areAllPlayersReady(arrayPlayers)) {
        initDimensionPickerLabyrinth1D();
      }
    });

  }

  private void initDimensionPickerLabyrinth1D() {
    JPanel settingsPanel = new JPanel(new BorderLayout());

    JPanel textPanel = new JPanel(new GridLayout(1, 2));

    UserInputFieldRange lengthLabyrinthInput = new UserInputFieldRange(2, 40);
    addInputFieldInPanel(lengthLabyrinthInput, textPanel, "Enter the length of the path: ");

    settingsPanel.add(textPanel, BorderLayout.CENTER);

    JButton validateButton = new JButton("Validate");
    validateButton.addActionListener(e -> {
      // Is it better to check the inputs in startGame1D ?
      if (lengthLabyrinthInput.isValidInput()) {
        int length = lengthLabyrinthInput.getValue();
        startGame1D(length);
      }
    });
    settingsPanel.add(validateButton, BorderLayout.SOUTH);

    frame.setContentPane(settingsPanel);
    Toolbox.frameUpdate(frame, "Be AMazed (Length Picker)");
  }

  private void startGame1D(int length) {
    gameData = new GameData(arrayPlayers, length);
    labyrinthControllerImplementation = new LabyrinthControllerImplementation(gameData);
    LabyrinthView labyrinthView = labyrinthControllerImplementation.getView();
    LabyrinthPanel tilePanel = labyrinthView.getTilePanel();

    frame.setContentPane(tilePanel);
    Toolbox.frameUpdate(frame, "Be AMazed (View Labyrinth1D)");
  }

  private void initPlayButtonLabyrinth2D() {

    JButton playLabyrinth2DButton = new JButton("Play Labyrinth 2D");
    buttonsPanel.add(playLabyrinth2DButton);
    playLabyrinth2DButton.addActionListener(e -> {
      if (Player.areAllPlayersReady(arrayPlayers)) {
        initDimensionPickerLabyrinth2D();
      }
    });

  }

  private void initDimensionPickerLabyrinth2D() {
    JPanel settingsPanel = new JPanel(new BorderLayout());

    JPanel textPanel = new JPanel(new GridLayout(2, 2));

    UserInputFieldRange heightLabyrinthInput = new UserInputFieldRange(2, 40);
    addInputFieldInPanel(heightLabyrinthInput, textPanel, "Enter the vertical length of the labyrinth: ");

    UserInputFieldRange widthLabyrinthInput = new UserInputFieldRange(2, 40);
    addInputFieldInPanel(widthLabyrinthInput, textPanel, "Enter the horizontal length of the labyrinth: ");

    settingsPanel.add(textPanel, BorderLayout.CENTER);

    JButton validateButton = new JButton("Validate");
    validateButton.addActionListener(e -> {
      // Is it better to check the inputs in startGame2D ?
      if (InputField.areAllValidInputs(
          widthLabyrinthInput, heightLabyrinthInput)) {
        startGame2D(widthLabyrinthInput.getValue(), heightLabyrinthInput.getValue());
      }
    });
    settingsPanel.add(validateButton, BorderLayout.SOUTH);

    frame.setContentPane(settingsPanel);
    Toolbox.frameUpdate(frame, "Be AMazed (Dimension Picker)");
  }

  public void startGame2D(int width, int height) {
    gameData = new GameData(arrayPlayers, width, height);
    labyrinthControllerImplementation = new LabyrinthControllerImplementation(gameData);
    LabyrinthView labyrinthView = labyrinthControllerImplementation.getView();
    LabyrinthPanel tilePanel = labyrinthView.getTilePanel();

    frame.setContentPane(tilePanel);
    Toolbox.frameUpdate(frame, "Be AMazed (View Labyrinth2D)");
  }

  private void addInputFieldInPanel(InputField inputField, JPanel panel, String instructions) {
    JLabel label = new JLabel(instructions);
    panel.add(label);

    JTextField textField = inputField.getTextField();
    panel.add(textField);
  }

}
