package org.firstgame.entities;

import java.util.List;

import org.firstgame.properties.Constants;
import org.firstgame.properties.Rotation;

public class Player extends GameObject {
    private int lives;
    private int score;
    private boolean hasExtraTime;
    private boolean hasReveal;
    private boolean hasCloak;
    private boolean hasLuringGem;



    private List<GameObject> inventory;

    public Player() {
        super();
        lives = 3;
        score = 0;
        setPosition(1,1);
        setSprite(Constants.PLAYER_SPRITE);
        hasExtraTime = false;
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
    
    public boolean isHasReveal() {
        return hasReveal;
    }
    
    public void setHasReveal(boolean hasReveal) {
        this.hasReveal = hasReveal;
    }
    
    public boolean isHasCloak() {
        return hasCloak;
    }
    
    public void setHasCloak(boolean hasCloak) {
        this.hasCloak = hasCloak;
    }
    
    public boolean isHasLuringGem() {
        return hasLuringGem;
    }
    
    public void setHasLuringGem(boolean hasLuringGem) {
        this.hasLuringGem = hasLuringGem;
    }



}
