package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Rotation;
import org.firstgame.properties.WorldPosition;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameObject {
    private WorldPosition worldPosition;
    private Rotation rotation;
    private Rotation facingDirection;
    private double scaleX;
    private double scaleY;
    private String sprite;
    private double width;
    private double height;
    private boolean hasRune;
    private boolean canHaveRuneInIt;
    private boolean isColliding;
    
    private Timer collisionTimer;
    private boolean canDecreaseLives = true;


    public GameObject() {
        this.worldPosition = new WorldPosition(0,0);
        this.rotation = new Rotation(0);
        this.scaleX = 1;
        this.scaleY = 1;
        this.sprite = "";
        this.width = 0;
        this.height = 0;
    }

    public GameObject(WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    public GameObject(WorldPosition worldPosition, String sprite) {
        this.worldPosition = worldPosition;
        setSprite(sprite);
        canHaveRuneInIt = true;
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

    public double getScaleX() {
        return scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScale(double scaleX, double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
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
        try {
            this.sprite = sprite;
            BufferedImage img = ImageIO.read(new File(sprite));
            this.width = img.getWidth() * 1.3;
            this.height = img.getHeight() * 1.3;
        } catch (IOException e) {
            // ignored
        }
    }

    public Rotation getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Rotation facingDirection) {
        this.facingDirection = facingDirection;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void move(Rotation rotation) {

    }

    public void onCollusion(GameObject otherObject) {
        if(otherObject.isColliding){
            return;
        }
        otherObject.setColliding(true);
        double x = this.getPosition().getX() - otherObject.getPosition().getX();
        double y = this.getPosition().getY() - otherObject.getPosition().getY();
        if(x == 0) {
            x += 0.0000000001;
        } else if(y == 0) {
            y += 0.0000000001;
        }
        double len = Math.sqrt((x * x) + (y * y));
        double norX = x / len;
        double norY = y / len;
        double theta;
        theta = Math.toDegrees(Math.atan2(norX, norY));
        if(Math.abs(norY) < Math.abs(norX)) {
            if(theta <= 180) {
                theta += 180;
            } else {
                theta = theta - 180;
            }
        }
        otherObject.move(new Rotation(theta));
        otherObject.setColliding(false);

        //System.out.println("Collision detected between: " + this.getSprite() + " and " + otherObject.getSprite());


        // if (otherObject.getSprite().equals("src/main/java/org/firstgame/assets/fighter.png") && this.getSprite().equals("src/main/java/org/firstgame/assets/player.png")) {
        //     RokueLikeGame.getInstance().getPlayer().decreaseLives();
        //     System.out.println("Player's health decreased by 1. Current health: " + RokueLikeGame.getInstance().getPlayer().getLives());
        // }


        if (otherObject.getSprite().equals("src/main/java/org/firstgame/assets/fighter.png") && this.getSprite().equals("src/main/java/org/firstgame/assets/player.png")) {
            handlePlayerCollision();
        } else if (this.getSprite().equals("src/main/java/org/firstgame/assets/fighter.png") && otherObject.getSprite().equals("src/main/java/org/firstgame/assets/player.png")) {
            handlePlayerCollision();
        }


        // enchantment collision parts are below
        if (otherObject.getSprite().equals("src/main/java/org/firstgame/assets/cloak.png") && this.getSprite().equals("src/main/java/org/firstgame/assets/player.png")) {
            RokueLikeGame.getInstance().removeAllEnchantments();
            RokueLikeGame.getInstance().getPlayer().setHasCloak(true);
            RokueLikeGame.getInstance().getGameWindow().updateEnchantmentIcons();
        } else if (this.getSprite().equals("src/main/java/org/firstgame/assets/cloak.png") && otherObject.getSprite().equals("src/main/java/org/firstgame/assets/player.png")) {
            RokueLikeGame.getInstance().removeAllEnchantments();
            RokueLikeGame.getInstance().getPlayer().setHasCloak(true);
            RokueLikeGame.getInstance().getGameWindow().updateEnchantmentIcons();
        }
        
        if (otherObject.getSprite().equals("src/main/java/org/firstgame/assets/luringGem.png") && this.getSprite().equals("src/main/java/org/firstgame/assets/player.png") && ((Enchantment) otherObject).getOwner() == null) {
            RokueLikeGame.getInstance().removeAllEnchantments();
            RokueLikeGame.getInstance().getPlayer().setHasLuringGem(true);
            RokueLikeGame.getInstance().getGameWindow().updateEnchantmentIcons();
        } else if (this.getSprite().equals("src/main/java/org/firstgame/assets/luringGem.png") && otherObject.getSprite().equals("src/main/java/org/firstgame/assets/player.png") && ((Enchantment) this).getOwner() == null) {
            RokueLikeGame.getInstance().removeAllEnchantments();
            RokueLikeGame.getInstance().getPlayer().setHasLuringGem(true);
            RokueLikeGame.getInstance().getGameWindow().updateEnchantmentIcons();
        } else if (this.getSprite().equals("src/main/java/org/firstgame/assets/luringGem.png") && ((Enchantment) this).getOwner() != null && !(otherObject instanceof Player)) {
            ((Enchantment) this).breakGem();
        } else if (otherObject.getSprite().equals("src/main/java/org/firstgame/assets/luringGem.png") && ((Enchantment) otherObject).getOwner() != null && !(this instanceof Player)) {
            ((Enchantment) otherObject).breakGem();
        }

        if (otherObject.getSprite().equals("src/main/java/org/firstgame/assets/reveal.png") && this.getSprite().equals("src/main/java/org/firstgame/assets/player.png")) {
            RokueLikeGame.getInstance().removeAllEnchantments();
            RokueLikeGame.getInstance().getPlayer().setHasReveal(true);
            RokueLikeGame.getInstance().getGameWindow().updateEnchantmentIcons();
        } else if (this.getSprite().equals("src/main/java/org/firstgame/assets/reveal.png") && otherObject.getSprite().equals("src/main/java/org/firstgame/assets/player.png")) {
            RokueLikeGame.getInstance().removeAllEnchantments();
            RokueLikeGame.getInstance().getPlayer().setHasReveal(true);
            RokueLikeGame.getInstance().getGameWindow().updateEnchantmentIcons();
        }

        if (otherObject.getSprite().equals("src/main/java/org/firstgame/assets/time.png") && this.getSprite().equals("src/main/java/org/firstgame/assets/player.png")) {
            RokueLikeGame.getInstance().removeAllEnchantments();
            RokueLikeGame.getInstance().addAdventureTime(5);
            RokueLikeGame.getInstance().getGameWindow().updateEnchantmentIcons();
        } else if (this.getSprite().equals("src/main/java/org/firstgame/assets/time.png") && otherObject.getSprite().equals("src/main/java/org/firstgame/assets/player.png")) {
            RokueLikeGame.getInstance().removeAllEnchantments();
            RokueLikeGame.getInstance().addAdventureTime(5);
            RokueLikeGame.getInstance().getGameWindow().updateEnchantmentIcons();
        }
        if (otherObject.getSprite().equals("src/main/java/org/firstgame/assets/firstAidKit.png") && this.getSprite().equals("src/main/java/org/firstgame/assets/player.png")) {
            RokueLikeGame.getInstance().removeAllEnchantments();
            RokueLikeGame.getInstance().getPlayer().increaseLives();


            RokueLikeGame.getInstance().getGameWindow().updateEnchantmentIcons();
        } else if (this.getSprite().equals("src/main/java/org/firstgame/assets/firstAidKit.png") && otherObject.getSprite().equals("src/main/java/org/firstgame/assets/player.png")) {
            RokueLikeGame.getInstance().removeAllEnchantments();
            RokueLikeGame.getInstance().getPlayer().increaseLives();


            RokueLikeGame.getInstance().getGameWindow().updateEnchantmentIcons();
        }
        
    }




    private void handlePlayerCollision() {
        if (canDecreaseLives) {
            Player player = RokueLikeGame.getInstance().getPlayer();
            player.decreaseLives();
            System.out.println("Player's health decreased by 1. Current health: " + player.getLives());
            canDecreaseLives = false;

            // Start a timer to reset canDecreaseLives after 1 second
            if (collisionTimer == null) {
                collisionTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        canDecreaseLives = true;
                    }
                });
                collisionTimer.setRepeats(false);
            }
            collisionTimer.restart();
        }
    }

    public boolean hasRune() {
        return hasRune;
    }

    public void setHasRune(boolean hasRune) {
        this.hasRune = hasRune;
    }

    public boolean canHaveRuneInIt() {
        return canHaveRuneInIt;
    }

    public void setCanHaveRuneInIt(boolean canHaveRuneInIt) {
        this.canHaveRuneInIt = canHaveRuneInIt;
    }

    public void checkForCollisions() {
        for (GameObject gameObject : RokueLikeGame.getInstance().getGameObjects()) {
            double minX = gameObject.getPosition().getX() - (gameObject.getWidth() / 88);
            double minY = gameObject.getPosition().getY() - (gameObject.getHeight() / 88);
            double maxX = gameObject.getPosition().getX() + (gameObject.getWidth() / 88);
            double maxY = gameObject.getPosition().getY() + (gameObject.getHeight() / 88);
            if(getPosition().getX() > minX && getPosition().getX() < maxX && getPosition().getY() > minY && getPosition().getY() < maxY) {
                if(!(gameObject.equals(this))){
                    gameObject.onCollusion(this);
                }
            }
        }
    }

    public void setColliding(boolean isColliding) {
        this.isColliding = isColliding;
    }
}
