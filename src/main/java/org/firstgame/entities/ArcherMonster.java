
package org.firstgame.entities;

import java.util.Random;

import org.firstgame.properties.Constants;

public class ArcherMonster extends Monster {

    // Constructor for ArcherMonster
    public ArcherMonster() {
        super(); // Call to the superclass (Monster) constructor

        // Set the sprite image for the ArcherMonster
        setSprite(Constants.ARCHER_SPRITE);

        // Generate a random position within a range (-150 to 150 for both x and y)
        Random rand = new Random();
        int x = rand.nextInt(300) - 150;
        int y = rand.nextInt(300) - 150;

        // Set the position of the ArcherMonster
        setPosition(x, y);
    }

    // Implementation of the abstract performAction method from Monster
    @Override
    public void performAction() {
        // Define specific behavior for ArcherMonster
        System.out.println("ArcherMonster is shooting arrows!");
        // Add logic for shooting arrows or attacking the player here
    }
}


