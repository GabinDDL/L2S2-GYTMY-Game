package com.gytmy.launcher;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gytmy.utils.GameFrameToolbox;
import com.gytmy.utils.input.UserInputFieldRange;

public class StartMenu extends JPanel {
  private JFrame frame;
  private JLabel askNbPlayers;
  private JPanel textPanel;
  private UserInputFieldRange nbPlayersField;

  StartMenu(JFrame frame) {
    this.frame = frame;
    initMenu();
  }

  private void initMenu() {
    setLayout(new BorderLayout());
    initTextField();
    initPlayerSettingsButton();
  }

  private void initTextField() {
    textPanel = new JPanel(new GridLayout(1, 1));
    askNbPlayers = new JLabel("Enter the number of players: ");
    textPanel.add(askNbPlayers);
    nbPlayersField = new UserInputFieldRange(1, 4);
    textPanel.add(nbPlayersField.getTextField());
    add(textPanel, BorderLayout.CENTER);
  }

  private void initPlayerSettingsButton() {
    JButton playButton = new JButton("Next");
    playButton.addActionListener(event -> {
      if (nbPlayersField.isValidInput()) {
        int nbPlayers = nbPlayersField.getValue();
        frame.setContentPane(new SettingsMenu(frame, nbPlayers));
        String className = this.getClass().getSimpleName();
        GameFrameToolbox.frameUpdate(frame, className);
      }
    });
    add(playButton, BorderLayout.SOUTH);
  }

}
