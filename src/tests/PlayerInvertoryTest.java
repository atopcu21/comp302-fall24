package org.firstgame.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInventoryTest {
    @Test
    void testAddToInventory() {
        Player player = new Player();
        player.setInventory(new ArrayList<>());

        GameObject item = new GameObject();
        player.addToInventory(item);

        assertEquals(1, player.getInventory().size());
        assertEquals(item, player.getInventory().get(0));
    }
}