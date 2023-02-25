package com.gytmy.labyrinth.model.player;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import com.gytmy.labyrinth.model.Direction;

public class TestPlayerImplementation {

    @Test
    public void testConstructor() {

        Player player = new PlayerImplementation(0, 0);
        assertTrue(player.getX() == 0 && player.getY() == 0);
    }

    @Test
    public void testMoveUp() {
        Player player = new PlayerImplementation(0, 0);
        player.move(Direction.UP);
        assertTrue(player.getX() == 0 && player.getY() == -1);

        player = new PlayerImplementation(1, 1);
        player.move(Direction.UP);
        assertTrue(player.getX() == 1 && player.getY() == 0);
    }

    @Test
    public void testMoveDown() {
        Player player = new PlayerImplementation(0, 0);
        player.move(Direction.DOWN);
        assertTrue(player.getX() == 0 && player.getY() == 1);

        player = new PlayerImplementation(1, 1);
        player.move(Direction.DOWN);
        assertTrue(player.getX() == 1 && player.getY() == 2);
    }

    @Test
    public void testMoveLeft() {
        Player player = new PlayerImplementation(0, 0);
        player.move(Direction.LEFT);
        assertTrue(player.getX() == -1 && player.getY() == 0);

        player = new PlayerImplementation(1, 1);
        player.move(Direction.LEFT);
        assertTrue(player.getX() == 0 && player.getY() == 1);
    }

    @Test
    public void testMoveRight() {
        Player player = new PlayerImplementation(0, 0);
        player.move(Direction.RIGHT);
        assertTrue(player.getX() == 1 && player.getY() == 0);

        player = new PlayerImplementation(1, 1);
        player.move(Direction.RIGHT);
        assertTrue(player.getX() == 2 && player.getY() == 1);
    }

}
