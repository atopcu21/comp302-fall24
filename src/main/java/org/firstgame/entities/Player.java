package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;
import org.firstgame.properties.Rotation;

import java.util.List;

public class Player extends GameObject {
    private int lives;
    private int score;
    private List<GameObject> inventory;

    public Player() {
        super();
        lives = 3;
        score = 0;
        setPosition(1,1);
        setSprite(Constants.PLAYER_SPRITE);
    }

    @Override
    public void onCollusion(GameObject otherObject) {
        super.onCollusion(otherObject);
    }

    public void move(Rotation rotation){
        double deltaX = Math.sin(Math.toRadians(rotation.getAngle())) / 44;
        double deltaY = Math.cos(Math.toRadians(rotation.getAngle())) / 44;
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
}
