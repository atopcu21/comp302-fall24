package org.firstgame.entities;

import org.firstgame.properties.Rotation;
import org.firstgame.properties.WorldPosition;

public abstract class GameObject {
    private WorldPosition worldPosition;
    private Rotation rotation;
    private double scale;
    private String sprite;

    public GameObject() {
        this.worldPosition = new WorldPosition(0,0);
    }

    public GameObject(WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    public WorldPosition getPosition() {
        return worldPosition;
    }

    public void setPosition(WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    public void setPosition(double x, double y) {
        this.worldPosition.setX(x);
        this.worldPosition.setY(y);
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
