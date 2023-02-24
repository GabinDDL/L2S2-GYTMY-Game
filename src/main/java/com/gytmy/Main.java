package com.gytmy;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.LabyrinthModelImplementation;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.player.PlayerImplementation;
import com.gytmy.labyrinth.view.LabyrinthView;
import com.gytmy.labyrinth.view.LabyrinthViewImplementation;
import com.gytmy.launcher.GraphicalLauncher;
import com.gytmy.utils.Boolean2DArraysOperations;

public class Main {

    public static void main(String[] args) {
        // EventQueue.invokeLater(new GraphicalLauncher());

        // Tests
        Player[] players = new PlayerImplementation[] {
                new PlayerImplementation(1, 1),
                new PlayerImplementation(1, 1),
                new PlayerImplementation(1, 1),
                new PlayerImplementation(1, 1),
                new PlayerImplementation(1, 1),
        };

        LabyrinthModel model = new LabyrinthModelImplementation(31, 31, players);
        LabyrinthView view = new LabyrinthViewImplementation(model);

        Boolean2DArraysOperations.print(model.getBoard());

        JFrame frame = new JFrame();
        frame.setContentPane(view.getLabyrinthPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        EventQueue.invokeLater(() -> {
            try {
                Thread.sleep(500);
                players[0].setColor(Color.BLUE);
                players[1].setColor(Color.GRAY);
                players[2].setColor(Color.ORANGE);
                players[3].setColor(Color.PINK);
                players[4].setColor(Color.YELLOW);

                Thread.sleep(500);
                model.movePlayer(players[0], Direction.DOWN);
                view.update(players[0], Direction.DOWN);

                Thread.sleep(500);
                model.movePlayer(players[1], Direction.DOWN);
                view.update(players[1], Direction.DOWN);

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            EventQueue.invokeLater(() -> {
                try {
                    Thread.sleep(500);
                    model.movePlayer(players[2], Direction.DOWN);
                    view.update(players[2], Direction.DOWN);

                    Thread.sleep(500);
                    model.movePlayer(players[3], Direction.DOWN);
                    view.update(players[3], Direction.DOWN);

                    Thread.sleep(500);
                    model.movePlayer(players[4], Direction.DOWN);
                    view.update(players[4], Direction.DOWN);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            });
            ;
            Boolean2DArraysOperations.print(model.getBoard());
        });

    }

}
