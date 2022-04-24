package com.example.rucafe.models;

import java.io.Serializable;

/**
 * Contains  donut type information.
 * @author Taze Balbosa, Yulie Ying
 */
public enum DonutType implements Serializable {
    YEAST_DONUT("Yeast Donut",1.59, "Maple", "Glazed", "Vanilla Glazed", "Chocolate Glazed"),
    CAKE_DONUT("Cake Donut", 1.79, "Maple", "Vanilla", "Boston Creme", "Chocolate Glazed"),
    DONUT_HOLE("Donut Hole", 0.39, "Custard", "Lemon Cream", "Raspberry", "Strawberry Jelly" );


    final private double price;
    final private String name, flavor_1, flavor_2, flavor_3, flavor_4;

    /**
     * Creates a DonutType object storing donut type information.
     * @param name Name of donut type.
     * @param price Price of donut type.
     * @param flavor_1 First flavor of donut type.
     * @param flavor_2 Second flavor of donut type.
     * @param flavor_3 Third flavor of donut type.
     * @param flavor_4 Fourth flavor of donut type.
     */
    DonutType(String name, double price, String flavor_1, String flavor_2, String flavor_3, String flavor_4) {
        this.name = name;
        this.price = price;
        this.flavor_1 = flavor_1;
        this.flavor_2 = flavor_2;
        this.flavor_3 = flavor_3;
        this.flavor_4 = flavor_4;
    }

    /**
     * Returns the name of the donut type.
     * @return Name of donut type.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the donut type.
     * @return Price of donut type.
     */
    public double getPrice() {return price;}

    /**
     * Returns the first flavor of donut type.
     * @return First flavor.
     */
    public String getFlavor_1() {
        return flavor_1;
    }

    /**
     * Returns the second flavor of donut type.
     * @return Second flavor.
     */
    public String getFlavor_2() {
        return flavor_2;
    }

    /**
     * Returns the third flavor of donut type.
     * @return Third flavor.
     */
    public String getFlavor_3() {
        return flavor_3;
    }

    /**
     * Returns the fourth flavor of donut type.
     * @return Fourth flavor.
     */
    public String getFlavor_4() {
        return flavor_4; }

    /**
     * Returns string format of the donut type's name and flavors.
     * @return String of donut type name and flavors.
     */
    @Override
    public String toString() {
        return  name + "::" + "$" + price + "::" + flavor_1 + ", " + flavor_2 + ", " +
                flavor_3 + ", " + flavor_4;
    }
}
