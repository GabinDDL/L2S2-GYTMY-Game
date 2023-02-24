package com.gytmy;

import java.awt.EventQueue;

import com.gytmy.labyrinth.model.Direction;
import com.gytmy.labyrinth.model.LabyrinthModel;
import com.gytmy.labyrinth.model.LabyrinthModelImplementation;
import com.gytmy.labyrinth.model.player.Player;
import com.gytmy.labyrinth.model.player.PlayerImplementation;
import com.gytmy.labyrinth.view.LabyrinthView;
import com.gytmy.labyrinth.view.LabyrinthViewImplementation;
import com.gytmy.launcher.GraphicalLauncher;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new GraphicalLauncher());

        // Tests
        // Player[] players = new PlayerImplementation[] {
        // new PlayerImplementation(2, 2),
        // new PlayerImplementation(2, 2),
        // new PlayerImplementation(2, 2),
        // new PlayerImplementation(2, 2),
        // };

        // LabyrinthModel model = new LabyrinthModelImplementation(31, 31, players);

        // LabyrinthView view = new LabyrinthViewImplementation(model);

        // EventQueue.invokeLater(() -> {
        // try {
        // Thread.sleep(2000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // view.update(players[3], Direction.DOWN);
        // });

    }

}
