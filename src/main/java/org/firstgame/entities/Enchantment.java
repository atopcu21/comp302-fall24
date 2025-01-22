package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Rotation;
import org.firstgame.properties.WorldPosition;

import java.io.Serializable;
import java.util.Random;

public class Enchantment extends GameObject implements Serializable {
    private String sprite;
    private Player owner;
    private WorldPosition initialPosition;

    public Enchantment(String sprite) {
        this.sprite = sprite;
        Random rand = new Random();
        int x = rand.nextInt(10) + 1;
        int y = rand.nextInt(10) + 1;
        setPosition(x, y);
    }

    public Enchantment(String sprite, Player player) {
        this.sprite = sprite;
        this.owner = player;
        initialPosition = new WorldPosition(player.getPosition().getX(), player.getPosition().getY());
        setPosition(player.getPosition().getX(), player.getPosition().getY());
    }

    public void move() {
        double deltaX = Math.sin(Math.toRadians(getRotation().getAngle())) / 9;
        double deltaY = Math.cos(Math.toRadians(getRotation().getAngle())) / 9;
        updatePosition(deltaX, -deltaY);
        checkForCollisions();

        if(Math.sqrt(Math.pow(initialPosition.getX() - getPosition().getX(), 2) + Math.pow(initialPosition.getY() - getPosition().getY(), 2)) > 3){
            breakGem();
        }
    }

    public void breakGem() {
        RokueLikeGame.getInstance().setGemBrokeAtPosition(this.getPosition());
        RokueLikeGame.getInstance().removeGameObject(this);
    }

    public void updatePosition(double x, double y) {
        setPosition(getPosition().getX() + x, getPosition().getY() + y);
    }

    public String getSprite() {
        return sprite;
    }

    public Player getOwner() {
        return owner;
    }
}