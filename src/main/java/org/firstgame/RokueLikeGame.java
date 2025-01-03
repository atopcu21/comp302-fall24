package org.firstgame;

import org.firstgame.entities.*;
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
    private List<GameObject> gameObjectsEarth = new ArrayList<>();
    private List<GameObject> gameObjectsAir = new ArrayList<>();
    private List<GameObject> gameObjectsFire = new ArrayList<>();
    private List<GameObject> gameObjectsWater = new ArrayList<>();
    private GameWindow gameWindow;
    private Set<Integer> activeKeys = new HashSet<>();
    private Player player;
    private long adventureTime;
    private boolean isGameOver;
    private String currentLevel = "Earth";

    private RokueLikeGame() {
        createGameObjects();
        createPlayer();
    }

    public static RokueLikeGame getInstance() {
        if (instance == null) {
            instance = new RokueLikeGame();
        }
        return instance;
    }

    public void createPlayer(){
        player = new Player();
        switch (currentLevel) {
            case "Earth" -> gameObjectsEarth.add(player);
            case "Air" -> gameObjectsAir.add(player);
            case "Fire" -> gameObjectsFire.add(player);
            case "Water" -> gameObjectsWater.add(player);
        }
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
        adventureTime = gameObjectsEarth.size() * 5L;
    }

    public void initGameObjects(){
        initWalls();
    }

    public void initWalls(){
        switch (currentLevel) {
            case "Earth" -> {
                for(int i = 0; i < 12; i++){
                    gameObjectsEarth.add(new Wall(Rotation.UP, i, 0));
                    if(i == 7){
                        gameObjectsEarth.add(new Door(i, 11));
                    } else {
                        gameObjectsEarth.add(new Wall(Rotation.DOWN, i, 11));
                    }
                    gameObjectsEarth.add(new Wall(Rotation.LEFT, -0.5, i));
                    gameObjectsEarth.add(new Wall(Rotation.RIGHT, 11.4, i));
                }
            }
            case "Air" -> {
                for(int i = 0; i < 12; i++){
                    gameObjectsAir.add(new Wall(Rotation.UP, i, 0));
                    if(i == 7){
                        gameObjectsAir.add(new Door(i, 11));
                    } else {
                        gameObjectsAir.add(new Wall(Rotation.DOWN, i, 11));
                    }
                    gameObjectsAir.add(new Wall(Rotation.LEFT, -0.5, i));
                    gameObjectsAir.add(new Wall(Rotation.RIGHT, 11.4, i));
                }
            }
            case "Fire" -> {
                for(int i = 0; i < 12; i++){
                    gameObjectsFire.add(new Wall(Rotation.UP, i, 0));
                    if(i == 7){
                        gameObjectsFire.add(new Door(i, 11));
                    } else {
                        gameObjectsFire.add(new Wall(Rotation.DOWN, i, 11));
                    }
                    gameObjectsFire.add(new Wall(Rotation.LEFT, -0.5, i));
                    gameObjectsFire.add(new Wall(Rotation.RIGHT, 11.4, i));
                }
            }
            case "Water" -> {
                for(int i = 0; i < 12; i++){
                    gameObjectsWater.add(new Wall(Rotation.UP, i, 0));
                    if(i == 7){
                        gameObjectsWater.add(new Door(i, 11));
                    } else {
                        gameObjectsWater.add(new Wall(Rotation.DOWN, i, 11));
                    }
                    gameObjectsWater.add(new Wall(Rotation.LEFT, -0.5, i));
                    gameObjectsWater.add(new Wall(Rotation.RIGHT, 11.4, i));
                }
            }
        }
    }

    public List<GameObject> getGameObjects() {
        return switch (currentLevel) {
            case "Earth" -> gameObjectsEarth;
            case "Air" -> gameObjectsAir;
            case "Fire" -> gameObjectsFire;
            case "Water" -> gameObjectsWater;
            default -> null;
        };
    }

    public void setGameObjects(List<GameObject> gameObjects) {

    }

    public void addGameObjects(List<GameObject> gameObject, String level) {
        switch (level) {
            case "Earth" -> this.gameObjectsEarth.addAll(gameObject);
            case "Air" -> this.gameObjectsAir.addAll(gameObject);
            case "Fire" -> this.gameObjectsFire.addAll(gameObject);
            case "Water" -> this.gameObjectsWater.addAll(gameObject);
        }
    }

    public void addGameObject(GameObject gameObject) {
        switch (currentLevel) {
            case "Earth" -> this.gameObjectsEarth.add(gameObject);
            case "Air" -> this.gameObjectsAir.add(gameObject);
            case "Fire" -> this.gameObjectsFire.add(gameObject);
            case "Water" -> this.gameObjectsWater.add(gameObject);
        }
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

    public void addAdventureTime(long adventureTime) {
        this.adventureTime += adventureTime;
    }

    public void addAdventureTime() {
        switch (currentLevel) {
            case "Earth" -> addAdventureTime(gameObjectsEarth.size() * 5L);
            case "Air" -> addAdventureTime(gameObjectsAir.size() * 5L);
            case "Fire" -> addAdventureTime(gameObjectsFire.size() * 5L);
            case "Water" -> addAdventureTime(gameObjectsWater.size() * 5L);
        }
    }

    public void gameOver() {
        isGameOver = true;
        activeKeys.clear();
        for (KeyListener keyListener : gameWindow.getKeyListeners()) {
            gameWindow.removeKeyListener(keyListener);
        }
        gameWindow.stopTimer();
    }

    public void movePlayer() {
        if(activeKeys.isEmpty()){
            return;
        } else if(activeKeys.size() == 1) {
            if (activeKeys.contains(KEY_LEFT_ARROW_CODE)) {
                player.move(Rotation.LEFT);
                player.setFacingDirection(Rotation.LEFT);
            } else if (activeKeys.contains(KEY_RIGHT_ARROW_CODE)) {
                player.move(Rotation.RIGHT);
                player.setFacingDirection(Rotation.RIGHT);
            } else if (activeKeys.contains(KEY_UP_ARROW_CODE)) {
                player.move(Rotation.UP);
            } else if (activeKeys.contains(KEY_DOWN_ARROW_CODE)) {
                player.move(Rotation.DOWN);
            }
        } else if (activeKeys.size() == 2) {
            if (activeKeys.contains(KEY_LEFT_ARROW_CODE) && activeKeys.contains(KEY_UP_ARROW_CODE)) {
                player.move(Rotation.UP_LEFT);
                player.setFacingDirection(Rotation.LEFT);
            } else if (activeKeys.contains(KEY_RIGHT_ARROW_CODE) && activeKeys.contains(KEY_UP_ARROW_CODE)) {
                player.move(Rotation.UP_RIGHT);
                player.setFacingDirection(Rotation.RIGHT);
            } else if (activeKeys.contains(KEY_RIGHT_ARROW_CODE) && activeKeys.contains(KEY_DOWN_ARROW_CODE)) {
                player.move(Rotation.DOWN_RIGHT);
                player.setFacingDirection(Rotation.RIGHT);
            } else if (activeKeys.contains(KEY_LEFT_ARROW_CODE) && activeKeys.contains(KEY_DOWN_ARROW_CODE)) {
                player.move(Rotation.DOWN_LEFT);
                player.setFacingDirection(Rotation.LEFT);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void winGame() {
        switch (currentLevel) {
            case "Earth" -> currentLevel = "Air";
            case "Air" -> currentLevel = "Water";
            case "Water" -> currentLevel = "Fire";
            default -> {
                return;
            }
        }
        gameWindow.winGame();
        initWalls();
        createGameObjects();
        createPlayer();
    }

    public String getCurrentLevel() {
        return currentLevel;
    }
}
