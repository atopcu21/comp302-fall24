package org.firstgame.entities;

import org.firstgame.RokueLikeGame;
import org.firstgame.properties.Constants;

import java.util.Random;

public class WizardMonster extends Monster {
    public WizardMonster() {
        super();
        setSprite(Constants.WIZARD_SPRITE);
        Random rand = new Random();
        int x = rand.nextInt(10) + 1;
        int y = rand.nextInt(10) + 1;
        setPosition(x, y);
    }

    @Override
    public void onCollusion(GameObject otherObject) {
        super.onCollusion(otherObject);
    }
}




