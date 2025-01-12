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

        @Test
    public void tesCollisionWhenObjectsAreFar() {
        // Set positions far apart
        fighter.setPosition(new WorldPosition(100, 100));
        player.setPosition(new WorldPosition(0, 0));
        
        player.checkForCollisions();
        // Lives should remain the same since no collision happened
        assertEquals(3, player.getLives());
    }
    
    @Test
    public void testCollisionWithCloakGrantsCloakToPlayer() {
        GameObject cloak = new GameObject(new WorldPosition(1, 1), "src/main/java/org/firstgame/assets/cloak.png");
        cloak.setPosition(player.getPosition()); // Simulate same position
        
        player.checkForCollisions();
        
        // Verify that the player has obtained the cloak
        assertTrue(RokueLikeGame.getInstance().getPlayer().hasCloak());
    }
    
    @Test
    public void testPlayerFirstAidKitCollisionIncreasesLives() {
        GameObject firstAidKit = new GameObject(new WorldPosition(2, 2), "src/main/java/org/firstgame/assets/firstAidKit.png");
        firstAidKit.setPosition(player.getPosition()); // Place at the same position
        
        player.checkForCollisions();
        
        // Verify that player's life increased by 1
        assertEquals(4, player.getLives());
    }

}
