package org.firstgame.entities;

import java.util.Random;

import org.firstgame.properties.Constants;

public class WizardMonster extends Monster {

    // Constructor for WizardMonster
    public WizardMonster() {
        super(); // Call to the superclass (Monster) constructor

        // Set the sprite image for the WizardMonster
        setSprite(Constants.WIZARD_SPRITE);

        // Generate a random position within a range (-150 to 150 for both x and y)
        Random rand = new Random();
        int x = rand.nextInt(300) - 150;
        int y = rand.nextInt(300) - 150;

        // Set the position of the WizardMonster
        setPosition(x, y);
    }

    // Implementation of abstract method from Monster
    @Override
    public void performAction() {
        // Unique behavior for the WizardMonster
        System.out.println("WizardMonster is teleporting the rune!");
        // Add teleportation logic here
    }
}



