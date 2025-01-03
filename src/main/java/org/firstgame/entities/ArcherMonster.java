package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;

import java.util.Random;

public class ArcherMonster extends Monster {
    public ArcherMonster() {
        super();
        setSprite(Constants.ARCHER_SPRITE);
        Random rand = new Random();
        int x = rand.nextInt(12);
        int y = rand.nextInt(12);
        setPosition(x, y);
    }

    @Override
    public void onCollusion(GameObject otherObject) {
        super.onCollusion(otherObject);
    }
}
