package org.firstgame;

import org.firstgame.entities.GameObject;
import org.firstgame.entities.Player;
import org.firstgame.properties.Rotation;
import org.firstgame.ui.GameWindow;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.firstgame.properties.Constants.*;

public class RokueLikeGame {
    private static RokueLikeGame instance;
    private List<GameObject> gameObjects = new ArrayList<>();
    private GameWindow gameWindow;
    private Set<Integer> activeKeys = new HashSet<>();
    private Player player;
    private long adventureTime;
    private boolean isGameOver;

    private RokueLikeGame() {
        createPlayer();
        createGameObjects();
        initGame();
    }

    public static RokueLikeGame getInstance() {
        if (instance == null) {
            instance = new RokueLikeGame();
        }
        return instance;
    }

    public void createPlayer(){
        player = new Player();
        player.setSprite("src/main/java/org/firstgame/assets/player2xright.png");
        gameObjects.add(player);
    }

    public void createGameObjects(){

    }

    public void initGame(){
        isGameOver = false;
        initGameWindow();
        initGameTimer();
        initGameObjects();
    }

    public void initGameWindow(){
        if(gameWindow == null){
            gameWindow = GameWindow.getInstance();
        }
        gameWindow.setGameInstance(this);
    }

    public void initGameTimer(){
        adventureTime = 90;
    }

    public void initGameObjects(){

    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void keyPressTriggered(Integer keyCode){
        activeKeys.add(keyCode);
    }

    public void keyReleaseTriggered(Integer keyCode){
        activeKeys.remove(keyCode);
    }

    public long getAdventureTime() {
        return adventureTime;
    }

    public void gameOver() {
        isGameOver = true;
        activeKeys.clear();
        for (KeyListener keyListener : gameWindow.getKeyListeners()) {
            gameWindow.removeKeyListener(keyListener);
        }
    }

    public void movePlayer() {
        if(activeKeys.isEmpty()){
            return;
        } else if(activeKeys.size() == 1) {
            if (activeKeys.contains(KEY_LEFT_ARROW_CODE)) {
                player.move(Rotation.LEFT);
                player.setSprite("src/main/java/org/firstgame/assets/player2xleft.png");
            } else if (activeKeys.contains(KEY_RIGHT_ARROW_CODE)) {
                player.move(Rotation.RIGHT);
                player.setSprite("src/main/java/org/firstgame/assets/player2xright.png");
            } else if (activeKeys.contains(KEY_UP_ARROW_CODE)) {
                player.move(Rotation.UP);
            } else if (activeKeys.contains(KEY_DOWN_ARROW_CODE)) {
                player.move(Rotation.DOWN);
            }
        } else if (activeKeys.size() == 2) {
            if (activeKeys.contains(KEY_LEFT_ARROW_CODE) && activeKeys.contains(KEY_UP_ARROW_CODE)) {
                player.move(Rotation.UP_LEFT);
                player.setSprite("src/main/java/org/firstgame/assets/player2xleft.png");
            } else if (activeKeys.contains(KEY_RIGHT_ARROW_CODE) && activeKeys.contains(KEY_UP_ARROW_CODE)) {
                player.move(Rotation.UP_RIGHT);
                player.setSprite("src/main/java/org/firstgame/assets/player2xright.png");
            } else if (activeKeys.contains(KEY_RIGHT_ARROW_CODE) && activeKeys.contains(KEY_DOWN_ARROW_CODE)) {
                player.move(Rotation.DOWN_RIGHT);
                player.setSprite("src/main/java/org/firstgame/assets/player2xright.png");
            } else if (activeKeys.contains(KEY_LEFT_ARROW_CODE) && activeKeys.contains(KEY_DOWN_ARROW_CODE)) {
                player.move(Rotation.DOWN_LEFT);
                player.setSprite("src/main/java/org/firstgame/assets/player2xleft.png");
            }
        }
    }
}
