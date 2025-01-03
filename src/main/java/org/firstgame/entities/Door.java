package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;
import org.firstgame.properties.Rotation;

public class Door extends GameObject {
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
            RokueLikeGame.getInstance().winGame();
        } else if (!isOpened) {
            otherObject.move(Rotation.UP);
        }
    }
}
