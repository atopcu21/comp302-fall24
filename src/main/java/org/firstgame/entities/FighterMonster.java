package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;
import org.firstgame.properties.Rotation;

import java.io.Serializable;
import java.util.Random;

public class FighterMonster extends Monster implements Serializable {
    private boolean isLured = false;
    public FighterMonster() {
        super();
        setSprite(Constants.FIGHTER_SPRITE);
        Random rand = new Random();
        int x = rand.nextInt(10) + 1;
        int y = rand.nextInt(10) + 1;
        setPosition(x, y);
    }

    @Override
    public void onCollusion(GameObject otherObject) {
        super.onCollusion(otherObject);
    }

    public void move() {
        move(getRotation());
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

    public boolean isLured() {
        return isLured;
    }

    public void setLured(boolean isLured) {
        this.isLured = isLured;
    }
}
