package com.gytmy;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import com.gytmy.labyrinth.controller.LabyrinthController;
import com.gytmy.labyrinth.controller.LabyrinthControllerImplementation;
import com.gytmy.labyrinth.model.GameData;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.player.PlayerImplementation;
import com.gytmy.labyrinth.view.LabyrinthView;
import com.gytmy.launcher.GraphicalLauncher;
import com.gytmy.utils.Coordinates;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(new GraphicalLauncher());

        // Player player1 = new PlayerImplementation(new Coordinates(1, 1), "Grep",
        // Color.BLACK, true);

        // int length = 10;
        // int height = 10;

        // GameData data = new GameData(length, height, new Player[] { player1 });
        // LabyrinthController labyrinthController = new
        // LabyrinthControllerImplementation(data);
        // LabyrinthView labyrinthView = labyrinthController.getView();

        // JFrame frame = new JFrame("Labyrinth");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.add(labyrinthView);
        // frame.pack();
        // frame.setLocationRelativeTo(null);
        // frame.setVisible(true);

    }

}
