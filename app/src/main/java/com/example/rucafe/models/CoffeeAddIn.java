package com.example.rucafe.models;

/**
 * Contains coffee add-in information.
 * @author Taze Balbosa, Yulie Ying
 */
public enum CoffeeAddIn {
    CREAM("Cream"), SYRUP("Syrup"), MILK("Milk"), CARAMEL("Caramel"), WHIPPED_CREAM("Whipped Cream");

    private final String name;

    /**
     * Creates a CoffeeAddIn object storing the add-in's name.
     * @param name Coffee add-in name.
     */
    CoffeeAddIn(String name) {
        this.name = name;
    }

    /**
     * Returns the coffee add-in name.
     * @return Coffee add-in name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string format of the coffee add-in name.
     * @return String of coffee add-in name.
     */
    @Override
    public String toString() {return name;}
}
