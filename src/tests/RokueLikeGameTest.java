package org.firstgame;

import org.firstgame.entities.Player;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RokueLikeGameTest {
    private RokueLikeGame game;

    @BeforeEach
    public void setup() {
        game = RokueLikeGame.getInstance();
        game.resetGame();
    }

    @Test
    public void testMovePlayerLeft() {
        Player player = game.getPlayer();
        player.setPosition(5, 5);
        game.keyPressTriggered(37); // Left arrow key
        game.movePlayer();
        assertTrue(player.getPosition().getX() < 5);
    }

    @Test
    public void testMovePlayerRight() {
        Player player = game.getPlayer();
        player.setPosition(5, 5);
        game.keyPressTriggered(39); // Right arrow key
        game.movePlayer();
        assertTrue(player.getPosition().getX() > 5);
    }

    @Test
    public void testDiagonalMovePlayer() {
        Player player = game.getPlayer();
        player.setPosition(5, 5);
        game.keyPressTriggered(37); // Left arrow key
        game.keyPressTriggered(38); // Up arrow key
        game.movePlayer();
        assertTrue(player.getPosition().getX() < 5 && player.getPosition().getY() < 5);
    }
}