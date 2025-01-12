package org.firstgame.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerLivesTest {
    @Test
    void testIncreaseLives() {
        Player player = new Player();
        player.setLives(3);
        player.increaseLives();
        assertEquals(4, player.getLives());

        // Should not increase beyond 4
        player.increaseLives();
        assertEquals(4, player.getLives());
    }

    @Test
    void testDecreaseLives() {
        Player player = new Player();
        player.setLives(3);
        player.decreaseLives();
        assertEquals(2, player.getLives());

        player.decreaseLives();
        player.decreaseLives();
        assertEquals(0, player.getLives());

        // Should not go below 0
        player.decreaseLives();
        assertEquals(0, player.getLives());
    }
}