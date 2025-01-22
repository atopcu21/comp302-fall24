package org.firstgame;

import org.firstgame.entities.*;
import org.firstgame.properties.Rotation;
import org.firstgame.properties.WorldPosition;
import org.firstgame.ui.GameOverScreen;
import org.firstgame.ui.GameWindow;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import static org.firstgame.properties.Constants.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class RokueLikeGame implements Serializable {
    private static RokueLikeGame instance;
    private List<GameObject> gameObjectsEarth = new CopyOnWriteArrayList<>();
    private List<GameObject> gameObjectsAir = new CopyOnWriteArrayList<>();
    private List<GameObject> gameObjectsFire = new CopyOnWriteArrayList<>();
    private List<GameObject> gameObjectsWater = new CopyOnWriteArrayList<>();

    private Set<Integer> activeKeys = new HashSet<>();
    private Set<Integer> activeKeysSecondPlayer = new HashSet<>();
    private Player player;
    private Player player2;
    private static boolean isMultiplayer;
    private long adventureTime;
    private long totalTime;
    private boolean isGameOver;
    private String currentLevel = "Earth";
    private boolean isLureEnchantActivated = false;
    private WorldPosition gemBrokeAtPosition;

    public static void resetInstance() {
        instance = null;
    }

    public static void setMultiplayer(boolean isMultiplayer) {
        RokueLikeGame.isMultiplayer = isMultiplayer;
    }

    public static boolean isMultiplayer() {
        return isMultiplayer;
    }

    public boolean repOk() {
        // 1. Singleton integrity (not specifically testable in usual sense,
        //    but we assume 'instance' is either null or references this).
        if (this != instance) {
            return false;
        }
        // 2. Check valid currentLevel
        if (!(currentLevel.equals("Earth")
                || currentLevel.equals("Air")
                || currentLevel.equals("Fire")
                || currentLevel.equals("Water")
                || currentLevel.equals("Finished"))) {
            // We might allow some other states, but check your design
            return false;
        }
        // 3. Ensure all gameObjects lists contain non-null GameObject instances
        if (!allValidGameObjects(gameObjectsEarth)) return false;
        if (!allValidGameObjects(gameObjectsAir)) return false;
        if (!allValidGameObjects(gameObjectsFire)) return false;
        if (!allValidGameObjects(gameObjectsWater)) return false;

        // 4. Check that player is non-null if the game has been initialized
        //    (We could refine this condition based on your design.)
        if (player == null && !isGameOver) {
            return false;
        }

        return true;
    }

    private boolean allValidGameObjects(List<GameObject> gameObjects) {
        for (GameObject go : gameObjects) {
            if (go == null) {
                return false;
            }
        }
        return true;
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

    public void createSecondPlayer() {
        player2 = new Player();
        player2.setPosition(2,1);
        switch (currentLevel) {
            case "Earth" -> gameObjectsEarth.add(player2);
            case "Air"   -> gameObjectsAir.add(player2);
            case "Fire"  -> gameObjectsFire.add(player2);
            case "Water" -> gameObjectsWater.add(player2);
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void initGame(){
        isGameOver = false;
        createPlayer();
        if (isMultiplayer) {
            createSecondPlayer();
        }
        initGameWindow();
        initGameTimer();
        initGameObjects();
        //GameWindow.getInstance().setBackgroundColor(new Color(34, 139, 34)); // Green for Earth
    }

    public void initGameFromSave(){
        activeKeys.clear();
        if ("Earth".equals(currentLevel)) {
            GameWindow.getInstance().setBackgroundOfGW("src/main/java/org/firstgame/assets/taslak01.png", "src/main/java/org/firstgame/assets/taslak02.png");
        }
        else if ("Air".equals(currentLevel)) {
            GameWindow.getInstance().setBackgroundOfGW("src/main/java/org/firstgame/assets/taslak03.png", "src/main/java/org/firstgame/assets/taslak04.png");
         } // Light blue for Air
        else if ("Water".equals(currentLevel)) {
            GameWindow.getInstance().setBackgroundOfGW("src/main/java/org/firstgame/assets/wata.png","src/main/java/org/firstgame/assets/wata2.png");
        }
        else if ("Fire".equals(currentLevel)) {
            GameWindow.getInstance().setBackgroundOfGW("src/main/java/org/firstgame/assets/fire.png","src/main/java/org/firstgame/assets/fire2.png"); // Orange-Red for Fire
        }
    }

    public void initGameWindow(){
        GameWindow.getInstance().setGameInstance(RokueLikeGame.getInstance());
    }

    public void initGameTimer(){
        adventureTime = (gameObjectsEarth.size() - 1) * 5000L;
        totalTime = adventureTime;
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

    public void setGameObjects(List<GameObject> gameObjects, String level) {
        switch (level) {
            case "Earth" -> {
                this.gameObjectsEarth.clear();
                this.gameObjectsEarth.addAll(gameObjects);
            }
            case "Air" -> {
                this.gameObjectsAir.clear();
                this.gameObjectsAir.addAll(gameObjects);
            }
            case "Fire" -> {
                this.gameObjectsFire.clear();
                this.gameObjectsFire.addAll(gameObjects);
            }
            case "Water" -> {
                this.gameObjectsWater.clear();
                this.gameObjectsWater.addAll(gameObjects);
            }
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
        return GameWindow.getInstance();
    }
    
    public void keyPressTriggered(Integer keyCode){
        if(isMultiplayer) {
            if (keyCode.equals(KEY_A_CODE) || keyCode.equals(KEY_D_CODE) || keyCode.equals(KEY_S_CODE) || keyCode.equals(KEY_W_CODE)) {
                activeKeysSecondPlayer.add(keyCode);
            } else activeKeys.add(keyCode);
        } else {
            activeKeys.add(keyCode);
        }
    }

    public void keyReleaseTriggered(Integer keyCode){
        if(isMultiplayer) {
            if (keyCode.equals(KEY_A_CODE) || keyCode.equals(KEY_D_CODE) || keyCode.equals(KEY_S_CODE) || keyCode.equals(KEY_W_CODE)) {
                activeKeysSecondPlayer.remove(keyCode);
            } else activeKeys.remove(keyCode);
        } else {
            activeKeys.remove(keyCode);
        }
    }

    public long getAdventureTime() {
        return adventureTime;
    }

    public void setAdventureTime(long adventureTime) {
        this.adventureTime = adventureTime;
    }

    public void addAdventureTime(long adventureTime) {
        this.adventureTime += adventureTime;
        totalTime += adventureTime;
    }

    public void addAdventureTime() {
        switch (currentLevel) {
            case "Earth" -> addAdventureTime(gameObjectsEarth.size() * 5000L);
            case "Air" -> addAdventureTime(gameObjectsAir.size() * 5000L);
            case "Fire" -> addAdventureTime(gameObjectsFire.size() * 5000L);
            case "Water" -> addAdventureTime(gameObjectsWater.size() * 5000L);
        }
    }

    public void gameOver() {
        isGameOver = true;
        activeKeys.clear();
        for (KeyListener keyListener : GameWindow.getInstance().getKeyListeners()) {
            GameWindow.getInstance().removeKeyListener(keyListener);
        }
        GameWindow.getInstance().stopTimer();
        showGameOverScreen();
    }

    private void showGameOverScreen() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(GameWindow.getInstance());
        parentFrame.dispose();
        SwingUtilities.invokeLater(GameOverScreen::new);
    }

    public void resetGame() {
        // Reset key variables
        isGameOver = false;
        player = new Player();  // Reset player state (lives, score, inventory)
        currentLevel = "Earth";  // Start from the first hall
        adventureTime = 300;  // Reset timer (or set according to your default)
        
        // Clear game objects and restart
        gameObjectsEarth.clear();
        gameObjectsAir.clear();
        gameObjectsFire.clear();
        gameObjectsWater.clear();
    }

    private long lastRKeyPressTime = 0;

    public void movePlayers() {
        moveSinglePlayerControls(player);
        if (isMultiplayer && player2 != null) {
            moveSecondPlayerControls(player2);
        }
    }

    public void moveSinglePlayerControls(Player player) {
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
            } else if (activeKeys.contains(KEY_R_CODE)) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastRKeyPressTime >= 1000) { // 1 second interval
                    // Message box that says "r is pressed"
                    if(RokueLikeGame.getInstance().getPlayer().isReveal()){
                        RokueLikeGame.getInstance().getPlayer().setHasReveal(false);
                        GameWindow.getInstance().changeRuneHighlight();
                        lastRKeyPressTime = currentTime;
                    }
                }
            } else if (activeKeys.contains(KEY_C_CODE)) {
                if (player.hasCloak()) {
                    player.setCloaked();
                }
            } else if (!isMultiplayer) {
                if (activeKeys.contains(KEY_L_CODE)) {
                    if(RokueLikeGame.getInstance().getPlayer().isLuringGem()){
                        isLureEnchantActivated = true;
                    }
                } else if (isLureEnchantActivated) {
                    if(activeKeys.contains(KEY_W_CODE)) {
                        player.throwLuringGem(Rotation.UP);
                    } else if (activeKeys.contains(KEY_S_CODE)) {
                        player.throwLuringGem(Rotation.DOWN);
                    } else if (activeKeys.contains(KEY_A_CODE)) {
                        player.throwLuringGem(Rotation.LEFT);
                    } else if (activeKeys.contains(KEY_D_CODE)) {
                        player.throwLuringGem(Rotation.RIGHT);
                    }
                    isLureEnchantActivated = false;
                }
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

    private void moveSecondPlayerControls(Player p) {
        if(activeKeysSecondPlayer.isEmpty()){
            return;
        } else if(activeKeysSecondPlayer.size() == 1) {
            if (activeKeysSecondPlayer.contains(KEY_A_CODE)) {
                p.move(Rotation.LEFT);
                p.setFacingDirection(Rotation.LEFT);
            }
            else if (activeKeysSecondPlayer.contains(KEY_D_CODE)) {
                p.move(Rotation.RIGHT);
                p.setFacingDirection(Rotation.RIGHT);
            }
            else if (activeKeysSecondPlayer.contains(KEY_W_CODE)) {
                p.move(Rotation.UP);
            }
            else if (activeKeysSecondPlayer.contains(KEY_S_CODE)) {
                p.move(Rotation.DOWN);
            }
        } else if (activeKeysSecondPlayer.size() == 2) {
            if (activeKeysSecondPlayer.contains(KEY_A_CODE) && activeKeysSecondPlayer.contains(KEY_W_CODE)) {
                p.move(Rotation.UP_LEFT);
                p.setFacingDirection(Rotation.LEFT);
            } else if (activeKeysSecondPlayer.contains(KEY_D_CODE) && activeKeysSecondPlayer.contains(KEY_W_CODE)) {
                p.move(Rotation.UP_RIGHT);
                p.setFacingDirection(Rotation.RIGHT);
            } else if (activeKeysSecondPlayer.contains(KEY_D_CODE) && activeKeysSecondPlayer.contains(KEY_S_CODE)) {
                p.move(Rotation.DOWN_RIGHT);
                p.setFacingDirection(Rotation.RIGHT);
            } else if (activeKeysSecondPlayer.contains(KEY_A_CODE) && activeKeysSecondPlayer.contains(KEY_S_CODE)) {
                p.move(Rotation.DOWN_LEFT);
                p.setFacingDirection(Rotation.LEFT);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void winGame() {
        if (isMultiplayer) {
            if (!bothPlayersAtDoor()) {
                return;
            }
        }

        switch (currentLevel) {
            case "Earth" -> {
                currentLevel = "Air";
                GameWindow.getInstance().setBackgroundOfGW("src/main/java/org/firstgame/assets/taslak03.png", "src/main/java/org/firstgame/assets/taslak04.png");
                break;
            }
            case "Air" -> {
                currentLevel = "Water";
                GameWindow.getInstance().setBackgroundOfGW("src/main/java/org/firstgame/assets/wata.png","src/main/java/org/firstgame/assets/wata2.png"); 
                break;
            }
            case "Water" -> {
                currentLevel = "Fire";
                GameWindow.getInstance().setBackgroundOfGW("src/main/java/org/firstgame/assets/fire.png","src/main/java/org/firstgame/assets/fire2.png"); 
                break;
            }
            case "Fire" -> {
                currentLevel = "Finished";
                JOptionPane.showMessageDialog(GameWindow.getInstance(), "Congratulations! You have won the game!", "Game Won", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
                break;
            }
            default -> {
                return;
            }
        }
        GameWindow.getInstance().winGame();
        initWalls();
        createPlayer();
        if (isMultiplayer) {
            createSecondPlayer();
        }
    }

    private boolean bothPlayersAtDoor(){
        return player.isPassedThroughTheDoor() && player2.isPassedThroughTheDoor();
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void removeGameObject(GameObject gameObject) {
        switch (getCurrentLevel()) {
            case "Earth" -> gameObjectsEarth.remove(gameObject);
            case "Air" -> gameObjectsAir.remove(gameObject);
            case "Fire" -> gameObjectsFire.remove(gameObject);
            case "Water" -> gameObjectsWater.remove(gameObject);
        }
    }

    public void removeAllEnchantments() {
        switch (getCurrentLevel()) {
            case "Earth" -> gameObjectsEarth.removeIf(gameObject -> gameObject instanceof Enchantment && ((Enchantment) gameObject).getOwner() == null);
            case "Air" -> gameObjectsAir.removeIf(gameObject -> gameObject instanceof Enchantment && ((Enchantment) gameObject).getOwner() == null);
            case "Fire" -> gameObjectsFire.removeIf(gameObject -> gameObject instanceof Enchantment && ((Enchantment) gameObject).getOwner() == null);
            case "Water" -> gameObjectsWater.removeIf(gameObject -> gameObject instanceof Enchantment && ((Enchantment) gameObject).getOwner() == null);
        }
    }

    public WorldPosition getGemBrokeAtPosition() {
        return gemBrokeAtPosition;
    }

    public void setGemBrokeAtPosition(WorldPosition gemBrokeAtPosition) {
        this.gemBrokeAtPosition = gemBrokeAtPosition;
        lureAllFighters();
    }

    public List<GameObject> getCurrentGameObjects() {
        switch (currentLevel) {
            case "Earth" -> {
                return gameObjectsEarth;
            }
            case "Air" -> {
                return gameObjectsAir;
            }
            case "Fire" -> {
                return gameObjectsFire;
            }
            case "Water" -> {
                return gameObjectsWater;
            }
        }
        return gameObjectsEarth;
    }

    public void lureAllFighters() {
        getCurrentGameObjects().stream().filter(it -> it instanceof FighterMonster).toList().forEach(gameObject -> {
            double x = gemBrokeAtPosition.getX() - gameObject.getPosition().getX();
            double y = gameObject.getPosition().getY() - gemBrokeAtPosition.getY();
            if(x == 0) {
                x += 0.0000000001;
            } else if(y == 0) {
                y += 0.0000000001;
            }
            double len = Math.sqrt((x * x) + (y * y));
            double norX = x / len;
            double norY = y / len;
            double theta;
            theta = Math.toDegrees(Math.atan2(norX, norY));
            Rotation targetDirection = new Rotation(theta);
            gameObject.setRotation(targetDirection);
            ((FighterMonster) gameObject).setLured(true);
            scheduleRemoveLure();
        });
    }

    public void removeLureFromAllFighters() {
        getCurrentGameObjects().stream().filter(it -> it instanceof FighterMonster).toList().forEach(gameObject -> {
            ((FighterMonster) gameObject).setLured(false);
        });
    }

    private void scheduleRemoveLure() {
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RokueLikeGame.getInstance().removeLureFromAllFighters();
            }
        });
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
    }

    public void saveGameState() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savedGameState.ku"))) {
            out.writeObject(this);
            System.out.println("Game state saved successfully to " + "savedGameState.ku");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save game state.");
        }
    }

    public void loadGameState() {
        File file = new File("savedGameState.ku");
        if (!file.exists()) {
            System.err.println("Save file does not exist. Cannot load game state.");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            RokueLikeGame loadedGame = (RokueLikeGame) in.readObject();
            RokueLikeGame.instance = loadedGame;
            RokueLikeGame.setMultiplayer(loadedGame.player2 != null);
            GameWindow.getInstance().setGameInstance(loadedGame);
            System.out.println("Game state loaded successfully from " + "savedGameState.ku");
            initGameFromSave();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Failed to load game state.");
        }
    }

    public long getTotalTime() {
        return totalTime;
    }
}
