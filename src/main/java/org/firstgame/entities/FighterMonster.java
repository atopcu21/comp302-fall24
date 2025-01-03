package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;
import org.firstgame.properties.Rotation;

import java.util.Random;

public class FighterMonster extends Monster {
    public FighterMonster() {
        super();
        setSprite(Constants.FIGHTER_SPRITE);
        Random rand = new Random();
        int x = rand.nextInt(12);
        int y = rand.nextInt(12);
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
}
