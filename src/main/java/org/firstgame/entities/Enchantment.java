package org.firstgame.entities;

public class Enchantment extends GameObject {
    private String type;

    public Enchantment(String type) {

        this.type = type;
    }

    public String getType() {
        return type;
    }
}