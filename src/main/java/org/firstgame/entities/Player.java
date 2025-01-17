package org.firstgame.entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;
import org.firstgame.properties.Rotation;

import javax.swing.*;

import static org.firstgame.properties.Constants.PLAYER_CLOAKED_SPRITE;
import static org.firstgame.properties.Constants.PLAYER_SPRITE;

public class Player extends GameObject {
    private int lives;
    private int score;
    private boolean hasExtraTime;
    private boolean hasReveal;
    private boolean hasCloak;
    private boolean isCloaked;
    private boolean hasLuringGem;

    private List<GameObject> inventory;

    public Player() {
        super();
        lives = 3;
        score = 0;
        setPosition(1,1);
        setSprite(Constants.PLAYER_SPRITE);
        hasReveal = false;
        hasCloak = false;
        hasLuringGem = false;
    }

    @Override
    public void onCollusion(GameObject otherObject) {
        super.onCollusion(otherObject);
    }

    public void move(Rotation rotation){
        double deltaX = Math.sin(Math.toRadians(rotation.getAngle())) / 26;
        double deltaY = Math.cos(Math.toRadians(rotation.getAngle())) / 26;
        setRotation(rotation);
        updatePosition(deltaX, -deltaY);
        checkForCollisions();
    }

    public void updatePosition(double x, double y) {
        setPosition(getPosition().getX() + x, getPosition().getY() + y);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    public void increaseLives() {
        if(lives < 4){lives++;};
    }

    public void addToInventory(GameObject item) {
        inventory.add(item);
    }
    public void decreaseLives() {
        if (lives > 0) {
            lives--;
        }
    }


    public boolean isHasExtraTime() {
        return hasExtraTime;
    }
    
    public void setHasExtraTime(boolean hasExtraTime) {
        this.hasExtraTime = hasExtraTime;
    }
    
    public boolean isReveal() {
        return hasReveal;
    }
    
    public void setHasReveal(boolean hasReveal) {
        this.hasReveal = hasReveal;
    }
    
    public boolean hasCloak() {
        return hasCloak;
    }
    
    public void setHasCloak(boolean hasCloak) {
        this.hasCloak = hasCloak;
    }
    
    public boolean isLuringGem() {
        return hasLuringGem;
    }
    
    public void setHasLuringGem(boolean hasLuringGem) {
        this.hasLuringGem = hasLuringGem;
    }

    public void throwLuringGem(Rotation rotation) {
        if (hasLuringGem) {
            hasLuringGem = false;
            Enchantment luringGem = new Enchantment("src/main/java/org/firstgame/assets/luringGem.png", this);
            luringGem.setRotation(rotation);
            RokueLikeGame.getInstance().addGameObject(luringGem);
        }
    }

    public boolean isCloaked() {
        return isCloaked;
    }

    public void setCloaked() {
        if(hasCloak) {
            hasCloak = false;
            setSprite(PLAYER_CLOAKED_SPRITE);
            isCloaked = true;
            scheduleRemoveCloak();
        }
    }

    private void scheduleRemoveCloak() {
        Timer timer = new Timer(6000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSprite(PLAYER_SPRITE);
                isCloaked = false;
            }
        });
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
    }
}
