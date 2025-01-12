package org.firstgame.entities;

import org.firstgame.properties.Rotation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerMovementTest {
    @Test
    void testPlayerMovement() {
        Player player = new Player();
        player.move(Rotation.UP);
        assertEquals(1, player.getPosition().getX(), 0.01);
        assertTrue(player.getPosition().getY() < 1);

        player.move(Rotation.DOWN);
        assertEquals(1, player.getPosition().getX(), 0.01);
        assertTrue(player.getPosition().getY() > 1);

        player.move(Rotation.LEFT);
        assertTrue(player.getPosition().getX() < 1);
        assertEquals(1, player.getPosition().getY(), 0.01);

        player.move(Rotation.RIGHT);
        assertTrue(player.getPosition().getX() > 1);
        assertEquals(1, player.getPosition().getY(), 0.01);
    }
}