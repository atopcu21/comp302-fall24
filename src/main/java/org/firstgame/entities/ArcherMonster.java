package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;
import org.firstgame.properties.Rotation;
import org.firstgame.properties.WorldPosition;

import java.util.Random;

public class ArcherMonster extends Monster {
    private boolean isFiring = false;
    private long timeCreated;

    public ArcherMonster() {
        super();
        timeCreated = System.currentTimeMillis();
        setSprite(Constants.ARCHER_SPRITE);
        Random rand = new Random();
        int x = rand.nextInt(10) + 1;
        int y = rand.nextInt(10) + 1;
        setPosition(x, y);
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    @Override
    public void onCollusion(GameObject otherObject) {
        if(otherObject instanceof Arrow) return;
        super.onCollusion(otherObject);
    }

    public void fireArrow(GameObject targetObject) {
        if (isFiring) return;
        double x = targetObject.getPosition().getX() - this.getPosition().getX();
        double y = this.getPosition().getY() - targetObject.getPosition().getY();
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
        Rotation targetDirection = new Rotation(theta);
        RokueLikeGame.getInstance().addGameObject(new Arrow(new WorldPosition(getPosition().getX(), getPosition().getY()), targetDirection, this));
        isFiring = true;
    }

    public void endFiring() {
        timeCreated = System.currentTimeMillis();
        this.isFiring = false;
    }
}
