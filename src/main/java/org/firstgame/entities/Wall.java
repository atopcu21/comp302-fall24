package org.firstgame.entities;

import org.firstgame.properties.Constants;
import org.firstgame.properties.Rotation;

import java.io.Serializable;

public class Wall extends GameObject implements Serializable {
    public Wall(Rotation direction, double x, double y) {
        setRotation(direction);
        if(direction == Rotation.RIGHT){
            setSprite(Constants.WALL_RIGHT_SPRITE);
        } else if(direction == Rotation.LEFT){
            setSprite(Constants.WALL_LEFT_SPRITE);
        } else if(direction == Rotation.UP){
            setSprite(Constants.WALL_UP_SPRITE);
        } else if(direction == Rotation.DOWN){
            setSprite(Constants.WALL_DOWN_SPRITE);
        }
        setScale(3 ,3);
        setPosition(x, y);
    }

    @Override
    public void onCollusion(GameObject otherObject) {
        if (getRotation() == Rotation.RIGHT) {
            otherObject.move(Rotation.LEFT);
        } else if (getRotation() == Rotation.LEFT) {
            otherObject.move(Rotation.RIGHT);
        } else if (getRotation() == Rotation.UP) {
            otherObject.move(Rotation.DOWN);
        } else if (getRotation() == Rotation.DOWN) {
            otherObject.move(Rotation.UP);
        }
    }
}
