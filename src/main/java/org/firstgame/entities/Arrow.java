package org.firstgame.entities;

import org.firstgame.properties.Constants;
import org.firstgame.properties.Rotation;
import org.firstgame.properties.WorldPosition;

public class Arrow extends GameObject {
    public WorldPosition initialPosition;
    public boolean isOutOfRange = false;
    public ArcherMonster owner;

    public Arrow(WorldPosition worldPosition, Rotation rotation, ArcherMonster owner) {
        setSprite(Constants.ARROW_SPRITE);
        setPosition(worldPosition);
        setScale(1, 1);
        setRotation(rotation);
        setFacingDirection(rotation);
        this.owner = owner;
        this.initialPosition = worldPosition;
    }

    public void move() {
        if (isOutOfRange) return;
        double deltaX = Math.sin(Math.toRadians(getRotation().getAngle())) / 11;
        double deltaY = Math.cos(Math.toRadians(getRotation().getAngle())) / 11;
        updatePosition(deltaX, -deltaY);
        if (checkDifferenceBetweenPositions(getPosition(), initialPosition) > 1){
            isOutOfRange = true;
            owner.endFiring();
        }
        checkForCollisions();
    }

    @Override
    public void onCollusion(GameObject otherObject) {
        // super.onCollusion(otherObject);
        if (otherObject == owner) return;
        isOutOfRange = true;
        owner.endFiring();
        if(otherObject instanceof Player){
            ((Player) otherObject).decreaseLives();
        }
    }

    private double checkDifferenceBetweenPositions(WorldPosition p1, WorldPosition p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    public void updatePosition(double x, double y) {
        setPosition(getPosition().getX() + x, getPosition().getY() + y);
    }
}
