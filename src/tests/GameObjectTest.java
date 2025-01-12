package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.WorldPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameObjectTest {
    private Player player;
    private FighterMonster fighter;

    @BeforeEach
    public void setup() {
        RokueLikeGame.getInstance().resetGame();
        player = new Player();
        fighter = new FighterMonster();
        RokueLikeGame.getInstance().addGameObject(player);
        RokueLikeGame.getInstance().addGameObject(fighter);
    }

    @Test
    public void testCollisionWithFighterDecreasesLife() {
        fighter.setPosition(player.getPosition());
        player.checkForCollisions();
        assertEquals(2, player.getLives());
    }

    @Test
    public void testCollisionWithOtherObjectNoEffect() {
        GameObject wall = new Wall(new WorldPosition(2, 2), "wall.png");
        wall.setPosition(player.getPosition());
        player.checkForCollisions();
        assertEquals(3, player.getLives());
    }

    @Test
    public void testNoDoubleLifeDecreaseInCollision() {
        fighter.setPosition(player.getPosition());
        player.checkForCollisions();
        player.checkForCollisions();
        assertEquals(2, player.getLives());
    }
}