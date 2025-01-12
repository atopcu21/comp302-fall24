import org.firstgame.RokueLikeGame;
import org.firstgame.entities.GameObject;
import org.firstgame.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RokueLikeTest {

    private RokueLikeGame game;

    @BeforeEach
    public void setUp() {
        game = RokueLikeGame.getInstance();
        game.resetGame(); // Ensures a clean start
        game.initGame();
    }

    /**
     * Test #1: Check repOk() after initialization
     */
    @Test
    public void testRepOkAfterInit() {
        assertTrue(game.repOk(), "Representation invariant should hold after game initialization");
    }

    /**
     * Test #2: Ensure the correct initial level is Earth
     */
    @Test
    public void testInitialLevel() {
        assertEquals("Earth", game.getCurrentLevel(), "Initial level should be Earth after resetGame & initGame");
    }

    /**
     * Test #3: Adding a new GameObject to the current level
     */
    @Test
    public void testAddGameObject() {
        int sizeBefore = game.getGameObjects().size();
        GameObject dummy = new Player();  // or some other GameObject
        game.addGameObject(dummy);
        assertEquals(sizeBefore + 1, game.getGameObjects().size(), "Size of gameObjects should increase by 1 after adding a new object");
        // Representation invariant should still hold
        assertTrue(game.repOk(), "Representation invariant should hold after adding a new object");
    }

    /**
     * Test #4: Transition to the next level using winGame()
     */
    @Test
    public void testWinGameTransition() {
        // Earth -> Air
        game.winGame();
        assertEquals("Air", game.getCurrentLevel(), "After first winGame(), level should be Air");
        // Representation invariant should hold
        assertTrue(game.repOk(), "Representation invariant should hold after switching level");
    }
}