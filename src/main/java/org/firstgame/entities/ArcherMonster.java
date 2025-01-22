package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;
import org.firstgame.properties.Rotation;
import org.firstgame.properties.WorldPosition;

import java.io.Serializable;
import java.util.Random;

public class ArcherMonster extends Monster implements Serializable {
    private boolean isFiring = false;
    private long timeCreated;
    private WorldPosition lastTargetPosition = new WorldPosition(0, 0);

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

        double targetX;
        double targetY;

        if (targetObject instanceof Player && ((Player) targetObject).isCloaked()){
            targetX = lastTargetPosition.getX();
            targetY = lastTargetPosition.getY();
        } else {
            targetX = targetObject.getPosition().getX();
            targetY = targetObject.getPosition().getY();
            lastTargetPosition = new WorldPosition(targetX, targetY);
        }

        double x = targetX - this.getPosition().getX();
        double y = this.getPosition().getY() - targetY;
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
