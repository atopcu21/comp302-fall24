package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.WorldPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArcherMonsterTest {
    private ArcherMonster archer;

    @BeforeEach
    public void setup() {
        RokueLikeGame.getInstance().resetGame();
        archer = new ArcherMonster();
        RokueLikeGame.getInstance().addGameObject(archer);
    }

    @Test
    public void testArrowCreatedInRange() {
        GameObject target = new GameObject(new WorldPosition(3, 3));
        RokueLikeGame.getInstance().addGameObject(target);
        archer.fireArrow(target);
        assertTrue(RokueLikeGame.getInstance().getGameObjects().stream()
                .anyMatch(obj -> obj instanceof Arrow));
    }

    @Test
    public void testNoArrowCreatedWhenAlreadyFiring() {
        GameObject target = new GameObject(new WorldPosition(3, 3));
        RokueLikeGame.getInstance().addGameObject(target);
        archer.fireArrow(target);
        archer.fireArrow(target);
        long arrowCount = RokueLikeGame.getInstance().getGameObjects().stream()
                .filter(obj -> obj instanceof Arrow).count();
        assertEquals(1, arrowCount);
    }

    @Test
    public void testNoArrowCreatedOutOfRange() {
        GameObject target = new GameObject(new WorldPosition(20, 20));
        RokueLikeGame.getInstance().addGameObject(target);
        archer.fireArrow(target);
        assertFalse(RokueLikeGame.getInstance().getGameObjects().stream()
                .anyMatch(obj -> obj instanceof Arrow));
    }
}


