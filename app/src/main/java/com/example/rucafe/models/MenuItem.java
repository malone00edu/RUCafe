package com.example.rucafe.models;

import java.io.Serializable;

/**
 * Stores information for a menu order. Subclass of all other menu items.
 * @author Taze Balbosa, Yulie Ying
 */
public class MenuItem implements Serializable {

    private static final double INITIAL_PRICE = 0;
    protected double price;
    protected int quantity;

    /**
     * Create a Menu Item type that stores menu item order information.
     * @param quantity Initial quantity of menu item.
     */
    public MenuItem(int quantity) {
        this.price = INITIAL_PRICE;
        this.quantity = quantity;
    }

    /**
     * Returns the price of the menu item.
     * @return Price of menu item.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Sets the quantity of the menu item.
     * @param quantity Quantity of menu item.
     */
    public  void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() { return this.quantity; }

    public void itemPrice() {}

}
