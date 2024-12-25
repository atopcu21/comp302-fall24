package org.firstgame.entities;

import org.firstgame.properties.Rotation;
import org.firstgame.properties.WorldPosition;

public abstract class GameObject {

    // Position of the game object in the game world
    private WorldPosition worldPosition;

    // Rotation of the game object
    private Rotation rotation;

    // The direction the game object is facing
    private Rotation facingDirection;

    // Scale factor for the game object
    private double scale;

    // Sprite image representing the game object
    private String sprite;

    // Default constructor initializing default values
    public GameObject() {
        this.worldPosition = new WorldPosition(0, 0); // Default position at (0, 0)
        this.rotation = new Rotation(0); // No rotation initially
        this.scale = 1; // Default scale of 1
        this.sprite = ""; // No sprite assigned initially
    }

    // Constructor to initialize the game object with a specific position
    public GameObject(WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    // Get the current position of the game object
    public WorldPosition getPosition() {
        return worldPosition;
    }

    // Set the position of the game object
    public void setPosition(WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    // Set the position of the game object using x and y coordinates
    public void setPosition(double x, double y) {
        this.worldPosition.setX(x);
        this.worldPosition.setY(y);
    }

    // Get the current scale of the game object
    public double getScale() {
        return scale;
    }

    // Set the scale of the game object
    public void setScale(double scale) {
        this.scale = scale;
    }

    // Get the current rotation of the game object
    public Rotation getRotation() {
        return rotation;
    }

    // Set the rotation of the game object
    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    // Get the sprite image of the game object
    public String getSprite() {
        return sprite;
    }

    // Set the sprite image of the game object
    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    // Get the direction the game object is facing
    public Rotation getFacingDirection() {
        return facingDirection;
    }

    // Set the direction the game object is facing
    public void setFacingDirection(Rotation facingDirection) {
        this.facingDirection = facingDirection;
    }
}
