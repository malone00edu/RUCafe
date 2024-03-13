package com.example.rucafe.model;

/**
 * Contains coffee size information.
 * @author Taze Balbosa, Yulie Ying
 */
public enum CoffeeSize {
    SHORT(1.69, "Short"),
    TALL(2.09, "Tall"),
    GRANDE(2.49, "Grande"),
    VENTI(2.89, "Venti");

    final private double price;
    final private String name;

    /**
     * Creates a CoffeeSize object storing a coffee size's price and name.
     * @param price Price of coffee size.
     * @param name Name of coffee size.
     */
    CoffeeSize(double price, String name) {
        this.price = price;
        this.name = name;
    }

    /**
     * Returns the price of the coffee size.
     * @return Price of coffee size.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the name of the coffee size.
     * @return Name of coffee size.
     */
    public String getName() { return name; }

    /**
     * Returns a string format of the coffee's name and price.
     * @return String of coffee name and price.
     */
    @Override
    public String toString() {
        return name() + " " + price;
    }

}
