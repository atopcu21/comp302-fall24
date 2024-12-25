package org.firstgame.entities;

import java.util.Random;

import org.firstgame.properties.Constants;

public class FighterMonster extends Monster {

    // Constructor for FighterMonster
    public FighterMonster() {
        super(); // Call to the superclass (Monster) constructor

        // Set the sprite image for the FighterMonster
        setSprite(Constants.FIGHTER_SPRITE);

        // Generate a random position within a range (-150 to 150 for both x and y)
        Random rand = new Random();
        int x = rand.nextInt(300) - 150;
        int y = rand.nextInt(300) - 150;

        // Set the position of the FighterMonster
        setPosition(x, y);
    }

    // Implementation of the abstract performAction method from Monster
    @Override
    public void performAction() {
        // Define specific behavior for FighterMonster
        System.out.println("FighterMonster is patrolling the area!");
        // Add logic for patrolling or attacking behavior here
    }
}
