package com.example.rucafe.models;

import java.io.Serializable;

/**
 * Stores information for a donut order.
 * @author Taze Balbosa, Yulie Ying
 */
public class Donut extends MenuItem implements Serializable {
    private static final double INITIAL_PRICE = 0;

    private String donutType, donutFlavor;

    /**
     * Creates a Donut object that stores donut order information.
     * @param quantity Initial quantity of donuts.
     * @param donutType Initial donut type.
     * @param donutFlavor Initial donut flavor.
     */
    public Donut(int quantity, String donutType, String donutFlavor) {
        super(quantity);
        this.donutType = donutType;
        this.donutFlavor = donutFlavor;
    }

    /**
     * Sets the type of donut.
     * @param donutType Donut type.
     */
    public void setDonutType(String donutType) {
        this.donutType = donutType;
    }

    /**
     * Sets donut flavor.
     * @param donutFlavor Donut flavor.
     */
    public void setDonutFlavor(String donutFlavor) {
        this.donutFlavor = donutFlavor;
    }

    /**
     * Calculates the price of current donut order.
     */
    @Override
    public void itemPrice() {
        double donutPrice = INITIAL_PRICE;

        switch (donutType) {
            case "Yeast Donut":
                donutPrice = DonutType.YEAST_DONUT.getPrice();
                break;
            case "Cake Donut":
                donutPrice = DonutType.CAKE_DONUT.getPrice();
                break;
            case "Donut Hole":
                donutPrice = DonutType.DONUT_HOLE.getPrice();
                break;
            default :
                break;
        }
        price = donutPrice * quantity;
    }

    /**
     * Returns string formal of current donut order.
     * @return String of current donut order.
     */
    @Override
    public String toString() {
        return "x" + quantity + " " + donutType + ": " + donutFlavor;
    }
}
