package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;
import org.firstgame.properties.Rotation;

import java.io.Serializable;

public class Door extends GameObject implements Serializable {
    private boolean isOpened;

    public Door(double x, double y) {
        setSprite(Constants.DOOR_CLOSED_SPRITE);
        setPosition(x, y);
    }

    public void setOpened(boolean isOpened) {
        this.isOpened = isOpened;
        setSprite(Constants.DOOR_OPENED_SPRITE);
    }

    public boolean isOpened() {
        return isOpened;
    }

    @Override
    public void onCollusion(GameObject otherObject) {
        super.onCollusion(otherObject);
        if(isOpened && (otherObject instanceof Player)) {
            ((Player) otherObject).setPassedThroughTheDoor(true);
            RokueLikeGame.getInstance().winGame();
        } else if (!isOpened) {
            otherObject.move(Rotation.UP);
        }
    }
}
