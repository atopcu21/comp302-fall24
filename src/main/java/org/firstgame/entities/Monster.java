package org.firstgame.entities;

public abstract class Monster extends GameObject {
    
    // Health of the monster
    private int health;

    // Damage dealt by the monster
    private int attackPower;

    // Default constructor for Monster
    public Monster() {
        super(); // Call to the superclass (GameObject) constructor
        this.health = 100; // Default health
        this.attackPower = 10; // Default attack power
    }

    // Getter for health
    public int getHealth() {
        return health;
    }

    // Setter for health
    public void setHealth(int health) {
        this.health = health;
    }

    // Getter for attack power
    public int getAttackPower() {
        return attackPower;
    }

    // Setter for attack power
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    // Method for the monster to attack
    public void attack(GameObject target) {
        // Logic for attack (e.g., reduce health of the target or apply effect)
        System.out.println("Monster attacks with power: " + attackPower);
        // Placeholder for specific logic, e.g., target.takeDamage(attackPower);
    }

    // Abstract method to define unique behavior of each monster type
    public abstract void performAction();
}





