package org.firstgame.entities;

public class Item extends GameObject {

    // Type of the item (e.g., "HealthPotion", "Rune", "Key", etc.)
    private String itemType;

    // Description of the item
    private String description;

    // Constructor to initialize the item with a specific type and optional description
    public Item(String itemType, String description) {
        super(); // Call to the superclass (GameObject) constructor
        this.itemType = itemType;
        this.description = description;
    }

    // Overloaded constructor for cases where description is not provided
    public Item(String itemType) {
        this(itemType, ""); // Default description is an empty string
    }

    // Getter for the item type
    public String getItemType() {
        return itemType;
    }

    // Setter for the item type
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    // Getter for the description
    public String getDescription() {
        return description;
    }

    // Setter for the description
    public void setDescription(String description) {
        this.description = description;
    }

    // Method to define the effect of using the item (can be overridden by subclasses if needed)
    public void use() {
        System.out.println("Using item: " + itemType);
        // Add specific logic for item effects here
    }

    

    // Override toString method for better debug and logging support
    @Override
    public String toString() {
        return "Item{type='" + itemType + "', description='" + description + "'}";
    }
}
