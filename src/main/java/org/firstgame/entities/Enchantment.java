package org.firstgame.entities;

import java.util.Random;

public class Enchantment extends GameObject {
    private String sprite;

    public Enchantment(String sprite) {

        this.sprite = sprite;
                Random rand = new Random();
        int x = rand.nextInt(12);
        int y = rand.nextInt(12);
        setPosition(x, y);
    }

    public String getSprite() {
        return sprite;
    }

}