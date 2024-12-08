package org.firstgame.entities;

import org.firstgame.properties.Rotation;

public class Player extends GameObject {
    private int lives;
    private int score;

    public Player() {
        super();
        lives = 3;
        score = 0;
    }

    public void move(Rotation rotation){
        double deltaX = Math.sin(Math.toRadians(rotation.getAngle()));
        double deltaY = Math.cos(Math.toRadians(rotation.getAngle()));
        updatePosition(deltaX, -deltaY);
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
}
